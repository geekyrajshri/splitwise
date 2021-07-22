package com.splitwise.controllers;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.notfound.GroupNotFoundException;
import com.splitwise.exceptions.notfound.UserNotFoundException;
import com.splitwise.models.Expense;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.ExpenseRepository;
import com.splitwise.repositories.GroupRepository;
import com.splitwise.repositories.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.paymentstrategy.PaymentStrategy;
import com.splitwise.services.splitstrategy.SplitStrategy;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseController {
    UserRepository userRepository;
    GroupRepository groupRepository;
    ExpenseRepository expenseRepository;

    public Expense createExpenseWithOtherUsers(AuthenticationContext authenticationContext,
                                               List<Long> participantsIds,
                                               PaymentStrategy paymentStrategy,
                                               SplitStrategy splitStrategy, String description,
                                               Date date) {

        User loggedInUser = authenticationContext.getCurrentLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException(""));
        List<User> participants = participantsIds.stream()
                .map((id) -> userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id.toString())))
                .collect(Collectors.toList());


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

}

