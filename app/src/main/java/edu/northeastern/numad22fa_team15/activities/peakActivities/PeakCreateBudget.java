package edu.northeastern.numad22fa_team15.activities.peakActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setProfilePictureToGivenImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakCreateBudget extends AppCompatActivity {

    private SeekBar sb_dining, sb_groceries, sb_shopping, sb_living;
    private SeekBar sb_entertainment, sb_education, sb_beauty, sb_transportation;
    private SeekBar sb_health, sb_travel, sb_pet, sb_other;

    private EditText et_dining, et_groceries, et_shopping, et_living;
    private EditText et_entertainment, et_education, et_beauty, et_transportation;
    private EditText et_health, et_travel, et_pet, et_other;

    private TextView totalBudget_tv;
    private TextView fullName_tv;
    private TextView username_tv;

    private ImageView profile_pic_iv;

    private Button confirm, cancel;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget);

        // Display message if intent carries a message.
        String potentialMessage = getIntent().getStringExtra("message");
        if (potentialMessage != null) {
            displayMessageInSnackbar(findViewById(android.R.id.content).getRootView(),
                    potentialMessage, Snackbar.LENGTH_SHORT);
        }

        dbHelper = new DBHelper(PeakCreateBudget.this);
        SummaryModel currentSummary = dbHelper.retrieveLatestSummaryInfoTableSummary();

        totalBudget_tv = (TextView) findViewById(R.id.edit_budget_amount);
        totalBudget_tv.setText("$ " + currentSummary.getTotalBudget() + "0");

        UserModel user = dbHelper.retrieveUserInfoTableUser();

        fullName_tv = (TextView) findViewById(R.id.tv_edit_budget_full_name);
        username_tv = (TextView) findViewById(R.id.tv_edit_budget_username);
        String fullName = user.getFirstName() + " " + user.getLastName();
        String username = user.getUsername();
        fullName_tv.setText(fullName);
        username_tv.setText(username);

        profile_pic_iv = (ImageView) findViewById(R.id.edit_budget_profile_picture);
        setProfilePictureToGivenImageView(dbHelper, profile_pic_iv);

        // Setting functions for buttons - Cancel Button
        cancel = (Button) findViewById(R.id.btn_cancel_new_budget);

        confirm = (Button) findViewById(R.id.btn_confirm_new_budget);

        // Dining SeekBar
        sb_dining = (SeekBar) findViewById(R.id.seekbar_dining);
        et_dining = (EditText) findViewById(R.id.et_seekbar_dining);
        et_dining.setText(String.valueOf(currentSummary.getDiningBudget()));
        sb_dining.setProgress((int) currentSummary.getDiningBudget());

        // Groceries SeekBar
        sb_groceries = (SeekBar) findViewById(R.id.seekbar_groceries);
        et_groceries = (EditText) findViewById(R.id.et_seekbar_groceries);
        et_groceries.setText(String.valueOf(currentSummary.getGroceriesBudget()));
        sb_groceries.setProgress((int) currentSummary.getGroceriesBudget());

        // Shopping SeekBar
        sb_shopping = (SeekBar) findViewById(R.id.seekbar_shopping);
        et_shopping = (EditText) findViewById(R.id.et_seekbar_shopping);
        sb_shopping.setProgress((int) currentSummary.getShoppingBudget());
        et_shopping.setText(String.valueOf(currentSummary.getShoppingBudget()));

        // Living SeekBar
        sb_living = (SeekBar) findViewById(R.id.seekbar_living);
        et_living = (EditText) findViewById(R.id.et_seekbar_living);
        sb_living.setProgress((int) currentSummary.getLivingBudget());
        et_living.setText(String.valueOf(currentSummary.getLivingBudget()));

        // Entertainment SeekBar
        sb_entertainment = (SeekBar) findViewById(R.id.seekbar_entertainment);
        et_entertainment = (EditText) findViewById(R.id.et_seekbar_entertainment);
        sb_entertainment.setProgress((int) currentSummary.getEntertainmentBudget());
        et_entertainment.setText(String.valueOf(currentSummary.getEntertainmentBudget()));

        // Education SeekBar
        sb_education = (SeekBar) findViewById(R.id.seekbar_education);
        et_education = (EditText) findViewById(R.id.et_seekbar_education);
        sb_education.setProgress((int) currentSummary.getEducationBudget());
        et_education.setText(String.valueOf(currentSummary.getEducationBudget()));

        // Beauty SeekBar
        sb_beauty = (SeekBar) findViewById(R.id.seekbar_beauty);
        et_beauty = (EditText) findViewById(R.id.et_seekbar_beauty);
        sb_beauty.setProgress((int) currentSummary.getBeautyBudget());
        et_beauty.setText(String.valueOf(currentSummary.getBeautyBudget()));

        // Transportation SeekBar
        sb_transportation = (SeekBar) findViewById(R.id.seekbar_transportation);
        et_transportation = (EditText) findViewById(R.id.et_seekbar_transportation);
        sb_transportation.setProgress((int) currentSummary.getTransportationBudget());
        et_transportation.setText(String.valueOf(currentSummary.getTransportationBudget()));

        // Health SeekBar
        sb_health = (SeekBar) findViewById(R.id.seekbar_health);
        et_health = (EditText) findViewById(R.id.et_seekbar_health);
        sb_health.setProgress((int) currentSummary.getHealthBudget());
        et_health.setText(String.valueOf(currentSummary.getHealthBudget()));

        // Travel SeekBar
        sb_travel = (SeekBar) findViewById(R.id.seekbar_travel);
        et_travel = (EditText) findViewById(R.id.et_seekbar_travel);
        sb_travel.setProgress((int) currentSummary.getTravelBudget());
        et_travel.setText(String.valueOf(currentSummary.getTravelBudget()));

        // Pet SeekBar
        sb_pet = (SeekBar) findViewById(R.id.seekbar_pet);
        et_pet = (EditText) findViewById(R.id.et_seekbar_pet);
        sb_pet.setProgress((int) currentSummary.getPetBudget());
        et_pet.setText(String.valueOf(currentSummary.getPetBudget()));

        // Other SeekBar
        sb_other = (SeekBar) findViewById(R.id.seekbar_other);
        et_other = (EditText) findViewById(R.id.et_seekbar_other);
        sb_other.setProgress((int) currentSummary.getOtherBudget());
        et_other.setText(String.valueOf(currentSummary.getOtherBudget()));

        setOnClickListenerHelper();
    }

    // Helper function to set progress of seekbar
    private void setProgressValue(EditText et, SeekBar seekBar) {
        seekBar.setProgress(Math.round(Float.parseFloat(et.getText().toString())));
    }

    // Function to ensure the user creates a budget when the budget table is empty
    private void cancelEditBudget(View view) {
        SummaryModel summary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        // Check if the summary table is empty. If it is, then:
        if (summary == null) {
            String message = "You need to set a budget for at least one category.";
             displayMessageInSnackbar(view, message, Snackbar.LENGTH_SHORT);
        } else {
            // Bring the user back to the home screen.
            Intent intent = new Intent(PeakCreateBudget.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    private void setOnFocusChangeListenerHelper(EditText categoryEditText, SeekBar categorySeekBar, View view) {
        if (categoryEditText.getText().toString().isEmpty() || Float.parseFloat(categoryEditText.getText().toString()) < 0) {
            categoryEditText.setText("0");
            categorySeekBar.setProgress(0);
        } else if (Float.parseFloat(categoryEditText.getText().toString()) > 5000) {
            categoryEditText.setText("5000");
            categorySeekBar.setProgress(5000);
        } else { // Valid value
            setProgressValue(categoryEditText, categorySeekBar);
        }
        // Close keyboard
        closeKeyboard(view.getContext(), view);
    }

    private void setOnClickListenerHelper() {
        cancel.setOnClickListener(view -> cancelEditBudget(view));
        confirm.setOnClickListener(view -> {
            float diningBudget = Float.parseFloat(et_dining.getText().toString());
            float groceriesBudget = Float.parseFloat(et_groceries.getText().toString());
            float shoppingBudget = Float.parseFloat(et_shopping.getText().toString());
            float livingBudget = Float.parseFloat(et_living.getText().toString());
            float entertainmentBudget = Float.parseFloat(et_entertainment.getText().toString());
            float educationBudget = Float.parseFloat(et_education.getText().toString());
            float beautyBudget = Float.parseFloat(et_beauty.getText().toString());
            float transportationBudget = Float.parseFloat(et_transportation.getText().toString());
            float healthBudget = Float.parseFloat(et_health.getText().toString());
            float travelBudget = Float.parseFloat(et_travel.getText().toString());
            float petBudget = Float.parseFloat(et_pet.getText().toString());
            float otherBudget = Float.parseFloat(et_other.getText().toString());
            float totalBudget = diningBudget + groceriesBudget + shoppingBudget + livingBudget + entertainmentBudget
                    + educationBudget + beautyBudget + transportationBudget + healthBudget + travelBudget
                    + petBudget + otherBudget;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String currentDate = String.valueOf(now);

            Integer currentYear = Integer.parseInt(currentDate.substring(0,4));
            Integer currentMonth = Integer.parseInt(currentDate.substring(5,7));

            boolean updateSummary = dbHelper.updateSummaryTableSummary(currentYear, currentMonth, totalBudget,
                    diningBudget, groceriesBudget, shoppingBudget, livingBudget, entertainmentBudget, educationBudget,
                    beautyBudget, transportationBudget, healthBudget, travelBudget, petBudget, otherBudget);


            if (updateSummary) {
                // Bring the user to the profile page if summary was updated successfully.
                Intent intent = new Intent(PeakCreateBudget.this, ProfileActivity.class);
                intent.putExtra("message", "Successfully updated summary");
                startActivity(intent);
            } else {
                String budgetMessage = "Fail to update Summary";
                displayMessageInSnackbar(view, budgetMessage, Snackbar.LENGTH_SHORT);
            }
        });
        sb_dining.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_dining, int progress, boolean b) {
                et_dining.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        et_dining.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_dining, sb_dining, view);
        });
        sb_groceries.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_groceries, int progress,
                                          boolean b) {
                et_groceries.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_groceries) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_groceries) {
            }
        });
        et_groceries.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_groceries, sb_groceries, view);
        });
        sb_shopping.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_shopping, int progress,
                                          boolean b) {
                et_shopping.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_shopping) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_shopping) {
            }
        });
        et_shopping.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_shopping, sb_shopping, view);
        });
        sb_living.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_living, int progress,
                                          boolean b) {
                et_living.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_living) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_living) {
            }
        });
        et_living.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_living, sb_living, view);
        });
        sb_entertainment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_entertainment, int progress,
                                          boolean b) {
                et_entertainment.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_entertainment) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_entertainment) {
            }
        });
        et_entertainment.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_entertainment, sb_entertainment, view);
        });
        sb_education.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_education, int progress,
                                          boolean b) {
                et_education.setText(String.valueOf(progress));
                updateTotalBudget();

            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_education) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_education) {
            }
        });
        et_education.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_education, sb_education, view);
        });
        sb_beauty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_beauty.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        et_beauty.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_beauty, sb_beauty, view);
        });
        sb_transportation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_transportation.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        et_transportation.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_transportation, sb_transportation, view);
        });
        sb_health.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_health.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        et_health.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_health, sb_health, view);
        });
        sb_travel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_travel.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        et_travel.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_travel, sb_travel, view);
        });
        sb_pet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_pet.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        et_pet.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_pet, sb_pet, view);
        });
        sb_other.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                et_other.setText(String.valueOf(progress));
                updateTotalBudget();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        et_other.setOnFocusChangeListener((view, b) -> {
            setOnFocusChangeListenerHelper(et_other, sb_other, view);
        });
    }

    private void updateTotalBudget() {
        float diningBudget = Float.parseFloat(et_dining.getText().toString());
        float groceriesBudget = Float.parseFloat(et_groceries.getText().toString());
        float shoppingBudget = Float.parseFloat(et_shopping.getText().toString());
        float livingBudget = Float.parseFloat(et_living.getText().toString());
        float entertainmentBudget = Float.parseFloat(et_entertainment.getText().toString());
        float educationBudget = Float.parseFloat(et_education.getText().toString());
        float beautyBudget = Float.parseFloat(et_beauty.getText().toString());
        float transportationBudget = Float.parseFloat(et_transportation.getText().toString());
        float healthBudget = Float.parseFloat(et_health.getText().toString());
        float travelBudget = Float.parseFloat(et_travel.getText().toString());
        float petBudget = Float.parseFloat(et_pet.getText().toString());
        float otherBudget = Float.parseFloat(et_other.getText().toString());
        float totalBudget = diningBudget + groceriesBudget + shoppingBudget + livingBudget + entertainmentBudget
                + educationBudget + beautyBudget + transportationBudget + healthBudget + travelBudget
                + petBudget + otherBudget;
        totalBudget_tv.setText("$ " + totalBudget + "0");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PeakCreateBudget.this, ProfileActivity.class);
        startActivity(intent);
    }

}