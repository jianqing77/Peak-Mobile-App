package edu.northeastern.numad22fa_team15.activities.peakActivities.userRegister;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.fourDigitPasscodeChecker;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakLogin extends AppCompatActivity {

    private static final String TAG = "PeakLogin___";

    private TextInputEditText usernameTextInputEditText;
    private TextInputEditText passcodeTextInputEditText;
    private IDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_login);

        usernameTextInputEditText = (TextInputEditText) findViewById(R.id.username_text_input_edit_text);
        passcodeTextInputEditText = (TextInputEditText) findViewById(R.id.passcode_text_input_edit_text);
        dbHelper = new DBHelper(PeakLogin.this);

    }

    /**
     * Linked with the login button on login page.
     */
    public void userLogin(View view) {
        Log.v(TAG, "Trying to login User");
        closeKeyboard(this.getApplicationContext(), view);
        String username = usernameTextInputEditText.getText().toString();
        String passcode = passcodeTextInputEditText.getText().toString();
        if (nullOrEmptyInputChecker(username, passcode)) {
            String message = "Username and passcode inputs are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        boolean confirmResult = dbHelper.confirmUserTableUser(username, passcode);
        String resultMessage = "Login failed.";
        if (confirmResult) {
            Log.v(TAG, "Login succeed");
            Intent intent = new Intent(PeakLogin.this, PeakHomePage.class);
            startActivity(intent);
            // resultMessage = "Login succeeded.";
        } else {
            Log.v(TAG, "Login failed.");
            displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
        }
    }

    /**
     * Linked with the reset passcode button on login page.
     */
    public void userResetPasscode(View view) {
        Log.v(TAG, "Reset Passcode");
        closeKeyboard(this.getApplicationContext(), view);
        String username = usernameTextInputEditText.getText().toString();
        String passcode = passcodeTextInputEditText.getText().toString();
        if (nullOrEmptyInputChecker(username, passcode)) {
            String message = "Username and passcode inputs are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        if (!fourDigitPasscodeChecker(passcode)) {
            String message = "Passcode needs to have 4 digits.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        boolean updateResult = dbHelper.updateUserPasswordTableUser(username, passcode);
        String resultMessage = "Failed to update passcode.";
        if (updateResult) {
            resultMessage = "Passcode was updated.";
        }
        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
    }
}