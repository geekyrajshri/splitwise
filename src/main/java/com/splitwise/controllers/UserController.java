package com.splitwise.controllers;

import com.splitwise.dtos.UserDTO;
import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.validations.DuplicateUsernameException;
import com.splitwise.exceptions.authentication.PasswordDoesNotMatchException;
import com.splitwise.models.Expense;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.interfaces.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.authentication.PasswordEncoder;
import com.splitwise.services.settle.SettleUserStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    SettleUserStrategy settleUserStrategy;


    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public  void setSettleUserStrategy(SettleUserStrategy settleUserStrategy){
        this.settleUserStrategy = settleUserStrategy;
    }
    public User register(UserDTO details) throws DuplicateUsernameException {
        User user = new User();
        if(userRepository.findByUsername(details.username).isPresent()){
            throw new DuplicateUsernameException("Username already exists");
        }
        user.setFullName(details.fullName);
        user.setUsername(details.username);
        String saltedPassword = passwordEncoder.encode(details.password,details.username);
        user.setPassword(saltedPassword);
        user.setPhoneNumber(details.phoneNumber);

        userRepository.create(user);
        return user;

    };
    public void changePassword(AuthenticationContext authenticationContext, String oldPassword, String newPassword){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        if(!user.getPassword().equals(passwordEncoder.encode(oldPassword,user.getUsername()))){
            throw new PasswordDoesNotMatchException("");
        }
        user.setPassword(passwordEncoder.encode(newPassword,user.getUsername()));
        userRepository.save(user);
    }
    public void updateProfile(AuthenticationContext authenticationContext,UserDTO newDetails){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
//        if(!user.getPassword().equals(passwordEncoder.encode(oldPassword,user.getUsername()))){
//            throw new PasswordDoesNotMatchException("");
//        }

        user.setPhoneNumber(newDetails.phoneNumber);
        user.setFullName(newDetails.fullName);
        userRepository.save(user);
    }
    public Double getMyTotalOwed(AuthenticationContext authenticationContext){

        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        return user.getTotalOwed();
    }

    public Set<Expense> getMyExpenseHistory(AuthenticationContext authenticationContext){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        return user.getExpenses();

    }
    public Set<Group> getMyGroups(AuthenticationContext authenticationContext){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        return user.getGroups();
    }

    public void settleUp(AuthenticationContext authenticationContext){
        User user = authenticationContext.getCurrentLoggedInUser().
                orElseThrow(()->  new NotLoggedInException("Please login in"));
        settleUserStrategy.settle(user);
    }
}

