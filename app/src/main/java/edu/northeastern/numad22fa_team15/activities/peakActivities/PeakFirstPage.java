//package edu.northeastern.numad22fa_team15.activities.peakActivities;
//
//import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
//import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
//import static edu.northeastern.numad22fa_team15.utils.CommonUtils.fourDigitPasscodeChecker;
//import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.google.android.material.textfield.TextInputEditText;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import edu.northeastern.numad22fa_team15.R;
//import edu.northeastern.numad22fa_team15.activities.peakActivities.onBoarding.onBoarding;
//import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
//import edu.northeastern.numad22fa_team15.R;
//import edu.northeastern.numad22fa_team15.utils.DBHelper;
//import edu.northeastern.numad22fa_team15.utils.IDBHelper;
//
///**
// * TODO: This activity is just a dummy activity to test out the signup, login, reset password, and set profile pic functionalities.
// */
//public class PeakFirstPage extends AppCompatActivity {
//
//    private static final String TAG = "PeakFirstPage___";
//    private TextInputEditText usernameTextInputEditText;
//    private TextInputEditText firstNameTextInputEditText;
//    private TextInputEditText lastNameTextInputEditText;
//    private TextInputEditText passcodeTextInputEditText;
//
//    private IDBHelper dbHelper;
//
//    private static final int CAMERA_ACTION_CODE = 1;
//    private static final int PICK_IMAGE_CODE = 11;
//    private ImageView profilePictureImageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        Log.v(TAG, "OnCreate()");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_peak_first_page);
//
//        usernameTextInputEditText = (TextInputEditText) findViewById(R.id.username_text_input_edit_text);
//        firstNameTextInputEditText = (TextInputEditText) findViewById(R.id.first_name_text_input_edit_text);
//        lastNameTextInputEditText = (TextInputEditText) findViewById(R.id.last_name_text_input_edit_text);
//        passcodeTextInputEditText = (TextInputEditText) findViewById(R.id.passcode_text_input_edit_text);
//
//        dbHelper = new DBHelper(PeakFirstPage.this);
//
//        profilePictureImageView = (ImageView) findViewById(R.id.profile_picture_image_view);
//        // Retrieve profile picture from user table
//        setProfilePicture();
//    }
//
//    private void setProfilePicture() {
//        UserModel user = dbHelper.retrieveUserInfoTableUser();
//        // TODO: This following if statement will be removed when moving this method to the EditProfile page.
//        if (user == null) {
//            return;
//        }
//        byte[] profilePictureByteArray = user.getProfilePicture();
//        if (profilePictureByteArray == null || profilePictureByteArray.length == 0) {
//            // TODO: XH - Need to merge Jaime's branch to get default profile picture.
//            return;
//        }
//        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(profilePictureByteArray,0, profilePictureByteArray.length);
//        profilePictureImageView.setImageBitmap(compressedBitmap);
//
//    }
//
//    public void userSignUp(View view) {
//        closeKeyboard(this.getApplicationContext(), view);
//        String username = usernameTextInputEditText.getText().toString();
//        String firstName = firstNameTextInputEditText.getText().toString();
//        String lastName = lastNameTextInputEditText.getText().toString();
//        String passcode = passcodeTextInputEditText.getText().toString();
//        if (nullOrEmptyInputChecker(username, firstName, lastName, passcode)) {
//            String message = "Username, first name, last name, and passcode inputs are all required.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return; // Terminate the method
//        }
//        if (!fourDigitPasscodeChecker(passcode)) {
//            String message = "Passcode needs to have 4 digits.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return;
//        }
//        boolean addResult = dbHelper.addUserTableUser(username, firstName, lastName, passcode);
//        String resultMessage = "Failed to add user"; // Default message
//        if (addResult) {
//            resultMessage = "User was added successfully.";
//        }
//        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
//    }
//
//    public void userLogin(View view) {
//        closeKeyboard(this.getApplicationContext(), view);
//        String username = usernameTextInputEditText.getText().toString();
//        String passcode = passcodeTextInputEditText.getText().toString();
//        if (nullOrEmptyInputChecker(username, passcode)) {
//            String message = "Username and passcode inputs are required.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return;
//        }
//        boolean confirmResult = dbHelper.confirmUserTableUser(username, passcode);
//        String resultMessage = "Login failed.";
//        if (confirmResult) {
//            resultMessage = "Login succeeded.";
//        }
//        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
//    }
//
//    public void userResetPasscode(View view) {
//        closeKeyboard(this.getApplicationContext(), view);
//        String username = usernameTextInputEditText.getText().toString();
//        String passcode = passcodeTextInputEditText.getText().toString();
//        if (nullOrEmptyInputChecker(username, passcode)) {
//            String message = "Username and passcode inputs are required.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return;
//        }
//        if (!fourDigitPasscodeChecker(passcode)) {
//            String message = "Passcode needs to have 4 digits.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return;
//        }
//        boolean updateResult = dbHelper.updateUserPasswordTableUser(username, passcode);
//        String resultMessage = "Failed to update passcode.";
//        if (updateResult) {
//            resultMessage = "Passcode was updated.";
//        }
//        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
//    }
//
//    public void userUpdateInfo(View view) {
//        closeKeyboard(this.getApplicationContext(), view);
//        String username = usernameTextInputEditText.getText().toString();
//        String firstName = firstNameTextInputEditText.getText().toString();
//        String lastName = lastNameTextInputEditText.getText().toString();
//        if (nullOrEmptyInputChecker(username, firstName, lastName)) {
//            String message = "Username, first name, and last name inputs are required.";
//            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
//            return;
//        }
////        boolean updateResult = dbHelper.updateUserInfoTableUser(username, firstName, lastName);
////        String resultMessage = "Failed to update user info.";
////        if (updateResult) {
////            resultMessage = "User info was updated.";
////        }
////        displayMessageInSnackbar(view, resultMessage, Snackbar.LENGTH_SHORT);
//    }
//
//    public void takeProfilePicture(View view) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        // Check if a camera application exists on the device.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, CAMERA_ACTION_CODE);
//        } else {
//            String errorMessage = "No app supports this action.";
//            displayMessageInSnackbar(view, errorMessage, Snackbar.LENGTH_SHORT);
//        }
//    }
//
//    public void pickFromPhotos(View view) {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//
//        // Check if a image gallery exists on the device.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, PICK_IMAGE_CODE);
//        } else {
//            String errorMessage = "No image gallery found.";
//            displayMessageInSnackbar(view, errorMessage, Snackbar.LENGTH_SHORT);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        String profilePictureMessage = "Failed to update profile picture.";
//        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && intent != null) {
//            Log.d(TAG, "CAMERA_ACTION onActivityResult()");
//            try {
//                Bundle bundle = intent.getExtras();
//                Bitmap photoTaken = (Bitmap) bundle.get("data");
//                profilePictureImageView.setImageBitmap(photoTaken);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                photoTaken.compress(Bitmap.CompressFormat.JPEG,80,stream);
//                byte[] profilePictureByteArray = stream.toByteArray();
//                updateProfilePicInDatabase(profilePictureByteArray,profilePictureMessage);
//            } catch (Exception e) {
//                Log.d(TAG, "Fail to set profile picture using image taken by device camera.");
//                Log.d(TAG, e.getMessage());
//                displayMessageInSnackbar(profilePictureImageView.getRootView(), profilePictureMessage, Snackbar.LENGTH_SHORT);
//            }
//        }
//        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && intent != null) {
//            Log.d(TAG, "PICK_IMAGE onActivityResult()");
//            try {
//                Uri imageUri = intent.getData();
//                profilePictureImageView.setImageURI(imageUri);
//                InputStream inputStream = getContentResolver().openInputStream(imageUri);
//                byte[] profilePictureByteArray = getByteArrayFromInputStream(inputStream);
//                updateProfilePicInDatabase(profilePictureByteArray,profilePictureMessage);
//            } catch (Exception e) {
//                Log.d(TAG, "Fail to set profile picture using image picked from photo library.");
//                Log.d(TAG, e.getMessage());
//                displayMessageInSnackbar(profilePictureImageView.getRootView(), profilePictureMessage, Snackbar.LENGTH_SHORT);
//            }
//        }
//    }
//
//    /**
//     * Update profile picture of the user in the database and display result in a Snackbar
//     * @param profilePictureByteArray byte array representing the profile picture
//     * @param profilePictureMessage message regarding the profile picture update operation
//     */
//    private void updateProfilePicInDatabase(byte[] profilePictureByteArray, String profilePictureMessage) {
//        boolean updateProfilePicResult = dbHelper.updateUserProfilePictureTableUser(profilePictureByteArray);
//        if (updateProfilePicResult) {
//            profilePictureMessage = "Profile picture was updated successfully.";
//        }
//        displayMessageInSnackbar(profilePictureImageView.getRootView(), profilePictureMessage, Snackbar.LENGTH_SHORT);
//    }
//
//    /**
//     * Convert an InputStream object into a byte array.
//     * @param inputStream input stream
//     * @return byte array
//     * @throws IOException if input stream read() method fails
//     */
//    private byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException {
//        // REF: https://stackoverflow.com/questions/10296734/image-uri-to-bytesarray
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//    @Override
//    protected void onPause() {
//        Log.v(TAG, "onPause()");
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.v(TAG, "onDestroy()");
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        Log.v(TAG, "onResume()");
//        super.onResume();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.v(TAG, "onRestart()");
//        super.onRestart();
//    }
//
//    @Override
//    protected void onStart() {
//        Log.v(TAG, "onStart()");
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.v(TAG, "onStop()");
//        super.onStop();
//    }
//}
