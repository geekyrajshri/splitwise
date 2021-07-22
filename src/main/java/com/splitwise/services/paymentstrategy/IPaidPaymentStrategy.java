package com.splitwise.services.paymentstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class IPaidPaymentStrategy implements PaymentStrategy{

    private final User me;
    private final Double amountsPaid;

    public IPaidPaymentStrategy(User me,Double amountsPaid){
        this.me = me;
        this.amountsPaid = amountsPaid;
    }
    @Override
    public void calculateAmountsPaid(Expense expense) {
        Map<User,Double>  amountsPaid = new HashMap<>();
        amountsPaid.put(me, this.amountsPaid);
        for (User participant : expense.getParticipants()) {
            if (participant.equals(me)) continue;
            amountsPaid.put(participant, 0.0);
        }
        expense.setAmountsPaid(amountsPaid);
    }
}
