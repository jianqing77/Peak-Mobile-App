package edu.northeastern.numad22fa_team15.activities.peakActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.northeastern.numad22fa_team15.PeakSummaryPieChart;
import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;
import edu.northeastern.numad22fa_team15.utils.Category;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakAddTransaction extends AppCompatActivity {

    private static final String TAG = "TestPeakAddTransaction___";
    private TextInputEditText expenseTextInputEditText;
    private TextInputEditText descriptionTextInputEditText;
    private TextInputEditText categoryTextInputEditText;
    private TextInputEditText transactionIdInputEdittext;
    private TextInputEditText transactionDateInputEdittext;

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
        transactionDateInputEdittext = findViewById(R.id.transactionDate_textinput_edittext);
    }

    public void viewTransactions(View view) {
        List<TransactionModel> transactions = dbHelper.retrieveTransactionsByDateTableTransaction(2022, 12,1);
        StringBuilder sb = new StringBuilder();
        for (TransactionModel transactionModel : transactions) {
            sb.append(transactionModel.getCategory() + "\n");
            sb.append(transactionModel.getDescription() + "\n");
            sb.append(transactionModel.getExpense() + "\n");
        }
        String message = sb.toString();
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setTitle("Transactions");
        b.setMessage(message);

        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = b.create();
        alert.show();
    }

    public void addTransaction(View view) {
        closeKeyboard(this.getApplicationContext(), view);

        String expenseString = expenseTextInputEditText.getText().toString();
        String description = descriptionTextInputEditText.getText().toString();
        String category = categoryTextInputEditText.getText().toString();
        String transactionDate = transactionDateInputEdittext.getText().toString();

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

        if (nullOrEmptyInputChecker(transactionDate)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            transactionDate = String.valueOf(now);
        }

        // TODO: check if there's correlated summary table and create?
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate, null);
        String transactionMessage = "Fail to add Transaction";
        if (addTransaction) {
            transactionMessage = "Successfully added transaction";
        }
        displayMessageInSnackbar(view, transactionMessage, Snackbar.LENGTH_SHORT);

        updateSummaryTable(expense, category, transactionDate);


    }

    private void updateSummaryTable(float expense, String category, String transactionDate) {
        SummaryModel currentSummary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        float currentExpense = currentSummary.getCurrentExpense();
        float diningExpense = currentSummary.getDiningExpense();
        float groceriesExpense = currentSummary.getGroceriesExpense();
        float shoppingExpense = currentSummary.getShoppingExpense();
        float livingExpense = currentSummary.getLivingExpense();
        float entertainmentExpense = currentSummary.getEntertainmentExpense();
        float educationExpense = currentSummary.getEducationExpense();
        float beautyExpense = currentSummary.getBeautyExpense();
        float transportationExpense = currentSummary.getTransportationExpense();
        float healthExpense = currentSummary.getHealthExpense();
        float travelExpense = currentSummary.getTravelExpense();
        float petExpense = currentSummary.getPetExpense();
        float otherExpense = currentSummary.getOtherExpense();

        //

        // TODO: hardcoded for now, need a better helper function
        if (category.equals("DINING")) {
            diningExpense += expense;
        } else if (category.equals("GROCERIES")) {
            groceriesExpense += expense;
        } else if (category.equals("SHOPPING")) {
            shoppingExpense += expense;
        } else if (category.equals("LIVING")) {
            livingExpense += expense;
        } else if (category.equals("ENTERTAINMENT")) {
            entertainmentExpense += expense;
        } else if (category.equals("EDUCATION")) {
            educationExpense += expense;
        } else if (category.equals("BEAUTY")) {
            beautyExpense += expense;
        } else if (category.equals("TRANSPORTATION")) {
            transportationExpense += expense;
        } else if (category.equals("HEALTH")) {
            healthExpense += expense;
        } else if (category.equals("TRAVEL")) {
            travelExpense += expense;
        } else if (category.equals("PET")) {
            petExpense += expense;
        } else if (category.equals("OTHER")) {
            otherExpense += expense;
        } else {
            String message = "invalid category";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
        currentExpense += expense;

        int year = Integer.parseInt(transactionDate.substring(0, 4));
        int month = Integer.parseInt(transactionDate.substring(5, 7));

        boolean updateSummary = dbHelper.updateExpenseTableSummary(year, month, currentExpense,
                diningExpense, groceriesExpense, shoppingExpense, livingExpense, entertainmentExpense,
                educationExpense, beautyExpense, transportationExpense, healthExpense, travelExpense,
                petExpense, otherExpense);
        String transactionMessage = "Fail to update summary";
        if (updateSummary) {
            transactionMessage = "Successfully added transaction";
        }
        // displayMessageInSnackbar(, transactionMessage, Snackbar.LENGTH_SHORT);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, transactionMessage, duration);
        toast.show();
    }

    private boolean invalidCategoryChecker(String categoryInput) {
        Category[] categories = Category.values();
        for (Category category : categories) {
            if (categoryInput.equals(category)) {
                return true;
            }
        }
        return false;
    }

    public void updateTransaction(View view) {
        closeKeyboard(this.getApplicationContext(), view);

        String expenseString = expenseTextInputEditText.getText().toString();
        String description = descriptionTextInputEditText.getText().toString();
        String category = categoryTextInputEditText.getText().toString();
        String idString = transactionIdInputEdittext.getText().toString();

        if (nullOrEmptyInputChecker(expenseString, description, category, idString)) {
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

    public void summaryActivity(View view) {
        Intent intent = new Intent(PeakAddTransaction.this, PeakSummaryPieChart.class);
        startActivity(intent);
    }
}