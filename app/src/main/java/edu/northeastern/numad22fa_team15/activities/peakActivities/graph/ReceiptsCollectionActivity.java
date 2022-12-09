package edu.northeastern.numad22fa_team15.activities.peakActivities.graph;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getYearAndMonthFromDateString;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.time.LocalDateTime;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;
import edu.northeastern.numad22fa_team15.utils.ReceiptsGridViewAdapter;

public class ReceiptsCollectionActivity extends AppCompatActivity {

    private static final String TAG = "ReceiptsCollectionActivity___";

    private IDBHelper dbHelper;
    private GridView receiptsGridView;
    private ReceiptsGridViewAdapter receiptsGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts_collection);

        dbHelper = new DBHelper(ReceiptsCollectionActivity .this);

        // Retrieve this month's transactions with receipt in a list
        LocalDateTime now = LocalDateTime.now();
        String transactionDate = String.valueOf(now);
        int[] yearAndMonth = getYearAndMonthFromDateString(transactionDate);
        int currentYear = yearAndMonth[0];
        int currentMonth = yearAndMonth[1];
        List<TransactionModel> transactionsWithReceipts = dbHelper.retrieveTransactionsWithReceiptByYearMonthTableTransaction(currentYear, currentMonth);
        Log.d(TAG, "Number of transactions with receipt this month: " + transactionsWithReceipts.size());

        receiptsGridView = (GridView) findViewById(R.id.receipts_collection_grid_view);
        ViewCompat.setNestedScrollingEnabled(receiptsGridView, true);
        receiptsGridViewAdapter = new ReceiptsGridViewAdapter(this, transactionsWithReceipts);
        receiptsGridView.setAdapter(receiptsGridViewAdapter);
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
