package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a summary entry in the sqlite database.
 * A summary entry has the following information: (1) summaryID, (2) startDate, (3) endDate,
 * (4) totalBudget, (5) currentExpense, (6) currentBalance.
 */

public class SummaryModel {
    private int summaryID;
    private Integer year;
    private Integer month;
    private float totalBudget;
    private float currentExpense;
    private float currentBalance;

    public SummaryModel(Integer year, Integer month, float totalBudget, float currentExpense) {
        this.year = year;
        this.month = month;
        this.totalBudget = totalBudget;
        this.currentExpense = currentExpense;
    }

    public int getSummaryID() {
        return this.summaryID;
    }

    public void setSummaryID(int summaryID) {
        this.summaryID = summaryID;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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
