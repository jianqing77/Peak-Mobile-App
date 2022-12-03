package edu.northeastern.numad22fa_team15.activities.peakActivities.homePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
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

    private RecyclerView recyclerView;
    private List<TransactionModel> matchResults;

    private TextView firstNameTextView;
    private TextView expensesTextView;
    private TextView balanceTextView;
    private Button datePickerButton;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_home_page);

        // TODO: Ask user to set budget if it's their first time to open the app

        matchResults = new ArrayList<>();
        recyclerView = findViewById(R.id.home_page_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransactionHistoryAdapter(matchResults, this));

        firstNameTextView = (TextView) findViewById(R.id.tv_home_name);
        expensesTextView = (TextView) findViewById(R.id.tv_expenses);
        balanceTextView = (TextView) findViewById(R.id.tv_balance);
        datePickerButton = (Button) findViewById(R.id.btn_pick_date);

        dbHelper = new DBHelper(PeakHomePage.this);

        // set homepage information with data from database
        setInfoOnHomePage();
    }

    // Bottom Navigation Bar -- open home page
    public void openHomePage(MenuItem item) {
        Intent intent = new Intent(PeakHomePage.this, PeakHomePage.class);
        startActivity(intent);
    }

    // Bottom Navigation Bar -- open graph page
    public void openGraph(MenuItem item) {
        Intent intent = new Intent(PeakHomePage.this, GraphActivity.class);
        startActivity(intent);
    }

    // Bottom Navigation Bar -- open piggy saving page
    public void openPiggySaving(MenuItem item)  {
        Intent intent = new Intent(PeakHomePage.this, SavingsActivity.class);
        startActivity(intent);
    }

    // Bottom Navigation Bar -- open profile page
    public void openProfile(MenuItem item) {
        // TODO: solve bug when open the profile activity
        Intent intent = new Intent(PeakHomePage.this, ProfileActivity.class);
        startActivity(intent);
    }

    // Bottom Navigation Bar -- add transaction
    public void addTransaction(View view) {
        Log.v(TAG, "Trying to add a new transaction");
        Intent intent = new Intent(PeakHomePage.this, AddTransactionActivity.class);
        startActivity(intent);
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
        float expenses = summary.getCurrentExpense();
        float balance = summary.getCurrentBalance();
        String expensesText = String.format("$%.2f", expenses);
        String balanceText = String.format("$%.2f", balance);
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
        // back to main activity when press the back button
        finish();
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


//    // navigation bar
//    bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
//        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    Log.v(TAG, "clicked the home page menu button");
//                    Toast.makeText(PeakHomePage.this, "Home", Toast.LENGTH_SHORT).show();
////                        Intent intent_openHome = new Intent(PeakHomePage.this, PeakHomePage.class);
////                        startActivity(intent_openHome);
//                    break;
//                case R.id.nav_graph:
//                    Log.v(TAG, "clicked the graph page menu button");
//                    Toast.makeText(PeakHomePage.this, "Graph", Toast.LENGTH_SHORT).show();
//
////                        Intent intent_openGraph = new Intent(PeakHomePage.this, GraphActivity.class);
////                        startActivity(intent_openGraph);
//                    break;
//                case R.id.nav_savings:
//                    Log.v(TAG, "clicked the savings page menu button");
//                    Toast.makeText(PeakHomePage.this, "savings", Toast.LENGTH_SHORT).show();
////                        Intent intent_openSavings = new Intent(PeakHomePage.this, SavingsActivity.class);
////                        startActivity(intent_openSavings);
//                    break;
//                case R.id.nav_profile:
//                    Log.v(TAG, "clicked the profile page menu button");
//                    Toast.makeText(PeakHomePage.this, "profile", Toast.LENGTH_SHORT).show();
//
////                        Intent intent_openProfile = new Intent(PeakHomePage.this, ProfileActivity.class);
////                        startActivity(intent_openProfile);
//                    break;
//                default:
//            }
//            return true;
//        }
//    });
}