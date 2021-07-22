package com.splitwise.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Expense extends Auditable {


   private String description;
   private Date date;
   private boolean isSettled;

   private  List<User> participants;
   private int totalAmount;
   private Map<User,Double> paidAmount;
   private Map<User,Double> owedAmount;

   public Expense(Date date, String description, List<User> participants) {
      this.date = date;
      this.description = description;
      this.participants = participants;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public boolean isSettled() {
      return isSettled;
   }

   public void setSettled(boolean settled) {
      isSettled = settled;
   }

   public List<User> getParticipants() {
      return participants;
   }

   public void setParticipants(List<User> participants) {
      this.participants = participants;
   }

   public int getTotalAmount() {
      return totalAmount;
   }

   public void setTotalAmount(int totalAmount) {
      this.totalAmount = totalAmount;
   }

   public Map<User, Double> getPaidAmount() {
      return paidAmount;
   }

   public void setAmountsPaid(Map<User, Double> paidAmount) {
      this.paidAmount = paidAmount;
   }

   public Map<User, Double> getOwedAmount() {
      return owedAmount;
   }

   public void setOwedAmount(Map<User, Double> owedAmount) {
      this.owedAmount = owedAmount;
   }
}
