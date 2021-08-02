package com.splitwise;

import com.splitwise.controllers.ExpenseController;
import com.splitwise.controllers.GroupController;
import com.splitwise.controllers.UserController;
import com.splitwise.repositories.inmemory.InMemoryExpenseRepository;
import com.splitwise.repositories.inmemory.InMemoryGroupRepository;
import com.splitwise.repositories.inmemory.InMemoryUserRepo;
import com.splitwise.repositories.interfaces.ExpenseRepository;
import com.splitwise.repositories.interfaces.GroupRepository;
import com.splitwise.repositories.interfaces.UserRepository;
import com.splitwise.services.authentication.PasswordEncoder;
import com.splitwise.services.authentication.PlainTextPasswordEncoder;
import lombok.Getter;

public class Splitwise {
    @Getter
    UserController userController;
    @Getter
    ExpenseController expenseController;
    @Getter
    GroupController groupController;
    @Getter
    UserRepository userRepository;
    @Getter
    GroupRepository groupRepository;
    @Getter
    ExpenseRepository expenseRepository;
    PasswordEncoder passwordEncoder;

    public Splitwise() {
        userRepository = new InMemoryUserRepo();
        expenseRepository = new InMemoryExpenseRepository();
        groupRepository = new InMemoryGroupRepository();
        passwordEncoder = PlainTextPasswordEncoder.getINSTANCE();
        userController = new UserController(userRepository);
        userController.setPasswordEncoder(passwordEncoder);
        groupController = new GroupController(groupRepository, userRepository);
        expenseController = new ExpenseController(userRepository, groupRepository, expenseRepository);

    }
}
