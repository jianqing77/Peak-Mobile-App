package edu.northeastern.numad22fa_team15.activities.peakActivities.piggySavings;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.nullOrEmptyInputChecker;
import static nl.dionsegijn.konfetti.core.Position.Relative;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction.AddTransactionActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.graph.GraphActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SavingModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class SavingsActivity extends AppCompatActivity {

    private static final String TAG = "SavingsActivity___";

    private NavigationBarView navigationBarView;
    private EditText savingGoal_et;
    private EditText goalDescription_et;
    private Button confirmGoal_btn;
    private TextView goal_tv;
    private TextView goalDescription_tv;
    private TextView savedAmount_tv;
    private TextView remainingAmount_tv;
    private IDBHelper dbHelper;

    private KonfettiView konfettiView = null;
    private Shape.DrawableShape drawableShape = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_piggybank);

        dbHelper = new DBHelper(SavingsActivity.this);

        // Initialize private fields
        goal_tv = findViewById(R.id.tv_goal_amount);
        goalDescription_tv = findViewById(R.id.tv_goal_description);
        savedAmount_tv = findViewById(R.id.tv_saved_amount);
        remainingAmount_tv = findViewById(R.id.tv_remaining_amount);

        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
        drawableShape = new Shape.DrawableShape(drawable, true);
        konfettiView = findViewById(R.id.konfettiView);

        // Show current saving info and check goal status
        SavingModel saving = dbHelper.retrieveLatestSavingTableSaving();
        setCurrentSavingInfo(saving);
        // After setting current saving info, check if goal is reached.
        boolean goalReached = goalStatusChecker(saving);
        if (goalReached) {
            Log.d(TAG, "Goal Reached!");
            explode();
            float remainingSaving = saving.getSavingSoFar() - saving.getSavingGoal();
            boolean resetSaving = dbHelper.resetSavingTableSaving(remainingSaving);
            String resetMessage = "Failed to reset saving";
            if (resetSaving) {
                resetMessage = "Successfully reset saving";
            }
            Log.d(TAG, resetMessage);
        }

        // Set up navigation bar
        navigationBarView = findViewById(R.id.bottom_navigation_id);
        navigationBarView.setSelectedItemId(R.id.nav_savings);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), PeakHomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_graph:
                        startActivity(new Intent(getApplicationContext(), GraphActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_savings:
                        startActivity(new Intent(getApplicationContext(), SavingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    // Bottom Navigation Bar -- add transaction
    public void addTransactionFAB(View view) {
        Log.v(TAG, "Trying to add a new transaction");
        Intent intent = new Intent(SavingsActivity.this, AddTransactionActivity.class);
        startActivity(intent);
        finish();
    }

    public void editGoalDialog(View view) {
        Log.d(TAG, "Edit piggy bank goal button was clicked");

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_edit_piggybank_goal, null);
        b.setView(dialogView);
        AlertDialog alert = b.create();
        savingGoal_et = dialogView.findViewById(R.id.et_edit_goal_amount);
        goalDescription_et = dialogView.findViewById(R.id.et_edit_goal_description);
        SavingModel saving = dbHelper.retrieveLatestSavingTableSaving();
        savingGoal_et.setHint(String.valueOf(saving.getSavingGoal()));
        goalDescription_et.setHint(saving.getGoalDescription());
        confirmGoal_btn = dialogView.findViewById(R.id.btn_confirm_edit_goal);
        confirmGoal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmEditGoal(view, alert);
            }
        });
        alert.show();
    }

    private void confirmEditGoal(View view, AlertDialog alert) {
        closeKeyboard(view.getContext(), view);
        String newGoal_str = savingGoal_et.getText().toString();
        String newGoalDescription = goalDescription_et.getText().toString();
        if (nullOrEmptyInputChecker(newGoal_str, newGoalDescription)) {
            String message = "All fields are required.";
            displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
            return;
        }
        int newGoal = Integer.parseInt(newGoal_str);
        Log.d(TAG, String.format("updateLatestSavingTableSaving: %s - %d", newGoalDescription, newGoal));
        boolean updateSaving = dbHelper.updateLatestSavingTableSaving(newGoal, newGoalDescription);

        String savingMessage = "Fail to update saving goal";
        if (updateSaving) {
            savingMessage = "Successfully updated saving goal";
            alert.dismiss();
        }
        Toast.makeText(getApplicationContext(), savingMessage, Toast.LENGTH_SHORT).show();

        // show current saving info
        SavingModel saving = dbHelper.retrieveLatestSavingTableSaving();
        setCurrentSavingInfo(saving);
    }

    /**
     * Set the current saving info on the page.
     * @param saving a SavingModel object
     */
    private void setCurrentSavingInfo(SavingModel saving) {
        Log.d(TAG, "setCurrentSavingInfoAndCheckGoalStatus() method with SavingModel");
        Log.d(TAG, "Debugging...");
        int savingGoal = saving.getSavingGoal();
        String goalDescription = saving.getGoalDescription();
        float savingSoFar = saving.getSavingSoFar();
        Log.d(TAG, "savingGoal: " + savingGoal);
        Log.d(TAG, "goalDescription: " + goalDescription);
        Log.d(TAG, "savingSoFar: " + savingSoFar);
        goal_tv.setText("$ " + savingGoal);
        goalDescription_tv.setText("Goal: " + goalDescription);
        savedAmount_tv.setText("$ " + savingSoFar);
        float remainingAmount = savingGoal - savingSoFar;
        if (remainingAmount < 0) {
            remainingAmount = 0;
        }
        remainingAmount_tv.setText("$ " + remainingAmount);
    }


    /**
     * Check if the current saving goal has been reached.
     * @param saving a SavingModel object
     * @return true if the goal has been reached. Otherwise, false.
     */
    private boolean goalStatusChecker(SavingModel saving) {
        return (saving.getSavingSoFar() >= saving.getSavingGoal() && saving.getSavingGoal() != 0);
    }

    /**
     * Show the confetti.
     */
    private void explode() {
        Log.d(TAG, "Exploding confetti!");
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Relative(0.5, 0.3))
                        .build()
        );
    }

}