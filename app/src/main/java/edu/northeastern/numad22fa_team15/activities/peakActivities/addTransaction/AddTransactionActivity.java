package edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.Category;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionPage______";

    private ImageButton dinningImgBtn;
    private ImageButton groceryBtn;
    private ImageButton shoppingBtn;
    private ImageButton livingBtn;
    private ImageButton entertainmentBtn;
    private ImageButton educationBtn;
    private ImageButton beautyBtn;
    private ImageButton transportationBtn;
    private ImageButton healthBtn;
    private ImageButton travelBtn;
    private ImageButton petBtn;
    private ImageButton otherBtn;

    private IDBHelper dbHelper;
    private EditText expenseEditText;
    private EditText descriptionEditText;
    private TextView categoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        dinningImgBtn = (ImageButton) findViewById(R.id.btn_dining);
        groceryBtn =  (ImageButton) findViewById(R.id.btn_groceries);
        shoppingBtn =  (ImageButton) findViewById(R.id.btn_shopping);
        livingBtn =  (ImageButton) findViewById(R.id.btn_living);
        entertainmentBtn =  (ImageButton) findViewById(R.id.btn_entertainment);
        educationBtn =  (ImageButton) findViewById(R.id.btn_education);
        beautyBtn =  (ImageButton) findViewById(R.id.btn_beauty);
        transportationBtn =  (ImageButton) findViewById(R.id.btn_transportation);
        healthBtn =  (ImageButton) findViewById(R.id.btn_health);
        travelBtn =  (ImageButton) findViewById(R.id.btn_travel);
        petBtn =  (ImageButton) findViewById(R.id.btn_pet);
        otherBtn =  (ImageButton) findViewById(R.id.btn_other);

        dbHelper = new DBHelper(AddTransactionActivity.this);

        // TODO: JM can follow addTransactionCategoryDining and addTransactionCategoryGroceries method examples
    }

    public void addTransactionCategoryDining(View view) {
        Log.d(TAG, "Category Dining was clicked.");

        addTransactionWithTipHelper(Category.DINING);
    }

    public void addTransactionCategoryGroceries(View view) {
        Log.d(TAG, "Category Groceries was clicked.");

        // TODO: Should be replaced by addTransactionWithoutTipHelper (not yet implemented)
        addTransactionWithTipHelper(Category.GROCERIES);
    }

    private void addTransactionWithTipHelper(Category category) {
        // Create and display the BottomSheetDialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet_add_transaction, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        // REF: https://stackoverflow.com/questions/41591733/bottom-sheet-landscape-issue
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            mBehavior.setPeekHeight(800);
        });

        categoryText = (TextView) bottomSheetView.findViewById(R.id.tv_category_with_tip);
        String categoryString = category.toString();
        categoryText.setText(categoryString);
        bottomSheetDialog.show();

        Button confirmTransactionDoneButton = (Button) bottomSheetView.findViewById(R.id.btn_done_add_transaction);
        confirmTransactionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAddTransaction(bottomSheetView, bottomSheetDialog, categoryString);
            }
        });
    }

    private void confirmAddTransaction(View bottomSheetView, BottomSheetDialog bottomSheetDialog, String category) {
        Log.d(TAG, "Add transaction DONE button was clicked");

        expenseEditText = (EditText) bottomSheetView.findViewById(R.id.et_transaction_amount_tip);
        descriptionEditText = (EditText) bottomSheetView.findViewById(R.id.et_description_tip);

        String expenseString = expenseEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Log.d(TAG, String.format("Category: %s. Expense: %s. Description: %s",
                category, expenseString, description));

        if (nullOrEmptyInputChecker(expenseString, description)) {
            // 向恶势力屈服
            String message = "All fields are required.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        float expense = Float.parseFloat(expenseString);
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);

        // Try to add transaction to the transactionEntry Table
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate);
        String transactionMessage = "Failed to add Transaction.";
        if (addTransaction) {
            transactionMessage = "Transaction added successfully.";
        }
        Toast.makeText(getApplicationContext(), transactionMessage, Toast.LENGTH_SHORT).show();
        bottomSheetDialog.dismiss();
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





