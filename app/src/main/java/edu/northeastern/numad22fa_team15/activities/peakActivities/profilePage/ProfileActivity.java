package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakFirstPage;
import edu.northeastern.numad22fa_team15.peakProfile.ProfilePage;
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
        fullNameTextView = (TextView) findViewById(R.id.tv_profile_username);
        usernameTextView = (TextView) findViewById(R.id.profile_budget);
        profileBudgetTextView = (TextView) findViewById(R.id.profile_budget);

        retrieveUserAndBudgetInfoFromDatabase();
    }

    private void retrieveUserAndBudgetInfoFromDatabase() {
        // TODO: Retrieve user info from user table and budget info from saving table
    }

    public void editProfileActivity(View view) {
        // TODO
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    public void editBudgetActivity(View view) {
        // TODO
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
