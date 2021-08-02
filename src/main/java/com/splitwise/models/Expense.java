package com.splitwise.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Expense extends Auditable {


    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private boolean isSettled;

    @Getter
    @Setter
    private Group group;

    @Getter
    @Setter
    private Set<User> participants;

    @Getter
    @Setter
    private int totalAmount;

    @Getter
    @Setter
    private Map<User, Double> paidAmount = new HashMap<>();

    @Getter
    @Setter
    private Map<User, Double> owedAmount = new HashMap<>();

    public Expense(Date date, String description, Set<User> participants) {
        this.date = date;
        this.description = description;
        this.participants = participants;
    }


    public void setAmountsPaid(Map<User, Double> amountsPaid) {
        this.paidAmount =  amountsPaid;
    }
}
