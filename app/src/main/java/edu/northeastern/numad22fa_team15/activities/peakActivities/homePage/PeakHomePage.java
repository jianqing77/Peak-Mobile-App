package edu.northeastern.numad22fa_team15.activities.peakActivities.homePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.northeastern.numad22fa_team15.utils.DailyBroadcastReceiver;
import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.MainActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction.AddTransactionActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.graph.GraphActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.piggySavings.SavingsActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;
import edu.northeastern.numad22fa_team15.utils.recyclerUtils.peakTransactionHistoryRecyclerUtil.TransactionHistoryAdapter;

public class PeakHomePage extends AppCompatActivity {

    private static final String TAG = "PeakHomePage___";
    private static final String CHANNEL_ID = "channel_two"; // Notification Channel

    private RecyclerView recyclerView;
    private List<TransactionModel> matchResults;

    private TextView firstNameTextView;
    private TextView expensesTextView;
    private TextView balanceTextView;
    private Button datePickerButton;

    private NavigationBarView navigationBarView;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_home_page_backup);

        matchResults = new ArrayList<>();
        recyclerView = findViewById(R.id.home_page_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransactionHistoryAdapter(matchResults, this));

        firstNameTextView = (TextView) findViewById(R.id.tv_home_name);
        expensesTextView = (TextView) findViewById(R.id.tv_expenses);
        balanceTextView = (TextView) findViewById(R.id.tv_balance);
        datePickerButton = (Button) findViewById(R.id.btn_pick_date);

        dbHelper = new DBHelper(PeakHomePage.this);

        // set up navigation bar
        navigationBarView = findViewById(R.id.bottom_navigation_id);
        navigationBarView.setSelectedItemId(R.id.nav_home);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_graph:
                        startActivity(new Intent(getApplicationContext(), GraphActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_savings:
                        startActivity(new Intent(getApplicationContext(), SavingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Set homepage information with data from database
        setInfoOnHomePage();

        // Call createNotificationChannel before a notification is sent.
        createNotificationChannel();
        setAlert();
    }

    // Bottom Navigation Bar -- add transaction
    public void addTransactionFAB(View view) {
        Log.v(TAG, "Trying to add a new transaction");
        Intent intent = new Intent(PeakHomePage.this, AddTransactionActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Helper function to set home page Info
     */
    private void setInfoOnHomePage() {
        // Retrieve user's first name
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        firstNameTextView.setText(user.getFirstName());
        // Retrieve this month's expenses and balance
        SummaryModel summary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        float totalBudget = summary.getTotalBudget();
        float expenses = summary.getCurrentExpense();
        float balance = totalBudget - expenses;
        String expensesText = String.format("$%.2f", expenses);
        String balanceText = String.format("$%.2f", balance);
        Log.d(TAG, "DEBUGGING BALANCE: "+ balanceText);
        expensesTextView.setText(expensesText);
        balanceTextView.setText(balanceText);
        // Retrieve today's transaction
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonth().getValue();
        int currentDay = now.getDayOfMonth();
        matchResults.clear();
        List<TransactionModel> transactions = dbHelper.retrieveTransactionsByDateTableTransaction(currentYear, currentMonth, currentDay);
        for (TransactionModel transaction : transactions) {
            matchResults.add(transaction);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        String buttonText = String.format("%s/%s/%s", currentMonth, currentDay, currentYear);
        datePickerButton.setText(buttonText);
    }

    /**
     * This date picker dialog will be open when the datePickerButton is clicked.
     * @param view view
     */
    public void datePickerDialog(View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setTitle("Choose a Date");
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_transactions_date_picker, null);
        DatePicker transactionsDatePicker = (DatePicker) dialogView.findViewById(R.id.transactions_date_picker);
        b.setView(dialogView);

        // Clicking the Confirm button will retrieve all the transactions on that date
        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = transactionsDatePicker.getYear();
                int month = transactionsDatePicker.getMonth()+1;
                int day = transactionsDatePicker.getDayOfMonth();
                // Check if the selected date is valid.
                boolean validationResult = selectedDateChecker(year, month, day);
                if (!validationResult) {
                    String message = "Invalid date. Cannot pick a future date.";
                    displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
                    return;
                }
                matchResults.clear();
                List<TransactionModel> transactions = dbHelper.retrieveTransactionsByDateTableTransaction(year, month, day);
                for (TransactionModel transaction : transactions) {
                    matchResults.add(transaction);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                String buttonText = String.format("%s/%s/%s", month, day, year);
                datePickerButton.setText(buttonText);
            }
        });
        // Clicking the Cancel button will simply dismiss the dialog.
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = b.create();
        alert.show();
    }

    /**
     * Check whether the given date is valid (i.e., present or past).
     * @param year year
     * @param month month
     * @param day day
     * @return true if input date is today or in the past. Otherwise, return false
     */
    private boolean selectedDateChecker(int year, int month, int day) {
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonth().getValue();
        int currentDay = now.getDayOfMonth();
        String message = "" + currentYear + " " + currentMonth + " " + currentDay;
        // Very interesting (stupid) nested if statements xh wrote
        if (year > currentYear) {
            return false;
        } else if (year < currentYear) {
            return true;
        } else { // year == currentYear
            if (month > currentMonth) {
                return false;
            } else if (month < currentMonth) {
                return true;
            } else { // month == currentMonth
                if (day > currentDay) {
                    return false;
                }
                // day <= currentDay
                return true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAlert(){
        Intent intent = new Intent(this, DailyBroadcastReceiver.class);
        Notification notification = buildNotification();
        intent.putExtra("notification", notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Set the alarm to start at approximately 8:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        // TODO: Remember to set the alarm manager correctly.
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (3 * 1000), pendingIntent);
        Log.d(TAG, "Daily notification alarm set on 8 pm.");
    }

    /**
     * This helper method creates the notification channel.
     */
    private void createNotificationChannel() {
        Log.d(TAG, "Create Peak daily notification channel");
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_two_name);
            String description = getString(R.string.channel_two_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Build notification.
     */
    private Notification buildNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent checkIntent = PendingIntent.getActivity(getApplicationContext(),
                (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

        String contentTitle = String.format("Hi Peak user %s", firstNameTextView.getText().toString());
        String contentText = "Remember to add your spending in Peak. =)";
        // Build notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentText(contentText)
                .setContentIntent(checkIntent)
                .build();

        // Hide notification after it is selected
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop()");
        super.onStop();
    }

}