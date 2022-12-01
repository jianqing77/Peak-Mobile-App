package edu.northeastern.numad22fa_team15.activities.peakActivities.userRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.MainActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakCreateBudget;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakFirstPage;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;

public class PeakSignUpConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_sign_up_confirmation);
    }

    public void openEditBudget(View view) {
        Intent intent = new Intent(PeakSignUpConfirmation.this, PeakCreateBudget.class);
        startActivity(intent);
    }

    /**
     * Press Back button on the signup confirmation page, should back to the MainActivity.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PeakSignUpConfirmation.this, MainActivity.class);
        startActivity(intent);
    }
}