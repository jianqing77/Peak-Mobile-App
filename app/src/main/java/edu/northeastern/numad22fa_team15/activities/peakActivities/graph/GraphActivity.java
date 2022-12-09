package edu.northeastern.numad22fa_team15.activities.peakActivities.graph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction.AddTransactionActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.homePage.PeakHomePage;
import edu.northeastern.numad22fa_team15.activities.peakActivities.piggySavings.SavingsActivity;
import edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage.ProfileActivity;
import edu.northeastern.numad22fa_team15.models.databaseModels.SummaryModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;

public class GraphActivity extends AppCompatActivity {

    private PieChart piechart;
    private DBHelper dbHelper;
    private static final String TAG = "GraphActivity___";
    private NavigationBarView navigationBarView;

    private TextView dining_tv;
    private TextView groceries_tv;
    private TextView shopping_tv;
    private TextView living_tv;
    private TextView entertainment_tv;
    private TextView education_tv;
    private TextView beauty_tv;
    private TextView transportation_tv;
    private TextView health_tv;
    private TextView travel_tv;
    private TextView pet_tv;
    private TextView other_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_peak_summary_pie_chart);

        dbHelper = new DBHelper(GraphActivity .this);
        piechart = findViewById(R.id.graph_pieChart);
        setPiechart();
        setCategoryInfo();

        // set up navigation bar
        navigationBarView = findViewById(R.id.bottom_navigation_id);
        navigationBarView.setSelectedItemId(R.id.nav_graph);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), PeakHomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_graph:
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
        Intent intent = new Intent(GraphActivity.this, AddTransactionActivity.class);
        startActivity(intent);
        finish();
    }

    // set up piechart value
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

    // set up current expense by category value
    private void setCategoryInfo() {
        dining_tv = findViewById(R.id.diningExpenseNum_tv);
        groceries_tv = findViewById(R.id.groceriesExpenseNum_tv);
        shopping_tv = findViewById(R.id.shoppingExpenseNum_tv);
        living_tv = findViewById(R.id.livingExpenseNum_tv);
        entertainment_tv = findViewById(R.id.entertainmentExpenseNum_tv);
        education_tv = findViewById(R.id.educationExpenseNum_tv);
        beauty_tv = findViewById(R.id.beautyExpenseNum_tv);
        transportation_tv = findViewById(R.id.transportationExpenseNum_tv);
        health_tv = findViewById(R.id.healthExpenseNum_tv);
        travel_tv = findViewById(R.id.travelExpenseNum_tv);
        pet_tv = findViewById(R.id.petExpenseNum_tv);
        other_tv = findViewById(R.id.otherExpenseNum_tv);

        SummaryModel summary = dbHelper.retrieveLatestSummaryInfoTableSummary();
        dining_tv.setText("$ " + summary.getDiningExpense());
        groceries_tv.setText("$ " + summary.getGroceriesExpense());
        shopping_tv.setText("$ " + summary.getShoppingExpense());
        living_tv.setText("$ " + summary.getLivingExpense());
        entertainment_tv.setText("$ " + summary.getEntertainmentExpense());
        education_tv.setText("$ " + summary.getEducationExpense());
        beauty_tv.setText("$ " + summary.getBeautyExpense());
        transportation_tv.setText("$ " + summary.getTransportationExpense());
        health_tv.setText("$ " + summary.getHealthExpense());
        travel_tv.setText("$ " + summary.getTravelExpense());
        pet_tv.setText("$ " + summary.getPetExpense());
        other_tv.setText("$ " + summary.getOtherExpense());

    }
}