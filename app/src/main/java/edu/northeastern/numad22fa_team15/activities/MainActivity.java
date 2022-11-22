package edu.northeastern.numad22fa_team15.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseSignUpActivity;
import edu.northeastern.numad22fa_team15.activities.movieApiActivities.MovieSearchActivityWithRecyclerView;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakAddTransaction;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakFirstPage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Clicking the MOVIE/TV SEARCH button will start the MovieSearchActivityWithRecyclerView activity.
     * @param view view
     */
    public void movieSearchActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MovieSearchActivityWithRecyclerView.class);
        startActivity(intent);
    }

    /**
     * Clicking the FIREBASE button will start the FirebaseLoginActivity activity.
     * @param view view
     */
    public void firebaseLoginActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), FirebaseSignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Clicking the PEAK button will start the PeakFirstPage activity.
     * @param view view
     */
    public void peakProjectActivity(View view) {
        // TODO: It will be changed to the actual first page of the Peak app.
        Intent intent = new Intent(getApplicationContext(), PeakFirstPage.class);
        startActivity(intent);
    }

    /**
     * Clicking the TEST button will start PeakFirstPage activity.
     * @param view view
     */
    public void testActivity(View view) {
        // TODO: Test for now, will be deleted
        Intent intent = new Intent(getApplicationContext(), PeakAddTransaction.class);
        startActivity(intent);
    }

    /**
     * Clicking the ABOUT button will start the AboutTeamActivity activity.
     * @param view view
     */
    public void aboutTeamActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutTeamActivity.class);
        startActivity(intent);
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