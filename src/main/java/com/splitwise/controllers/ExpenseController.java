package com.splitwise.controllers;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.notfound.ExpenseNotFoundException;
import com.splitwise.exceptions.notfound.GroupNotFoundException;
import com.splitwise.exceptions.notfound.UserNotFoundException;
import com.splitwise.models.Expense;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.interfaces.ExpenseRepository;
import com.splitwise.repositories.interfaces.GroupRepository;
import com.splitwise.repositories.interfaces.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.paymentstrategy.PaymentStrategy;
import com.splitwise.services.splitstrategy.SplitStrategy;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExpenseController {
    UserRepository userRepository;
    GroupRepository groupRepository;
    ExpenseRepository expenseRepository;

    public ExpenseController(UserRepository userRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpenseWithOtherUsers(AuthenticationContext authenticationContext,
                                               Set<Long> participantsIds,
                                               PaymentStrategy paymentStrategy,
                                               SplitStrategy splitStrategy, String description,
                                               Date date) {

        User loggedInUser = authenticationContext.getCurrentLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException(""));
        Set<User> participants = participantsIds.stream()
                .map((id) -> userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id.toString())))
                .collect(Collectors.toSet());


        participants.add(loggedInUser);
        Expense expense = new Expense(date, description, participants);
        splitStrategy.calculateAmountsOwed(expense);
        paymentStrategy.calculateAmountsPaid(expense);
        expenseRepository.create(expense);
        return expense;
    }

    public Expense createExpenseInGroup(AuthenticationContext authenticationContext,
                                        Long groupId,
                                        PaymentStrategy paymentStrategy,
                                        SplitStrategy splitStrategy, String description,
                                        Date date) {
        User loggedInUser = authenticationContext.getCurrentLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException(""));
        Group group = groupRepository.findById(groupId).orElseThrow(()-> new GroupNotFoundException(""));

        if(!group.getMembers().contains(loggedInUser))
            throw new GroupNotFoundException("No such group exists");
        Expense expense = new Expense(date, description, group.getMembers());
        splitStrategy.calculateAmountsOwed(expense);
        paymentStrategy.calculateAmountsPaid(expense);
        expenseRepository.create(expense);
        return expense;
    }


    public Expense deleteExpense(AuthenticationContext authenticationContext, Long expenseId){
        User loggedInUser = authenticationContext.getCurrentLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException(""));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new ExpenseNotFoundException(""));
        if(!expense.getParticipants().contains(loggedInUser)){
            throw new ExpenseNotFoundException("");
        }
        expenseRepository.delete(expenseId);
        return expense;
    }

}

