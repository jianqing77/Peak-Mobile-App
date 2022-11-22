package edu.northeastern.numad22fa_team15.utils;

public interface IDBHelper {

    // Methods that interact with the user table
    boolean addUserTableUser(String username, String firstName, String lastName, String passcode);
    boolean confirmUserTableUser(String usernameInput, String passcodeInput);
    boolean updateUserPasswordTableUser(String usernameInput, String passcodeInput);
    boolean updateUserInfoTableUser(String username, String firstName, String lastName);
    boolean addSummaryTableSummary(String startDate, String endDate, float totalBudget, float currentExpense, float currentBalance);
    // TODO: set category as string for testing, change to enum?
    boolean addTranTableTransaction(float expense, String description, String category, String transactionDate, int summaryID);
    boolean updateTranTableTransaction(float expense, String description, String category, int transactionID);

}
