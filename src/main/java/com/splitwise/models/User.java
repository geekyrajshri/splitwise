package com.splitwise.models;

import com.splitwise.exceptions.validations.InvalidUsernameException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class User extends Auditable {

    @Getter
    @Setter
    private String fullName;
    @Getter
    @Setter
    private String password;
    @Getter
    private String username;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private Set<Expense> expenses = new HashSet<>();
    @Getter
    @Setter
    private Set<Group> groups = new HashSet<>();


    public void setUsername(String username) {
        if(username.length()<2){
            throw new InvalidUsernameException("Username should be more than 2 characters");
        }
        this.username = username;
    }

       public Double getTotalOwed(){
        return 0.00;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
