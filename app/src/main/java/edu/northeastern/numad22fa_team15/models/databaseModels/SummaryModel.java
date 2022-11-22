package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a summary entry in the sqlite database.
 * A summary entry has the following information: (1) summaryID, (2) startDate, (3) endDate,
 * (4) totalBudget, (5) currentExpense, (6) currentBalance.
 */

public class SummaryModel {
    private int summaryID;
    private String startDate;
    private String endDate;
    private float totalBudget;
    private float currentExpense;
    private float currentBalance;

    public SummaryModel(String startDate, String endDate, float totalBudget, float currentExpense) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
        this.currentExpense = currentExpense;
    }

    public int getSummaryID() {
        return this.summaryID;
    }

    public void setSummaryID(int summaryID) {
        this.summaryID = summaryID;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getTotalBudget() {
        return this.totalBudget;
    }

    public void setTotalBudget(float totalBudget) {
        this.totalBudget = totalBudget;
    }

    public float getCurrentExpense() {
        return this.currentExpense;
    }

    public void setCurrentExpense(float currentExpense) {
        this.currentExpense = currentExpense;
    }

    public float getCurrentBalance() {
        return this.currentBalance;
    }

    public void setCurrentBalance() {
        this.currentBalance = this.totalBudget - this.currentExpense;
    }

}
