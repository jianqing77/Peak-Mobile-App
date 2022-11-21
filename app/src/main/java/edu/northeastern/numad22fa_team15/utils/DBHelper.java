package edu.northeastern.numad22fa_team15.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // Table summary
    private static final String SUMMARY_TABLE_NAME = "summary";

    // Table saving
    private static final String SAVING_TABLE_NAME = "saving";

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
        // TODO: Execute "create table" queries.
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: This method should be updated if we change the database version number in future releases.
        String dropUserTableQuery = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        db.execSQL(dropUserTableQuery);
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
    public boolean updateUserPasswordTableUser(String usernameInput, String passcodeInput) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSCODE_COL, passcodeInput);

        String whereClause = String.format("%s = ?", USERNAME_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{usernameInput});

        return (numOfRowsImpacted != 0);
    }

    @Override
    public boolean updateUserInfoTableUser(String username, String firstName, String lastName) {
        // This method does not validate user identity and should only be called on the Profile page.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(FIRST_NAME_COL, firstName);
        values.put(LAST_NAME_COL, lastName);

        // Number of users should always be 1.
        String whereClause = String.format("%s = ?", USER_ID_COL);
        int numOfRowsImpacted = db.update(USER_TABLE_NAME, values, whereClause, new String[]{"1"});

        return (numOfRowsImpacted != 0);
    }
}
