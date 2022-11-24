package edu.northeastern.numad22fa_team15.activities.peakActivities.lockScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.northeastern.numad22fa_team15.R;

public class PeakLockScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_lock_screen);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}