package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getByteArrayFromInputStream;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setProfilePictureToGivenImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity______";

    private static final int CAMERA_ACTION_CODE = 1;
    private static final int PICK_IMAGE_CODE = 11;

    private ImageView profilePictureImageView;
    private ImageView editProfileImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView usernameTextView;
    private byte[] profilePictureByteArray;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dbHelper = new DBHelper(EditProfileActivity.this);

        profilePictureImageView = (ImageView) findViewById(R.id.profile_picture);
        editProfileImageView = (ImageView) findViewById(R.id.btn_edit_profile_picture);
        firstNameTextView = (TextView) findViewById(R.id.et_edit_first_name);
        lastNameTextView = (TextView) findViewById(R.id.et_edit_last_name);
        usernameTextView = (TextView) findViewById(R.id.et_edit_username);

        // Retrieve user info from database and set the hints
        retrieveUserInfoFromDatabase();

        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a dialog that uses change_profile_picture_bottomsheet
                // REF: https://www.section.io/engineering-education/bottom-sheet-dialogs-using-android-studio/
                showBottomSheetDialog(v);
            }
        });
    }

    /**
     * This method gets called when the BACK button is clicked. It brings user back to the
     * ProfileActivity page.
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Display bottom sheet dialog that contains 3 buttons: (1) take picture, (2) select photo,
     * and (3) delete profile picture.
     */
    private void showBottomSheetDialog(View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.change_profile_picture_bottomsheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        // REF: https://stackoverflow.com/questions/41591733/bottom-sheet-landscape-issue
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            mBehavior.setPeekHeight(500);
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

    private void deleteProfilePicture() {
        // Set image view to null
        profilePictureImageView.setImageBitmap(null);
        // Set this.profilePictureByteArray to null
        this.profilePictureByteArray = null;
    }

    private void takePhotoUsingCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if a camera application exists on the device.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_ACTION_CODE);
        } else {
            String errorMessage = "No app supports this action.";
            Log.d(TAG, errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void pickFromPhotos(View view) {
        Log.d(TAG, "pickFromPhotos() method");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        // Check if a image gallery exists on the device.
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "Image gallery exists.");
            startActivityForResult(intent, PICK_IMAGE_CODE);
        } else {
            String errorMessage = "No image gallery found.";
            Log.d(TAG, errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieve user info from database and set hints (i.e., first name, last name, and username)
     * to relevant values.
     */
    private void retrieveUserInfoFromDatabase() {
        // Set profile picture. If none found, use default profile picture.
        setProfilePictureToGivenImageView(dbHelper, profilePictureImageView);

        // Set first name, last name, and username in hints.
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        firstNameTextView.setHint(firstName);
        lastNameTextView.setHint(lastName);
        String username = user.getUsername();
        usernameTextView.setHint(username);
        profilePictureByteArray = user.getProfilePicture();
    }

    /**
     * This method gets called when user clicks the "Confirm Changes" button.
     * @param view view
     */
    public void confirmUserInfoChanges(View view) {
        closeKeyboard(getApplicationContext(), view);
        // Check if any value gets changed. Updated or Default.
        String firstNameInput = firstNameTextView.getText().toString();
        if (firstNameInput == null || firstNameInput.isEmpty()) {
            firstNameInput = firstNameTextView.getHint().toString();
        }
        String lastNameInput = lastNameTextView.getText().toString();
        if (lastNameInput == null || lastNameInput.isEmpty()) {
            lastNameInput = lastNameTextView.getHint().toString();
        }
        String usernameInput = usernameTextView.getText().toString();
        if (usernameInput == null || usernameInput.isEmpty()) {
            usernameInput = usernameTextView.getHint().toString();
        }
        Log.d(TAG,"confirmUserInfoChanges() Values: " + firstNameInput + " " + lastNameInput + " " + usernameInput);

        Log.d(TAG, "profilePictureByteArray: " + profilePictureByteArray);
        boolean updateResult = dbHelper.updateUserInfoTableUser(usernameInput, firstNameInput,
                lastNameInput, profilePictureByteArray);
        String message = "Update failed. Please try again later.";
        if (updateResult) {
            message = "User info was updated.";
        }
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        String profilePictureMessage = "Failed to update profile picture.";
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && intent != null) {
            Log.d(TAG, "CAMERA_ACTION onActivityResult()");
            try {
                Bundle bundle = intent.getExtras();
                Bitmap photoTaken = (Bitmap) bundle.get("data");
                profilePictureImageView.setImageBitmap(photoTaken);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photoTaken.compress(Bitmap.CompressFormat.JPEG,80,stream);
                profilePictureByteArray = stream.toByteArray();
            } catch (Exception e) {
                Log.d(TAG, "Fail to set profile picture using image taken by device camera.");
                Log.d(TAG, e.getMessage());
                displayMessageInSnackbar(profilePictureImageView.getRootView(), profilePictureMessage, Snackbar.LENGTH_SHORT);
            }
        }
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && intent != null) {
            Log.d(TAG, "PICK_IMAGE onActivityResult()");
            try {
                Uri imageUri = intent.getData();
                profilePictureImageView.setImageURI(imageUri);
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                profilePictureByteArray = getByteArrayFromInputStream(inputStream);
            } catch (Exception e) {
                Log.d(TAG, "Fail to set profile picture using image picked from photo library.");
                Log.d(TAG, e.getMessage());
                displayMessageInSnackbar(profilePictureImageView.getRootView(), profilePictureMessage, Snackbar.LENGTH_SHORT);
            }
        }
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
