package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getByteArrayFromInputStream;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setProfilePictureToGivenImageView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction.AddTransactionActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity______";
    private static final int CAMERA_PERMISSION_CODE = 1;

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
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfileActivity.this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            ImagePicker.with(this)
                    .cropSquare()
                    .compress(1024)
                    .maxResultSize(480, 480)
                    .start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImagePicker.with(this)
                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(480, 480)
                        .start();
            } else {
                String msg = "Camera permission denied, please allow permission first before accessing the camera features";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
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
        Uri uri = intent.getData();
        if (uri != null) {
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                profilePictureByteArray = getByteArrayFromInputStream(inputStream);
                profilePictureImageView.setImageURI(uri);
            } catch (FileNotFoundException e) {
                Log.v(TAG, "File not found");
            } catch (IOException e) {
                Log.v(TAG, "Failed to get byte array from input stream");
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
