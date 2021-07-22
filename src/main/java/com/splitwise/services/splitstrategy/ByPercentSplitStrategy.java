package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class ByPercentSplitStrategy implements SplitStrategy {

    private final Map<User,Integer> percents;
    private final Double totalAmount;

    public ByPercentSplitStrategy(Map<User,Integer> percents,Double totalAmount){
        this.percents = percents;
        this.totalAmount = totalAmount;
    }
    @Override
    public void calculateAmountsOwed(Expense expense) {
        Map<User,Double> amountsPaid = new HashMap<>();
        for(User participant: expense.getParticipants()){
            Double amount = totalAmount*percents.get(participant)/100.0;
            amountsPaid.put(participant,amount);
        }
        expense.setOwedAmount(amountsPaid);
    }
}
