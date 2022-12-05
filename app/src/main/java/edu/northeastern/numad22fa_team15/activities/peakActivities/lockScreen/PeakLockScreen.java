package edu.northeastern.numad22fa_team15.activities.peakActivities.lockScreen;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.MainActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakEntrance;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class PeakLockScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PeakLockScreen___";
    private View view_01, view_02, view_03, view_04;
    private Button btn_01, btn_02, btn_03, btn_04, btn_05, btn_06, btn_07, btn_08, btn_09, btn_00, btn_clear;

    private List<String> numbers_list = new ArrayList<>();
    private String passcodeInput = "";
    private String num_01, num_02, num_03, num_04;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_lock_screen);

        dbHelper = new DBHelper(PeakLockScreen.this);

        initializeComponents();

        LocalDateTime now = LocalDateTime.now();
        String currentDate = String.valueOf(now);
        Integer currentYear = Integer.parseInt(currentDate.substring(0,4));
        Integer currentMonth = Integer.parseInt(currentDate.substring(5,7));

        SummaryModel lastSummary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        Log.d(TAG, "Last Summary Year: " + lastSummary.getYear() + ". Current Year: " + currentYear);
        Log.d(TAG, "Last Summary Month: " + lastSummary.getMonth() + ". Current Month: " + currentMonth);
        if (lastSummary.getYear() != currentYear && lastSummary.getMonth() != currentMonth) {
            Log.d(TAG, "No summary found for this current month. Adding summary to database...");
            float budget = lastSummary.getTotalBudget();
            float diningBudget = lastSummary.getDiningBudget();
            float groceriesBudget = lastSummary.getGroceriesBudget();
            float shoppingBudget = lastSummary.getShoppingBudget();
            float livingBudget = lastSummary.getLivingBudget();
            float entertainmentBudget = lastSummary.getEntertainmentBudget();
            float educationBudget = lastSummary.getEducationBudget();
            float beautyBudget = lastSummary.getBeautyBudget();
            float transportationBudget = lastSummary.getTransportationBudget();
            float healthBudget = lastSummary.getHealthBudget();
            float travelBudget = lastSummary.getTravelBudget();
            float petBudget = lastSummary.getPetBudget();
            float otherBudget = lastSummary.getOtherBudget();

            boolean addSummary = dbHelper.addSummaryTableSummary(currentYear, currentMonth, budget,
                    diningBudget, groceriesBudget, shoppingBudget, livingBudget, entertainmentBudget,
                    educationBudget, beautyBudget, transportationBudget, healthBudget, travelBudget,
                    petBudget, otherBudget);

            String budgetMessage = "Fail to add Summary";
            if (addSummary) {
                budgetMessage = "Successfully added summary";
            }
            Log.d(TAG, budgetMessage);
        }
    }

    private void initializeComponents() {
        view_01 = findViewById(R.id.view_01);
        view_02 = findViewById(R.id.view_02);
        view_03 = findViewById(R.id.view_03);
        view_04 = findViewById(R.id.view_04);

        btn_01 = findViewById(R.id.btn_01);
        btn_02 = findViewById(R.id.btn_02);
        btn_03 = findViewById(R.id.btn_03);
        btn_04 = findViewById(R.id.btn_04);
        btn_05 = findViewById(R.id.btn_05);
        btn_06 = findViewById(R.id.btn_06);
        btn_07 = findViewById(R.id.btn_07);
        btn_08 = findViewById(R.id.btn_08);
        btn_09 = findViewById(R.id.btn_09);
        btn_00 = findViewById(R.id.btn_00);
        btn_clear = findViewById(R.id.btn_clear);

        btn_01.setOnClickListener(this);
        btn_02.setOnClickListener(this);
        btn_03.setOnClickListener(this);
        btn_04.setOnClickListener(this);
        btn_05.setOnClickListener(this);
        btn_06.setOnClickListener(this);
        btn_07.setOnClickListener(this);
        btn_08.setOnClickListener(this);
        btn_09.setOnClickListener(this);
        btn_00.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_01:
                numbers_list.add("1");
                passNumber(numbers_list);
                break;
            case R.id.btn_02:
                numbers_list.add("2");
                passNumber(numbers_list);
                break;
            case R.id.btn_03:
                numbers_list.add("3");
                passNumber(numbers_list);
                break;
            case R.id.btn_04:
                numbers_list.add("4");
                passNumber(numbers_list);
                break;
            case R.id.btn_05:
                numbers_list.add("5");
                passNumber(numbers_list);
                break;
            case R.id.btn_06:
                numbers_list.add("6");
                passNumber(numbers_list);
                break;
            case R.id.btn_07:
                numbers_list.add("7");
                passNumber(numbers_list);
                break;
            case R.id.btn_08:
                numbers_list.add("8");
                passNumber(numbers_list);
                break;
            case R.id.btn_09:
                numbers_list.add("9");
                passNumber(numbers_list);
                break;
            case R.id.btn_00:
                numbers_list.add("0");
                passNumber(numbers_list);
                break;
            case R.id.btn_clear:
                numbers_list.clear();
                passNumber(numbers_list);
                break;
        }

    }

    private void passNumber(List<String> numbers_list) {

        if (numbers_list.size() == 0) {
            view_01.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_02.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_03.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_04.setBackgroundResource(R.drawable.bg_view_grey_oval);
        } else {
            switch (numbers_list.size()) {
                case 1:
                    num_01 = numbers_list.get(0);
                    view_01.setBackgroundResource(R.drawable.bg_view_dark_oval);
                    break;
                case 2:
                    num_02 = numbers_list.get(1);
                    view_02.setBackgroundResource(R.drawable.bg_view_dark_oval);
                    break;
                case 3:
                    num_03 = numbers_list.get(2);
                    view_03.setBackgroundResource(R.drawable.bg_view_dark_oval);
                    break;
                case 4:
                    num_04 = numbers_list.get(3);
                    view_04.setBackgroundResource(R.drawable.bg_view_dark_oval);
                    passcodeInput = num_01 + num_02 + num_03 + num_04;
                    matchPasscode(passcodeInput);
                    break;
            }
        }
    }

    private void matchPasscode(String passcodeInput) {
        if (getPasscode().equals(passcodeInput)) {
            startActivity(new Intent(this, PeakHomePage.class));
        } else {

            String message = "Wrong passcode. Please try again.";
            displayMessageInSnackbar(view_01.getRootView(), message, Snackbar.LENGTH_SHORT);

            // clear the current password input
            numbers_list.clear();
            passNumber(numbers_list);
        }
    }

    private String getPasscode() {
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        String passcode = user.getPasscode();

        return passcode;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
