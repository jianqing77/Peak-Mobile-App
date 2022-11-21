package edu.northeastern.numad22fa_team15.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

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

    // Table summary
    private static final String SUMMARY_TABLE_NAME = "summary";
    private static final String SUMMARY_ID_COL = "_summaryID";
    private static final String SUMMARY_START_DATE_COL = "startDate";
    private static final String SUMMARY_END_DATE_COL = "endDate";
    private static final String SUMMARY_TOTAL_BUDGET_COL = "totalBudget";
    private static final String SUMMARY_CURRENT_EXPENSE_COL = "currentExpense";
    private static final String SUMMARY_CURRENT_BALANCE_COL = "currentBalance";

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
    private static final String COST_COL = "cost";
    private static final String DESCRIPTION_COL = "description";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO: Create table queries.
        String createUserTableQuery =
                "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + FIRST_NAME_COL + " TEXT NOT NULL, "
                + LAST_NAME_COL + " TEXT NOT NULL, "
                + USERNAME_COL + " TEXT NOT NULL, "
                + PASSCODE_COL + " TEXT NOT NULL)";

        String createSavingTableQuery =
                "CREATE TABLE " + SAVING_TABLE_NAME + " ("
                + SAVING_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SAVING_GOAL_COL + " TEXT NOT NULL, "
                + SAVING_GOAL_DESCRIPTION_COL + " TEXT NOT NULL, "
                + SAVING_NUM_COL + " FLOAT NOT NULL, "
                + SAVING_STATUS_COL + "BOOLEAN NOT NULL)";

        String createSummaryTableQuery =
                "CREATE TABLE " + SUMMARY_TABLE_NAME + " ("
                + SUMMARY_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SUMMARY_START_DATE_COL + " TEXT NOT NULL, "
                + SUMMARY_END_DATE_COL + " TEXT NOT NULL, "
                + SUMMARY_TOTAL_BUDGET_COL + " FLOAT NOT NULL, "
                + SUMMARY_CURRENT_EXPENSE_COL + " FLOAT NOT NULL, "
                + SUMMARY_CURRENT_BALANCE_COL + " FLOAT NOT NULL)";

        // TODO: Execute "create table" queries.
        db.execSQL(createUserTableQuery);
        db.execSQL(createSavingTableQuery);
        db.execSQL(createSummaryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: This method should be updated if we change the database version number in future releases.
        String dropUserTableQuery = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        db.execSQL(dropUserTableQuery);
        String dropSavingTableQuery = "DROP TABLE IF EXISTS " + SAVING_TABLE_NAME;
        db.execSQL(dropSavingTableQuery);
        String dropSummaryTableQuery = "DROP TABLE IF EXISTS " + SUMMARY_TABLE_NAME;
        db.execSQL(dropSummaryTableQuery);
        onCreate(db);
    }

}
