package edu.northeastern.numad22fa_team15.activities.peakActivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.lockScreen.PeakLockScreen;
import edu.northeastern.numad22fa_team15.activities.peakActivities.onBoarding.onBoardingAnimation;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakEntrance extends AppCompatActivity {
    private static final String TAG = "PeakEntrance___";
    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_entrance);
        dbHelper = new DBHelper(this);

        // if user does not exist --> jump to Signup Page
        if (dbHelper.retrieveUserInfoTableUser() == null) {
            Intent intent = new Intent(PeakEntrance.this, onBoardingAnimation.class);
            startActivity(intent);
        } else {
            // if user already exist --> jump to the PeakLockScreen
            Intent intent = new Intent(PeakEntrance.this, PeakLockScreen.class);
            startActivity(intent);
        }
    }

    /**
     * Make sure the page will not show up after created, so that open the PEAK will directly jump
     * to lock screen
     */
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}