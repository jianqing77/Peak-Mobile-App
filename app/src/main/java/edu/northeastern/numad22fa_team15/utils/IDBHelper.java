package edu.northeastern.numad22fa_team15.utils;

import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;

public interface IDBHelper {

    // Methods that interact with the user table
    boolean addUserTableUser(String username, String firstName, String lastName, String passcode);
    boolean confirmUserTableUser(String usernameInput, String passcodeInput);
    boolean resetUserPasswordTableUser(String passcodeInput);
    boolean updateUserPasswordTableUser(String usernameInput, String passcodeInput);
    boolean updateUserInfoTableUser(String username, String firstName, String lastName, byte[] profilePicture);
    boolean updateUserProfilePictureTableUser(byte[] profilePictureBlob);
    UserModel retrieveUserInfoTableUser();

    boolean addSummaryTableSummary(Integer year, Integer month, float totalBudget,
                                   float diningBudget, float groceriesBudget, float shoppingBudget,
                                   float livingBudget, float entertainmentBudget, float educationBudget,
                                   float beautyBudget, float transportationBudget, float healthBudget,
                                   float travelBudget, float petBudget, float otherBudget);
    SummaryModel retrieveLatestSummaryInfoTableUser();
    // TODO: set category as string for testing, change to enum?
    boolean addTranTableTransaction(float expense, String description, String category, String transactionDate, int summaryID);
    boolean updateTranTableTransaction(float expense, String description, String category, int transactionID);

    // Methods that interact with multiple tables in the database
    boolean truncateTablesTransactionSummaryAndSaving();




}
