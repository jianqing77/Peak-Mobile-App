package edu.northeastern.numad22fa_team15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction.AddTransactionActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.graph.GraphActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.activities.peakActivities.piggySavings.SavingsActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;

public class PeakSummaryPieChart extends AppCompatActivity {

    private static final String TAG = "PeakSummaryPieChart___";
    private PieChart piechart;
    private DBHelper dbHelper;
    private NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak_summary_pie_chart);

        dbHelper = new DBHelper(PeakSummaryPieChart.this);
        piechart = findViewById(R.id.graph_pieChart);
        setPiechart();

        // set up navigation bar
        navigationBarView = findViewById(R.id.bottom_navigation_id);
        navigationBarView.setSelectedItemId(R.id.nav_home);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), PeakHomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_graph:
                        // startActivity(new Intent(getApplicationContext(), GraphActivity.class));
                        // overridePendingTransition(0, 0);
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
        Intent intent = new Intent(PeakSummaryPieChart.this, AddTransactionActivity.class);
        startActivity(intent);
        finish();
    }

    private void setPiechart() {
        SummaryModel summary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        int diningExpense = (int) summary.getDiningExpense();
        int groceriesExpense = (int) summary.getGroceriesExpense();
        int shoppingExpense = (int) summary.getShoppingExpense();
        int livingExpense = (int) summary.getLivingExpense();
        int entertainmentExpense = (int) summary.getEntertainmentExpense();
        int educationExpense = (int) summary.getEducationExpense();
        int beautyExpense = (int) summary.getBeautyExpense();
        int transportationExpense = (int) summary.getTransportationExpense();
        int healthExpense = (int) summary.getHealthExpense();
        int travelExpense = (int) summary.getTravelExpense();
        int petExpense = (int) summary.getPetExpense();
        int otherExpense = (int) summary.getOtherExpense();

        piechart.addPieSlice(new PieModel("Dining", diningExpense, Color.parseColor("#c0392b")));
        piechart.addPieSlice(new PieModel("Groceries", groceriesExpense, Color.parseColor("#d35400")));
        piechart.addPieSlice(new PieModel("Shopping", shoppingExpense, Color.parseColor("#f1c40f")));
        piechart.addPieSlice(new PieModel("Living", livingExpense, Color.parseColor("#f39c12")));
        piechart.addPieSlice(new PieModel("Entertainment", entertainmentExpense, Color.parseColor("#1abc9c")));
        piechart.addPieSlice(new PieModel("Education", educationExpense, Color.parseColor("#2ecc71")));
        piechart.addPieSlice(new PieModel("Beauty", beautyExpense, Color.parseColor("#3498db")));
        piechart.addPieSlice(new PieModel("Transportation", transportationExpense, Color.parseColor("#2980b9")));
        piechart.addPieSlice(new PieModel("Health", healthExpense, Color.parseColor("#9b59b6")));
        piechart.addPieSlice(new PieModel("Travel", travelExpense, Color.parseColor("#34495e")));
        piechart.addPieSlice(new PieModel("Pet", petExpense, Color.parseColor("#7f8c8d")));
        piechart.addPieSlice(new PieModel("Other", otherExpense, Color.parseColor("#bdc3c7")));

        piechart.startAnimation();

    }
}