package edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionPage___";

    ImageButton dinningImgBtn;
    ImageButton groceryBtn;
    ImageButton shoppingBtn;
    ImageButton livingBtn;
    ImageButton entertainmentBtn;
    ImageButton educationBtn;
    ImageButton beautyBtn;
    ImageButton transportationBtn;
    ImageButton healthBtn;
    ImageButton travelBtn;
    ImageButton petBtn;
    ImageButton otherBtn;

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


        dinningImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Clicked dining");
                // set the dialog style
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog (
                        AddTransactionActivity.this,
                        R.style.BottomSheetDialogTheme);

                // get the bottom sheet dialog view
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet_add_transaction, (LinearLayout)findViewById(R.id.bottomTransactionContainer));

                // TODO: modify the bottom sheet height
                bottomSheetDialog.getBehavior().setPeekHeight(1500);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                // TODO: link the buttons to functions
                bottomSheetView.findViewById(R.id.btn_done_add_transaction).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // closeKeyboard(this.getApplicationContext(), view);

                        expenseEditText = (EditText) bottomSheetView.findViewById(R.id.et_transaction_amount_tip);
                        descriptionEditText = (EditText) bottomSheetView.findViewById(R.id.et_description_tip);
                        categoryText = (TextView) bottomSheetView.findViewById(R.id.tv_category_with_tip);

                        String expenseString = expenseEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();
                        String category = categoryText.getText().toString();

                        if (nullOrEmptyInputChecker(expenseString, description)) {
                            String message = "All fields are required.";
                            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
                            return;
                        }

                        float expense = Float.parseFloat(expenseString);

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
                });
            }
        });

        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Clicked dining, without tio");
            }
        });
    }

    // element on the dialog
    public void addTransaction(View view) {
        closeKeyboard(this.getApplicationContext(), view);

        expenseEditText = (EditText) findViewById(R.id.et_transaction_amount_tip);
        descriptionEditText = (EditText) findViewById(R.id.et_description_tip);
        categoryText = (TextView) findViewById(R.id.tv_category_with_tip);

        String expenseString = expenseEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String category = categoryText.getText().toString();

        if (nullOrEmptyInputChecker(expenseString, description)) {
            String message = "All fields are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }

        float expense = Float.parseFloat(expenseString);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);

        // hardcode summary id for testing
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate, 1);
//        String transactionMessage = "Fail to add Transaction";
//        if (addTransaction) {
//            transactionMessage = "Successfully added transaction";
//        }
//        displayMessageInSnackbar(view, transactionMessage, Snackbar.LENGTH_SHORT);
    }



}



