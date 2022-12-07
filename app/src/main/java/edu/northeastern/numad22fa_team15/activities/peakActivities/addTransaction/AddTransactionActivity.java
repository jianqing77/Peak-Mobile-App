package edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getByteArrayFromInputStream;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setPictureToGivenImageView;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.updateSummaryTable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.utils.Category;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class AddTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddTransactionActivity______";

    private static final int CAMERA_ACTION_CODE = 2;
    private static final int PICK_IMAGE_CODE = 22;
    private byte[] receiptPictureByteArray;

    private IDBHelper dbHelper;
    private View bottomSheetView;
    private Spinner addTipSpinner;
    private EditText expenseEditText;
    private EditText descriptionEditText;
    private TextView categoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        dbHelper = new DBHelper(AddTransactionActivity.this);
    }

    public void addTransactionCategoryDining(View view) {
        Log.d(TAG, "Category Dining was clicked.");
        addTransactionWithTipHelper(Category.DINING);
    }

    public void addTransactionCategoryGroceries(View view) {
        Log.d(TAG, "Category Groceries was clicked.");
        addTransactionWithoutTipHelper(Category.GROCERIES);
    }

    public void addTransactionCategoryShopping(View view) {
        Log.d(TAG, "Category Shopping was clicked.");
        addTransactionWithTipHelper(Category.SHOPPING);
    }

    public void addTransactionCategoryLiving(View view) {
        Log.d(TAG, "Category Living was clicked.");
        addTransactionWithoutTipHelper(Category.LIVING);
    }

    public void addTransactionCategoryEntertainment(View view) {
        Log.d(TAG, "Category Entertainment was clicked.");
        addTransactionWithTipHelper(Category.ENTERTAINMENT);
    }

    public void addTransactionCategoryEducation(View view) {
        Log.d(TAG, "Category Education was clicked.");
        addTransactionWithoutTipHelper(Category.EDUCATION);
    }

    public void addTransactionCategoryBeauty(View view) {
        Log.d(TAG, "Category Beauty was clicked.");
        addTransactionWithTipHelper(Category.BEAUTY);
    }

    public void addTransactionCategoryTransportation(View view) {
        Log.d(TAG, "Category Transportation was clicked.");
        addTransactionWithoutTipHelper(Category.TRANSPORTATION);
    }

    public void addTransactionCategoryHealth(View view) {
        Log.d(TAG, "Category Health was clicked.");
        addTransactionWithoutTipHelper(Category.HEALTH);
    }

    public void addTransactionCategoryTravel(View view) {
        Log.d(TAG, "Category Travel was clicked.");
        addTransactionWithTipHelper(Category.TRAVEL);
    }

    public void addTransactionCategoryPet(View view) {
        Log.d(TAG, "Category Pet was clicked.");
        addTransactionWithoutTipHelper(Category.PET);
    }

    public void addTransactionCategoryOther(View view) {
        Log.d(TAG, "Category Other was clicked.");
        addTransactionWithTipHelper(Category.OTHER);
    }

    /**
     * Helper method for categories when adding transaction that requires tip calculation.
     * @param category category that requires tip calculation
     */
    private void addTransactionWithTipHelper(Category category) {
        // Create and display the BottomSheetDialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,
                R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet_add_transaction_with_tip, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        // REF: https://stackoverflow.com/questions/41591733/bottom-sheet-landscape-issue
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            mBehavior.setPeekHeight(1000);
        });

        categoryText = (TextView) bottomSheetView.findViewById(R.id.tv_category_with_tip);
        String categoryString = category.toString();
        categoryText.setText(categoryString);
        bottomSheetDialog.show();

        // Add setOnClickListener to VIEW button
        Button previewReceiptPhotoViewButton = (Button) bottomSheetView.findViewById(R.id.btn_view_receipt);
        previewReceiptPhotoViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReceiptPicturePreviewDialog(v);
            }
        });

        // Add tip spinner
        addTipSpinner = (Spinner) bottomSheetView.findViewById(R.id.spinner_add_tip);
        addTipSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.tip_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        addTipSpinner.setAdapter(adapter);

        // Add setOnClickListener to ADD RECEIPT button
        Button addReceiptButton = (Button) bottomSheetView.findViewById(R.id.btn_add_receipt);
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(v);
            }
        });

        // Add setOnClickListener to DONE button
        Button confirmTransactionDoneButton = (Button) bottomSheetView.findViewById(R.id.btn_done_add_transaction);
        confirmTransactionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAddTransaction(bottomSheetDialog, categoryString);
            }
        });
    }

    private void showReceiptPicturePreviewDialog(View v) {
        // Check if receiptPictureByteArray contains value
        // If no, make a toast
        if (receiptPictureByteArray == null || receiptPictureByteArray.length == 0) {
            Toast.makeText(getApplicationContext(), "No receipt photo added.", Toast.LENGTH_SHORT).show();
            return;
        }
        // If yes, create a dialog
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Receipt Picture Preview");
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_receipt_picture_preview, null);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.receipt_photo_preview_image_view);
        Log.d(TAG, "showReceiptPicturePreviewDialog() method: " + receiptPictureByteArray);
        if (imageView == null) {
            Log.d(TAG, "Something wrong");
        }
        // Set receipt picture to given image view
        setPictureToGivenImageView(receiptPictureByteArray, imageView);
        Button okButton = (Button) dialogView.findViewById(R.id.ok_button_drpp);
        b.setView(dialogView);
        AlertDialog alert = b.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] tipChoices = getResources().getStringArray(R.array.tip_choices);
        String selectedTipChoice = tipChoices[position];
        Log.d(TAG, String.format("Selected tip choice: %s", selectedTipChoice));
        // If other tip choices are selected, check if amount has value in it.
        if (!selectedTipChoice.equals(tipChoices[0])) {
            expenseEditText = (EditText) bottomSheetView.findViewById(R.id.et_transaction_amount_tip);
            String expenseString = expenseEditText.getText().toString();
            // If not, ask user to put a value in the amount field.
            if (nullOrEmptyInputChecker(expenseString)) {
                addTipSpinner.setSelection(0);
                String addTipErrorMessage = "Amount is required if you want to calculate the tip.";
                Toast.makeText(getApplicationContext(), addTipErrorMessage, Toast.LENGTH_SHORT).show();
            } else { // If yes, calculate tip and update amount.
                calculateTipAndUpdateAmount(expenseEditText, selectedTipChoice);
            }
        }
    }

    private void showBottomSheetDialog(View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.change_profile_picture_bottomsheet, null);
        TextView bottomSheetTitle = bottomSheetView.findViewById(R.id.tv_change_picture);
        bottomSheetTitle.setText("Add Receipt Photo:");

        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        // REF: https://stackoverflow.com/questions/41591733/bottom-sheet-landscape-issue
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            mBehavior.setPeekHeight(700);
        });

        ImageButton takePictureButton = (ImageButton) bottomSheetDialog.findViewById(R.id.btn_take_profile_picture);
        ImageButton uploadPictureButton = (ImageButton) bottomSheetDialog.findViewById(R.id.btn_upload_profile_picture);
        ImageButton deletePictureButton = (ImageButton) bottomSheetDialog.findViewById(R.id.btn_delete_profile_picture);

        // Add onClickListener to the buttons
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Take picture button clicked.");
                takePhotoUsingCamera(v);
                bottomSheetDialog.dismiss();
            }
        });
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Upload picture button clicked.");
                pickFromPhotos(v);
                bottomSheetDialog.dismiss();
            }
        });
        deletePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Delete picture button clicked.");
                deleteProfilePicture();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void takePhotoUsingCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if a camera application exists on the device.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_ACTION_CODE);
        } else {
            String errorMessage = "No app supports this action.";
            displayMessageInSnackbar(view, errorMessage, Snackbar.LENGTH_SHORT);
        }
    }

    private void pickFromPhotos(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        // Check if a image gallery exists on the device.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE_CODE);
        } else {
            String errorMessage = "No image gallery found.";
            displayMessageInSnackbar(view, errorMessage, Snackbar.LENGTH_SHORT);
        }
    }

    private void deleteProfilePicture() {
        // Set this.profilePictureByteArray to null
        this.receiptPictureByteArray = null;
        String receiptPictureMessage = "Receipt picture deleted.";
        Toast.makeText(getApplicationContext(), receiptPictureMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        String receiptPictureMessage = "Failed to add receipt picture.";
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && intent != null) {
            Log.d(TAG, "CAMERA_ACTION onActivityResult()");
            try {
                Bundle bundle = intent.getExtras();
                Bitmap photoTaken = (Bitmap) bundle.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photoTaken.compress(Bitmap.CompressFormat.JPEG,80,stream);
                receiptPictureByteArray = stream.toByteArray();
                Log.d(TAG, "Receipt Photo Byte Array: " + receiptPictureByteArray);
                receiptPictureMessage = "Receipt picture added.";
                Toast.makeText(getApplicationContext(), receiptPictureMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "Fail to add receipt photo using image taken by device camera.");
                Log.d(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), receiptPictureMessage, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && intent != null) {
            Log.d(TAG, "PICK_IMAGE onActivityResult()");
            try {
                Uri imageUri = intent.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                receiptPictureByteArray = getByteArrayFromInputStream(inputStream);
                Log.d(TAG, "Receipt Photo Byte Array: " + receiptPictureByteArray);
                receiptPictureMessage = "Receipt picture added.";
                Toast.makeText(getApplicationContext(), receiptPictureMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "Fail to add receipt photo using image picked from photo library.");
                Log.d(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), receiptPictureMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method should not be called if user selects 0% tip.
     * @param expenseEditText expense EditText
     * @param selectedTipChoice selected tip choice
     */
    private void calculateTipAndUpdateAmount(EditText expenseEditText, String selectedTipChoice) {
        float expense = Float.parseFloat(expenseEditText.getText().toString());
        // Retrieve tip percentage from selectedTipChoice String
        float selectedTip = Float.parseFloat(selectedTipChoice.substring(0, 2)) / 100;
        DecimalFormat df = new DecimalFormat("0.00");
        float updatedAmount = expense * (1 + selectedTip);
        expenseEditText.setText(df.format(updatedAmount));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Linked with DONE button on bottomSheetDialog for transaction with tip.
     * @param bottomSheetDialog bottom sheet dialog
     * @param category expense category
     */
    private void confirmAddTransaction(BottomSheetDialog bottomSheetDialog, String category) {
        Log.d(TAG, "Add transaction DONE button was clicked");

        expenseEditText = (EditText) bottomSheetView.findViewById(R.id.et_transaction_amount_tip);
        descriptionEditText = (EditText) bottomSheetView.findViewById(R.id.et_description_tip);

        String expenseString = expenseEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Log.d(TAG, String.format("Category: %s. Expense: %s. Description: %s",
                category, expenseString, description));

        if (nullOrEmptyInputChecker(expenseString, description)) {
            String message = "All fields are required.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        float expense = Float.parseFloat(expenseString);
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);
        Log.d(TAG, "Receipt Photo to be added: " + Arrays.toString(receiptPictureByteArray));
        // Try to add transaction to the transactionEntry Table
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate, receiptPictureByteArray);
        boolean updateSummary = updateSummaryTable(dbHelper, expense, category, transactionDate);
        if (!updateSummary) {
            Log.d(TAG, "Failed to update the summary table after adding a transaction.");
        }
        String transactionMessage = "Failed to add Transaction.";
        if (addTransaction) {
            transactionMessage = "Transaction added successfully.";
        }
        Toast.makeText(getApplicationContext(), transactionMessage, Toast.LENGTH_SHORT).show();

        // update summary table
        updateSummaryTable(expense, category, transactionDate);

        bottomSheetDialog.dismiss();
    }

    /**
     * Helper method for adding transaction without tip categories.
     * @param category the category chosen that does not require tip calculation
     */
    private void addTransactionWithoutTipHelper(Category category) {
        // Create and display the BottomSheetDialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,
                R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet_add_transaction_without_tip, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            mBehavior.setPeekHeight(1000);
        });

        // set category text
        categoryText = (TextView) bottomSheetView.findViewById(R.id.tv_category_without_tip);
        String categoryString = category.toString();
        categoryText.setText(categoryString);
        bottomSheetDialog.show();

        // Add setOnClickListener to VIEW button
        Button previewReceiptPhotoViewButton = (Button) bottomSheetView.findViewById(R.id.btn_view_receipt_no_tip);
        previewReceiptPhotoViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReceiptPicturePreviewDialog(v);
            }
        });

        // Add setOnClickListener to ADD RECEIPT button
        Button addReceiptButton = (Button) bottomSheetView.findViewById(R.id.btn_add_receipt_no_tip);
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(v);
            }
        });

        // Add setOnClickListener to DONE button
        Button confirmTransactionDoneButton = (Button) bottomSheetView.findViewById(R.id.btn_done_add_transaction_no_tip);
        confirmTransactionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAddTransactionWithoutTip(bottomSheetDialog, categoryString);
            }
        });
    }

    /**
     * Linked with DONE button for bottomSheetDialog without tip
     */
    private void confirmAddTransactionWithoutTip(BottomSheetDialog bottomSheetDialog, String category) {
        Log.d(TAG, "Add transaction DONE button was clicked for transaction without tip ");

        expenseEditText = (EditText) bottomSheetView.findViewById(R.id.et_transaction_amount_no_tip);
        descriptionEditText = (EditText) bottomSheetView.findViewById(R.id.et_description_no_tip);

        String expenseString = expenseEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Log.d(TAG, String.format("Category: %s. Expense: %s. Description: %s",
                category, expenseString, description));

        if (nullOrEmptyInputChecker(expenseString, description)) {
            String message = "All fields are required.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            return;
        }

        float expense = Float.parseFloat(expenseString);
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);
        Log.d(TAG, "Receipt Photo to be added: " + Arrays.toString(receiptPictureByteArray));
        // Try to add transaction to the transactionEntry Table
        boolean addTransaction = dbHelper.addTranTableTransaction(expense, description, category, transactionDate, receiptPictureByteArray);
        boolean updateSummary = updateSummaryTable(dbHelper, expense, category, transactionDate);
        if (!updateSummary) {
            Log.d(TAG, "Failed to update the summary table after adding a transaction.");
        }
        String transactionMessage = "Failed to add Transaction.";
        if (addTransaction) {
            transactionMessage = "Transaction added successfully.";
        }
        Toast.makeText(getApplicationContext(), transactionMessage, Toast.LENGTH_SHORT).show();

        // update summary table
        updateSummaryTable(expense, category, transactionDate);

        bottomSheetDialog.dismiss();
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

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, transactionMessage, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), PeakHomePage.class);
        startActivity(intent);
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

}
