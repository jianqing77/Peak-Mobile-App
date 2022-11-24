package edu.northeastern.numad22fa_team15.activities.peakActivities.homePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseFriendListActivity;

public class PeakHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_home_page);
    }

    @Override
    public void onBackPressed() {
        // back to main activity when press the back button
        finish();
    }
}