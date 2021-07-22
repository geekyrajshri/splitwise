package com.splitwise.models;

import com.splitwise.exceptions.validations.InvalidUsernameException;

import java.util.List;
import java.util.Set;

public class User extends Auditable {


    private String fullName;
    private String password;
    private String username;
    private String phoneNumber;

    private Set<Expense> expenses;
    private List<Group> groups;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username.length()<2){
            throw new InvalidUsernameException("Username should be more than 2 characters");
        }
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }
    public Double getTotalOwed(){
        return 0.00;
    }
}
