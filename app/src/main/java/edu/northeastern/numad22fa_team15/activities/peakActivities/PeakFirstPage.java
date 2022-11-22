package edu.northeastern.numad22fa_team15.activities.peakActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.fourDigitPasscodeChecker;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

/**
 * TODO: This activity is just a dummy activity to test out the signup, login, and reset password functionalities.
 */
public class PeakFirstPage extends AppCompatActivity {

    private static final String TAG = "PeakFirstPage___";
    private TextInputEditText usernameTextInputEditText;
    private TextInputEditText firstNameTextInputEditText;
    private TextInputEditText lastNameTextInputEditText;
    private TextInputEditText passcodeTextInputEditText;

    private IDBHelper dbHelper;

    private static final int CAMERA_ACTION_CODE = 1;
    private ImageView profilePictureImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peak_first_page);

        usernameTextInputEditText = (TextInputEditText) findViewById(R.id.username_text_input_edit_text);
        firstNameTextInputEditText = (TextInputEditText) findViewById(R.id.first_name_text_input_edit_text);
        lastNameTextInputEditText = (TextInputEditText) findViewById(R.id.last_name_text_input_edit_text);
        passcodeTextInputEditText = (TextInputEditText) findViewById(R.id.passcode_text_input_edit_text);

        dbHelper = new DBHelper(PeakFirstPage.this);

        profilePictureImageView = (ImageView) findViewById(R.id.profile_picture_image_view);
        // TODO: Retrieve profile picture from user table
        setProfilePicture();
    }

    private void setProfilePicture() {
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        byte[] profilePictureByteArray = user.getProfilePicture();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(profilePictureByteArray,0,profilePictureByteArray.length);
        profilePictureImageView.setImageBitmap(compressedBitmap);

    }

    public void userSignUp(View view) {
        closeKeyboard(this.getApplicationContext(), view);
        String username = usernameTextInputEditText.getText().toString();
        String firstName = firstNameTextInputEditText.getText().toString();
        String lastName = lastNameTextInputEditText.getText().toString();
        String passcode = passcodeTextInputEditText.getText().toString();
        if (nullOrEmptyInputChecker(username, firstName, lastName, passcode)) {
            String message = "Username, first name, last name, and passcode inputs are all required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return; // Terminate the method
        }
        if (!fourDigitPasscodeChecker(passcode)) {
            String message = "Passcode needs to have 4 digits.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        boolean addResult = dbHelper.addUserTableUser(username, firstName, lastName, passcode);
        String resultMessage = "Failed to add user"; // Default message
        if (addResult) {
            resultMessage = "User was added successfully.";
        }
        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
    }

    public void userLogin(View view) {
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
            resultMessage = "Login succeeded.";
        }
        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
    }

    public void userResetPasscode(View view) {
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

    public void userUpdateInfo(View view) {
        closeKeyboard(this.getApplicationContext(), view);
        String username = usernameTextInputEditText.getText().toString();
        String firstName = firstNameTextInputEditText.getText().toString();
        String lastName = lastNameTextInputEditText.getText().toString();
        if (nullOrEmptyInputChecker(username, firstName, lastName)) {
            String message = "Username, first name, and last name inputs are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        boolean updateResult = dbHelper.updateUserInfoTableUser(username, firstName, lastName);
        String resultMessage = "Failed to update user info.";
        if (updateResult) {
            resultMessage = "User info was updated.";
        }
        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
    }

    public void takeProfilePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if a camera application exists on the device.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_ACTION_CODE);
        } else {
            String errorMessage = "No app supports this action.";
            displayMessageInSnackbar(view, errorMessage, Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && intent != null) {
            Log.v(TAG, "onActivityResult");
            Bundle bundle = intent.getExtras();
            Bitmap photoTaken = (Bitmap) bundle.get("data");
            profilePictureImageView.setImageBitmap(photoTaken);
//            InputStream inputStream = getAssets().open("image.png");
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            ivSource.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoTaken.compress(Bitmap.CompressFormat.JPEG,80,stream);
            byte[] profilePictureByteArray = stream.toByteArray();
            boolean updateProfilePicResult = dbHelper.updateUserProfilePictureTableUser(profilePictureByteArray);
            String message = "Failed to update profile picture.";
            if (updateProfilePicResult) {
                message = "Profile picture was updated successfully.";
            }
            displayMessageInSnackbar(profilePictureImageView.getRootView(), message, Snackbar.LENGTH_SHORT);
//            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
//            ivCompressed.setImageBitmap(compressedBitmap);
        }
    }

    public void pickFromPhotos(View view) {

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
