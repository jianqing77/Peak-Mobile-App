package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class ChangePasscodeActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasscodeActivity______";

    private EditText newPasscodeEditText;
    private EditText confirmNewPasscodeEditText;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passcode);

        dbHelper = new DBHelper(ChangePasscodeActivity.this);

        newPasscodeEditText = (EditText) findViewById(R.id.et_reset_passcode_new);
        confirmNewPasscodeEditText = (EditText) findViewById(R.id.et_reset_passcode_confirm);
    }

    /**
     * Clicking the BACK button will lead user back to the Profile page.
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This method gets called when the user clicks the Reset Passcode button.
     * @param view view
     */
    public void resetPasscode(View view) {
        closeKeyboard(getApplicationContext(), view);
        String newPasscode = newPasscodeEditText.getText().toString();
        String confirmNewPasscode = confirmNewPasscodeEditText.getText().toString();
        // Check if both input fields have value
        if (nullOrEmptyInputChecker(newPasscode, confirmNewPasscode)) {
            String message = "Input fields cannot be empty.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        // Check if input fields have the same value
        if (!newPasscode.equals(confirmNewPasscode)) {
            String message = "Passcode does not match.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        // Reset Passcode in the database
        boolean resetResult = dbHelper.resetUserPasswordTableUser(newPasscode);
        // If reset passcode operation failed, stay on the same page.
        if (!resetResult) {
            String message = "Failed to reset passcode. Please try again later.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        // If reset passcode operation succeeded. Bring user back to the ProfileActivity
        String message = "Passcode was updated";
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("message", message);
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
