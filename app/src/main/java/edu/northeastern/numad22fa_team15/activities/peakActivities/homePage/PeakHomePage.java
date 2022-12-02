package edu.northeastern.numad22fa_team15.activities.peakActivities.homePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseFriendListActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakCreateBudget;
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
    private Button datePickerButton;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_home_page);

        matchResults = new ArrayList<>();
        recyclerView = findViewById(R.id.home_page_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransactionHistoryAdapter(matchResults, this));

        firstNameTextView = (TextView) findViewById(R.id.tv_home_name);
        datePickerButton = (Button) findViewById(R.id.btn_pick_date);

        dbHelper = new DBHelper(PeakHomePage.this);

        setInfoOnHomePage();
    }

    @Override
    public void onBackPressed() {
        // back to main activity when press the back button
        finish();
    }

    private void setInfoOnHomePage() {
        // Retrieve user's first name
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        firstNameTextView.setText(user.getFirstName());
        // Retrieve this month's expenses and balance

        // Retrieve today's transaction
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
                    // TODO
                    // Display invalid date message
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

    private boolean selectedDateChecker(int year, int month, int day) {
        LocalDateTime now = LocalDateTime.now();
//                String transactionDate = String.valueOf(now);

        int currentYear = now.getYear();
        int currentMonth = now.getMonth().getValue();
        int currentDay = now.getDayOfMonth();
        String message = "" + currentYear + " " + currentMonth + " " + currentDay;
//        displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
        return true;

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