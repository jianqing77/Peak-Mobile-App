package edu.northeastern.numad22fa_team15.activities.peakActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
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

        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate);
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

        // TODO: hardcoded for now, need a better helper function
        if (category == "DINING") {
            diningExpense += expense;
        } else if (category == "GROCERIES") {
            groceriesExpense += expense;
        } else if (category == "SHOPPING") {
            shoppingExpense += expense;
        } else if (category == "LIVING") {
            livingExpense += expense;
        } else if (category == "ENTERTAINMENT") {
            entertainmentExpense += expense;
        } else if (category == "EDUCATION") {
            educationExpense += expense;
        } else if (category == "BEAUTY") {
            beautyExpense += expense;
        } else if (category == "TRANSPORTATION") {
            transportationExpense += expense;
        } else if (category == "HEALTH") {
            healthExpense += expense;
        } else if (category == "TRAVEL") {
            travelExpense += expense;
        } else if (category == "PET") {
            petExpense += expense;
        } else if (category == "OTHER") {
            otherExpense += expense;
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
}