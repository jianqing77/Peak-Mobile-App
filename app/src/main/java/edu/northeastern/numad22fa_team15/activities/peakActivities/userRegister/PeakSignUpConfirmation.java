package edu.northeastern.numad22fa_team15.activities.peakActivities.userRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;

public class PeakSignUpConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_sign_up_confirmation);
    }

    public void openHomePage(View view) {
        Intent intent = new Intent(PeakSignUpConfirmation.this, PeakHomePage.class);
        startActivity(intent);
    }

    /**
     * Avoid back to the signup page again.
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}