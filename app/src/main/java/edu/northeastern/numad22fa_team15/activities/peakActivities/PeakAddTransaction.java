package edu.northeastern.numad22fa_team15.activities.peakActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.Category;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakAddTransaction extends AppCompatActivity {

    private static final String TAG = "TestPeakAddTransaction___";
    private TextInputEditText expenseTextInputEditText;
    private TextInputEditText descriptionTextInputEditText;
    private TextInputEditText categoryTextInputEditText;
    private TextInputEditText transactionIdInputEdittext;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peak_add_transaction);

        dbHelper = new DBHelper(PeakAddTransaction.this);
        expenseTextInputEditText = findViewById(R.id.expense_textinput_edittext);
        descriptionTextInputEditText = findViewById(R.id.description_textinput_edittext);
        categoryTextInputEditText = findViewById(R.id.category_textinput_edittext);
        transactionIdInputEdittext = findViewById(R.id.transactionID_textinput_edittext);
    }

    public void addTransaction(View view) {
        closeKeyboard(this.getApplicationContext(), view);

        // TODO: hardcoded for testing
        boolean addSummary = dbHelper.addSummaryTableSummary("2022/11/01 00:00:00", "2022/11/30 23:59:59",
                2000, 0, 2000);
        String summaryMessage = "Fail to add Summary";
        if (addSummary) {
            summaryMessage = "Successfully added summary";
        }
        displayMessageInSnackbar(view, summaryMessage, Snackbar.LENGTH_SHORT);

        String expenseString = expenseTextInputEditText.getText().toString();
        String description = descriptionTextInputEditText.getText().toString();
        String category = categoryTextInputEditText.getText().toString();

        if (nullOrEmptyInputChecker(expenseString, description, category)) {
            String message = "All fields are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }

        float expense = Float.parseFloat(expenseString);

        if (invalidCategoryChecker(category)) {
            String message = "Invalid Category.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);

        // hardcode summary id for testing
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate, 1);
        String transactionMessage = "Fail to add Transaction";
        if (addTransaction) {
            transactionMessage = "Successfully added transaction";
        }
        displayMessageInSnackbar(view, transactionMessage, Snackbar.LENGTH_SHORT);


    }

    private boolean invalidCategoryChecker(String category) {
        Category[] categories = Category.values();
        List<String> category_lst = new ArrayList<>();
        for (Category c : categories) {
            category_lst.add(c.toString());
        }
        if (category_lst.contains(category)) {
            return false;
        }
        return true;

    }

    public void updateTransaction(View view) {
        closeKeyboard(this.getApplicationContext(), view);

        String expenseString = expenseTextInputEditText.getText().toString();
        String description = descriptionTextInputEditText.getText().toString();
        String category = categoryTextInputEditText.getText().toString();
        String idString = transactionIdInputEdittext.getText().toString();

        if (nullOrEmptyInputChecker(expenseString, description, category)) {
            String message = "All fields are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }

        float expense = Float.parseFloat(expenseString);
        int transactionID = Integer.parseInt(idString);

        if (invalidCategoryChecker(category)) {
            String message = "Invalid Category.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }

        boolean updateResult = dbHelper.updateTranTableTransaction(expense, description, category, transactionID);
        String resultMessage = "Failed to update transaction";
        if (updateResult) {
            resultMessage = "Successfully updated transaction";
        }
        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);

    }
}