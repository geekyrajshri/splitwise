package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.Map;

public class ExactAmountSplitStrategy implements SplitStrategy {
    private final Map<User,Double> amountOwed;
    public ExactAmountSplitStrategy(Map<User,Double> amountsOwed){
        this.amountOwed = amountsOwed;
    }
    @Override
    public void calculateAmountsOwed(Expense expense) {
        expense.setOwedAmount(amountOwed);
    }
}
