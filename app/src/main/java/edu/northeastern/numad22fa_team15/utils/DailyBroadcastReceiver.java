package edu.northeastern.numad22fa_team15.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.List;

import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;

public class DailyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "DailyBroadcastReceiver";

    private static int notificationGeneration = 1;
    private static final int NOTIFICATION_UNIQUE_ID = 77;

    private IDBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Check if there is a transaction record today.");
        // Create a DB Helper
        dbHelper = new DBHelper(context);
        // Get today's date
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonth().getValue();
        int currentDay = now.getDayOfMonth();
        // Retrieve all transaction(s) today
        List<TransactionModel> todayTransactions = dbHelper.retrieveTransactionsByDateTableTransaction(
                currentYear, currentMonth, currentDay);
        // Check if the transactions list is empty
        // If yes, send notification to remind user to add spending
        if (todayTransactions.isEmpty()) {
            Log.d(TAG, "No transaction record found for today.");
            Log.d(TAG, "Sending Peak daily notification.");
            Notification n = (Notification) intent.getParcelableExtra("notification");
            notificationGeneration++;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_UNIQUE_ID + notificationGeneration, n);
        } else {
            Log.d(TAG, "Transaction record found,");
        }
    }

}
