package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setProfilePictureToGivenImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity______";

    private ImageView profilePictureImageView;
    private TextView fullNameTextView;
    private TextView usernameTextView;
    private TextView profileBudgetTextView;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        dbHelper = new DBHelper(ProfileActivity.this);

        profilePictureImageView = (ImageView) findViewById(R.id.profile_picture);
        fullNameTextView = (TextView) findViewById(R.id.tv_profile_full_name);
        usernameTextView = (TextView) findViewById(R.id.tv_profile_username);
        profileBudgetTextView = (TextView) findViewById(R.id.profile_budget);

        retrieveUserAndBudgetInfoFromDatabase();

        // Display message if intent carries a message.
        String potentialMessage = getIntent().getStringExtra("message");
        if (potentialMessage != null) {
            displayMessageInSnackbar(findViewById(android.R.id.content).getRootView(),
                    potentialMessage, Snackbar.LENGTH_SHORT);
        }


    }

    private void retrieveUserAndBudgetInfoFromDatabase() {
        // Set profile picture. If none found, use default profile picture.
        setProfilePictureToGivenImageView(dbHelper, profilePictureImageView);

        // Set full name and username.
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String fullName = firstName + " " + lastName;
        fullNameTextView.setText(fullName);
        String username = user.getUsername();
        usernameTextView.setText(username);

        // TODO: Retrieve budget info from saving table
    }

    public void editProfileActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void editBudgetActivity(View view) {
        // TODO: testing for now, change to actual edit budget activity
        // Intent intent = new Intent(getApplicationContext(), TestBudgetActivity.class);
        // startActivity(intent);
        // finish();
    }

    public void changePasscodeActivity(View view) {
        // TODO
    }

    public void securitySettingsActivity(View view) {
        // TODO
    }

    public void termsAndConditionsActivity(View view) {
        // TODO
    }

    public void resetAccount(View view) {
        // TODO: Ask user to confirm reset account action.
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
