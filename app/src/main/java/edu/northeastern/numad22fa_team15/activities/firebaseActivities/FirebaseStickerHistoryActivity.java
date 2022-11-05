package edu.northeastern.numad22fa_team15.activities.firebaseActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.firebaseStickerHistoryRecyclerUtil.StickerHistoryAdapter;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class FirebaseStickerHistoryActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseStickerHistoryActivity_________";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference stickerRecordsDatabaseReference;

    private List<StickerRecord> stickerRecordResults;
    private RecyclerView stickerRecordsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_history);

        // Retrieve current user's username from intent.
        Intent intent = getIntent();
        String username = intent.getStringExtra("current_user");

        // Get the instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v(TAG, "Firebase Database " + firebaseDatabase.toString());

        stickerRecordResults = new ArrayList<StickerRecord>();
        stickerRecordsRecyclerView = findViewById(R.id.sticker_history_recycler_view);
        stickerRecordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickerRecordsRecyclerView.setAdapter(new StickerHistoryAdapter(stickerRecordResults, this));

        // Get the "stickerRecords" reference for our database and add ValueEventListener to it.
        stickerRecordsDatabaseReference = firebaseDatabase.getReference().child("users").child(username).child("stickerRecords");
        addEventListenerToStickerRecordsDatabaseReference();
    }

    /**
     * Listening for a change to the stickerRecords database reference.
     */
    private void addEventListenerToStickerRecordsDatabaseReference() {
        // Listening for a change to the stickerRecords item
        stickerRecordsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Need to check for null because user may not have any sticker records.
                Object stickerRecordsValue = dataSnapshot.getValue();
                if (stickerRecordsValue == null) {
                    Log.v(TAG, "No sticker history detected for the current user.");
                    return;
                }
                List<Object> stickerRecordList = (List<Object>) stickerRecordsValue;
                stickerRecordResults.clear();
                Log.v(TAG, "Class type: " + stickerRecordList);
                for (Object stickerRecord : stickerRecordList) {
                    if (stickerRecord == null) {
                        continue;
                    }
                    Log.v(TAG, "Class type: " + stickerRecord.getClass());
                    try {
                        Map<String, Object> stickerRecordMap = (Map<String, Object>) stickerRecord;
                        int stickerResourceID =  (int) (long) stickerRecordMap.get("stickerResourceID");
                        String stickerName = (String) stickerRecordMap.get("stickerName");
                        String senderUsername = (String) stickerRecordMap.get("sender");
                        String receiverUsername = (String) stickerRecordMap.get("receiver");
                        long timeStamp = (long) stickerRecordMap.get("timestamp");
                        StickerRecord record = new StickerRecord(stickerResourceID, stickerName, senderUsername, receiverUsername, timeStamp);
                        stickerRecordResults.add(record);
                    } catch (Exception e) {
                        String errorMessage = "Something is wrong with how the sticker records are stored in database.";
                        Log.v(TAG, errorMessage);
                        Log.v(TAG, e.getMessage());
                    }
                }
                stickerRecordsRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to update value
                String errorMessage = String.format("Failed to update value. %s", error.toException());
                Log.v(TAG, errorMessage);
            }
        });
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
