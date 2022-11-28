package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setProfilePictureToGivenImageView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseFriendListActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity______";

    private ImageView profilePictureImageView;
    private ImageView editProfileImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView usernameTextView;

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

        // TODO: Add onClickListener to edit profile picture image view.
        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a dialog that uses change_profile_picture_bottomsheet
                // REF: https://www.section.io/engineering-education/bottom-sheet-dialogs-using-android-studio/
                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.change_profile_picture_bottomsheet);

        // TODO: Implement button functions.
        ImageButton takePictureButton = (ImageButton) bottomSheetDialog.findViewById(R.id.btn_take_profile_picture);

        bottomSheetDialog.show();
    }

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
    }

    public void confirmUserInfoChanges(View view) {
        // TODO: Edit user info
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

        // TODO: Check if profile picture is changed. Updated or Default.

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
