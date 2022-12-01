package edu.northeastern.numad22fa_team15.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;

public class DBHelper extends SQLiteOpenHelper implements IDBHelper {

    // Database name
    private static final String DB_NAME = "peakDB";

    // Database version
    private static final int DB_VERSION = 1;

    // Table user
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID_COL = "_userId";
    private static final String FIRST_NAME_COL = "firstName";
    private static final String LAST_NAME_COL = "lastName";
    private static final String USERNAME_COL = "username";
    private static final String PASSCODE_COL = "passcode";
    private static final String PROFILE_PICTURE_COL = "profilePicture";

    // Table summary
    private static final String SUMMARY_TABLE_NAME = "summary";
    private static final String SUMMARY_ID_COL = "_summaryID";
    private static final String SUMMARY_YEAR_COL = "year";
    private static final String SUMMARY_MONTH_COL = "month";
    private static final String SUMMARY_TOTAL_BUDGET_COL = "totalBudget";
    private static final String SUMMARY_CURRENT_EXPENSE_COL = "currentExpense";
    private static final String SUMMARY_CURRENT_BALANCE_COL = "currentBalance";

    private static final String SUMMARY_DINING_BUDGET_COL = "diningBudget";
    private static final String SUMMARY_DINING_EXPENSE_COL = "diningExpense";
    private static final String SUMMARY_GROCERIES_BUDGET_COL = "groceriesBudget";
    private static final String SUMMARY_GROCERIES_EXPENSE_COL = "groceriesExpense";
    private static final String SUMMARY_SHOPPING_BUDGET_COL = "shoppingBudget";
    private static final String SUMMARY_SHOPPING_EXPENSE_COL = "shoppingExpense";
    private static final String SUMMARY_LIVING_BUDGET_COL = "livingBudget";
    private static final String SUMMARY_LIVING_EXPENSE_COL = "livingExpense";
    private static final String SUMMARY_ENTERTAINMENT_BUDGET_COL = "entertainmentBudget";
    private static final String SUMMARY_ENTERTAINMENT_EXPENSE_COL = "entertainmentExpense";
    private static final String SUMMARY_EDUCATION_BUDGET_COL = "educationBudget";
    private static final String SUMMARY_EDUCATION_EXPENSE_COL = "educationExpense";
    private static final String SUMMARY_BEAUTY_BUDGET_COL = "beautyBudget";
    private static final String SUMMARY_BEAUTY_EXPENSE_COL = "beautyExpense";
    private static final String SUMMARY_TRANSPORTATION_BUDGET_COL = "transportationBudget";
    private static final String SUMMARY_TRANSPORTATION_EXPENSE_COL = "transportationExpense";
    private static final String SUMMARY_HEALTH_BUDGET_COL = "healthBudget";
    private static final String SUMMARY_HEALTH_EXPENSE_COL = "healthExpense";
    private static final String SUMMARY_TRAVEL_BUDGET_COL = "travelBudget";
    private static final String SUMMARY_TRAVEL_EXPENSE_COL = "travelExpense";
    private static final String SUMMARY_PET_BUDGET_COL = "petBudget";
    private static final String SUMMARY_PET_EXPENSE_COL = "petExpense";
    private static final String SUMMARY_OTHER_BUDGET_COL = "otherBudget";
    private static final String SUMMARY_OTHER_EXPENSE_COL = "otherExpense";

    // Table saving (for piggy bank)
    private static final String SAVING_TABLE_NAME = "saving";
    private static final String SAVING_ID_COL = "_savingID";
    private static final String SAVING_GOAL_COL = "savingGoal";
    private static final String SAVING_GOAL_DESCRIPTION_COL = "goalDescription";
    private static final String SAVING_NUM_COL = "savingNum";
    private static final String SAVING_STATUS_COL = "savingStatus";

    // Table transaction
    private static final String TRANSACTION_TABLE_NAME = "transactionEntry";
    private static final String TRANSACTION_ID_COL = "_transactionId";
    private static final String DESCRIPTION_COL = "description";
    private static final String EXPENSE_COL = "expense";
    private static final String CATEGORY_COL = "category";
    private static final String TRANSACTION_DATE_COL = "transactionDate";
    private static final String RECEIPT_PHOTO_COL = "receiptPhoto";
    private static final String TRANSACTION_FK_SUMMARY_ID_COL = "fk_summaryID";

    private static final String CATEGORY_ENUM = "Enum('" + Category.DINING.name() + "', '"
            + Category.GROCERIES.name() + "', '"
            + Category.SHOPPING.name() + "', '"
            + Category.LIVING.name() + "', '"
            + Category.ENTERTAINMENT.name() + "', '"
            + Category.EDUCATION.name() + "', '"
            + Category.BEAUTY.name() + "', '"
            + Category.TRANSPORTATION.name() + "', '"
            + Category.HEALTH.name() + "', '"
            + Category.TRAVEL.name() + "', '"
            + Category.PET.name() + "', '"
            + Category.OTHER.name() + "')";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table queries.
        String createUserTableQuery =
                "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " ("
                + USER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + FIRST_NAME_COL + " TEXT NOT NULL, "
                + LAST_NAME_COL + " TEXT NOT NULL, "
                + USERNAME_COL + " TEXT NOT NULL, "
                + PASSCODE_COL + " TEXT NOT NULL, "
                + PROFILE_PICTURE_COL + " BLOB, "
                + "CHECK (" + USER_ID_COL + " < 2));";

        String createSavingTableQuery =
                "CREATE TABLE IF NOT EXISTS " + SAVING_TABLE_NAME + " ("
                + SAVING_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SAVING_GOAL_COL + " TEXT NOT NULL, "
                + SAVING_GOAL_DESCRIPTION_COL + " TEXT NOT NULL, "
                + SAVING_NUM_COL + " FLOAT NOT NULL, "
                + SAVING_STATUS_COL + "BOOLEAN NOT NULL)";

        String createSummaryTableQuery =
                "CREATE TABLE IF NOT EXISTS " + SUMMARY_TABLE_NAME + " ("
                + SUMMARY_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SUMMARY_YEAR_COL + " INTEGER NOT NULL, "
                + SUMMARY_MONTH_COL + " INTEGER NOT NULL, "
                + SUMMARY_TOTAL_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_CURRENT_EXPENSE_COL + " FLOAT DEFAULT 0, "
                + SUMMARY_CURRENT_BALANCE_COL + " FLOAT NOT NULL, "
                + SUMMARY_DINING_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_DINING_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_GROCERIES_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_GROCERIES_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_SHOPPING_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_SHOPPING_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_LIVING_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_LIVING_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_ENTERTAINMENT_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_ENTERTAINMENT_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_EDUCATION_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_EDUCATION_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_BEAUTY_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_BEAUTY_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_TRANSPORTATION_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_TRANSPORTATION_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_HEALTH_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_HEALTH_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_TRAVEL_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_TRAVEL_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_PET_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_PET_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_OTHER_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_OTHER_EXPENSE_COL + " FLOAT NOT NULL)";

        String createTransactionTableQuery =
                "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE_NAME + " ("
                + TRANSACTION_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + EXPENSE_COL + " FLOAT NOT NULL, "
                + CATEGORY_COL + " TEXT NOT NULL, "
                + DESCRIPTION_COL + " TEXT NOT NULL, "
                + TRANSACTION_DATE_COL + " TEXT NOT NULL, "
                + RECEIPT_PHOTO_COL + " BLOB, "
                + TRANSACTION_FK_SUMMARY_ID_COL + " INT NOT NULL, "
                + "FOREIGN KEY (" + TRANSACTION_FK_SUMMARY_ID_COL + ") REFERENCES "
                        + SUMMARY_TABLE_NAME + "(" + SUMMARY_ID_COL + "))";

        // Execute "create table" queries.
        db.execSQL(createUserTableQuery);
        db.execSQL(createSavingTableQuery);
        db.execSQL(createSummaryTableQuery);
        db.execSQL(createTransactionTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: This method should be updated if we change the database version number in future releases.
        String dropUserTableQuery = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";
        db.execSQL(dropUserTableQuery);
        String dropSavingTableQuery = "DROP TABLE IF EXISTS " + SAVING_TABLE_NAME + ";";
        db.execSQL(dropSavingTableQuery);
        String dropSummaryTableQuery = "DROP TABLE IF EXISTS " + SUMMARY_TABLE_NAME + ";";
        db.execSQL(dropSummaryTableQuery);
        String dropTransactionTableQuery = "DROP TABLE IF EXISTS " + TRANSACTION_TABLE_NAME + ";";
        db.execSQL(dropTransactionTableQuery);
        onCreate(db);
    }

    @Override
    public boolean addUserTableUser(String username, String firstName, String lastName, String passcode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(FIRST_NAME_COL, firstName);
        values.put(LAST_NAME_COL, lastName);
        values.put(PASSCODE_COL, passcode);

        long result = db.insert(USER_TABLE_NAME, null, values);
        return (result != -1);
    }

    @Override
    public boolean confirmUserTableUser(String usernameInput, String passcodeInput) {
        Cursor cursor = getUserCursor();

        boolean confirmResult = false;
        if (cursor.moveToFirst()) {
            String username = cursor.getString(3);
            String passcode = cursor.getString(4);
            if (usernameInput.equals(username) && passcodeInput.equals(passcode)) {
                confirmResult = true;
            }
        }
        cursor.close();
        return confirmResult;
    }

    private Cursor getUserCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Hardcoded to be 1 because we will only have one user for the local SQLite database.
        String getUserQuery = String.format("SELECT * FROM %s WHERE %s = 1", USER_TABLE_NAME, USER_ID_COL);
        Cursor cursor = db.rawQuery(getUserQuery, null);
        return cursor;
    }

    @Override
    public boolean resetUserPasswordTableUser(String passcodeInput) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSCODE_COL, passcodeInput);

        // Number of users should always be 1.
        String whereClause = String.format("%s = ?", USER_ID_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{"1"});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public boolean updateUserPasswordTableUser(String usernameInput, String passcodeInput) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSCODE_COL, passcodeInput);

        String whereClause = String.format("%s = ?", USERNAME_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{usernameInput});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public boolean updateUserInfoTableUser(String username, String firstName, String lastName, byte[] profilePicture) {
        // This method does not validate user identity and should only be called on the Edit Profile page.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(FIRST_NAME_COL, firstName);
        values.put(LAST_NAME_COL, lastName);
        values.put(PROFILE_PICTURE_COL, profilePicture);

        // Number of users should always be 1.
        String whereClause = String.format("%s = ?", USER_ID_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{"1"});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public boolean updateUserProfilePictureTableUser(byte[] profilePictureBlob) {
        // This method does not validate user identity and should only be called on the Edit Profile page.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_PICTURE_COL, profilePictureBlob);

        // Number of users should always be 1.
        String whereClause = String.format("%s = ?", USER_ID_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{"1"});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public UserModel retrieveUserInfoTableUser() {
        Cursor cursor = getUserCursor();

        UserModel user = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String username = cursor.getString(3);
            String passcode = cursor.getString(4);
            byte[] profilePicture = cursor.getBlob(5);
            user = new UserModel(firstName, lastName, username, passcode, profilePicture);
        }
        cursor.close();
        return user;
    }

    @Override
    public boolean addSummaryTableSummary(Integer year, Integer month, float totalBudget) {
        SQLiteDatabase db = this.getWritableDatabase();

        float currentBalance = totalBudget;

        ContentValues values = new ContentValues();
        values.put(SUMMARY_YEAR_COL, year);
        values.put(SUMMARY_MONTH_COL, month);
        values.put(SUMMARY_TOTAL_BUDGET_COL, totalBudget);
        values.put(SUMMARY_CURRENT_BALANCE_COL, currentBalance);

        long result = db.insert(SUMMARY_TABLE_NAME, null, values);
        return (result != -1);
    }

    @Override
    public SummaryModel retrieveLatestSummaryInfoTableUser() {
        Cursor cursor = getSummaryCursor();

        SummaryModel summary = null;
        if (cursor.moveToLast()) {
            int year = cursor.getInt(1);
            int month = cursor.getInt(2);
            float totalBudget = cursor.getFloat(3);
            float currentExpense = cursor.getFloat(4);
            float diningBudget = cursor.getFloat(5);
            float diningExpense = cursor.getFloat(6);
            float groceriesBudget = cursor.getFloat(7);
            float groceriesExpense = cursor.getFloat(8);
            float shoppingBudget = cursor.getFloat(9);
            float shoppingExpense = cursor.getFloat(10);
            float livingBudget = cursor.getFloat(11);
            float livingExpense = cursor.getFloat(12);
            float entertainmentBudget = cursor.getFloat(13);
            float entertainmentExpense = cursor.getFloat(14);
            float educationBudget = cursor.getFloat(15);
            float educationExpense = cursor.getFloat(16);
            float beautyBudget = cursor.getFloat(17);
            float beautyExpense = cursor.getFloat(18);
            float transportationBudget = cursor.getFloat(19);
            float transportationExpense = cursor.getFloat(20);
            float healthBudget = cursor.getFloat(21);
            float healthExpense = cursor.getFloat(22);
            float travelBudget = cursor.getFloat(23);
            float travelExpense = cursor.getFloat(24);
            float petBudget = cursor.getFloat(25);
            float petExpense = cursor.getFloat(26);
            float otherBudget = cursor.getFloat(27);
            float otherExpense = cursor.getFloat(28);

            summary = new SummaryModel(year, month, totalBudget, currentExpense,
            diningBudget, diningExpense,
            groceriesBudget, groceriesExpense,
            shoppingBudget, shoppingExpense,
            livingBudget, livingExpense,
            entertainmentBudget, entertainmentExpense,
            educationBudget, educationExpense,
            beautyBudget, beautyExpense,
            transportationBudget, transportationExpense,
            healthBudget, healthExpense,
            travelBudget, travelExpense,
            petBudget, petExpense,
            otherBudget, otherExpense);
        }
        cursor.close();
        return summary;
    }

    private Cursor getSummaryCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String getUserQuery = String.format("SELECT * FROM %s", SUMMARY_TABLE_NAME);
        Cursor cursor = db.rawQuery(getUserQuery, null);
        return cursor;
    }


    @Override
    public boolean addTranTableTransaction(float expense, String description, String category, String transactionDate, int summaryID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_COL, expense);
        values.put(DESCRIPTION_COL, description);
        values.put(CATEGORY_COL, category);
        values.put(TRANSACTION_DATE_COL, transactionDate);
        values.put(TRANSACTION_FK_SUMMARY_ID_COL, summaryID);

        long result = db.insert(TRANSACTION_TABLE_NAME, null, values);
        return (result != -1);
    }

    @Override
    public boolean updateTranTableTransaction(float expense, String description, String category, int transactionID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_COL, expense);
        values.put(DESCRIPTION_COL, description);
        values.put(CATEGORY_COL, category);

        String whereClause = String.format("%s = ?", TRANSACTION_ID_COL);
        int numOfRowsImpacted = db.update(TRANSACTION_TABLE_NAME, values, whereClause, new String[]{String.valueOf(transactionID)});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public boolean truncateTablesTransactionSummaryAndSaving() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TRANSACTION_TABLE_NAME, null, null);
        rowsAffected += db.delete(SUMMARY_TABLE_NAME, null, null);
        rowsAffected += db.delete(SAVING_TABLE_NAME, null, null);

        // Return true if at least 1 row was affected since we cannot guarantee every table has rows.
        return (rowsAffected != 0);
    }

    @Override
    public boolean addSavingTableSaving(float savingGoal, String goalDescription, float savingSoFar, boolean savingStatus) {
        // TODO
        return false;
    }

}
