package edu.northeastern.numad22fa_team15;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import edu.northeastern.numad22fa_team15.firebaseFriendTVRecyclerUtil.FriendTvAdapter;
import edu.northeastern.numad22fa_team15.model.Friend;

public class FirebaseFriendListActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseFriendListActivity______";

    private TextView currentUserTextView;
    private TextView numOfStickersSentTextView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;
    private DatabaseReference numOfStickersDatabaseReference;
    private DatabaseReference friendsDatabaseReference;

    private List<Friend> friendResults;
    private RecyclerView friendsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_friend_list);

        // Retrieve current user's username from intent and set it to the currentUserTextView TextView.
        Intent intent = getIntent();
        String username = intent.getStringExtra("current_user");
        currentUserTextView = findViewById(R.id.current_userTV);
        currentUserTextView.setText(username);

        numOfStickersSentTextView = findViewById(R.id.sticker_numTV);

        // Get the instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v(TAG, "Firebase Database " + firebaseDatabase.toString());
        // Get the "user" reference for our database.
        userDatabaseReference = firebaseDatabase.getReference().child("users").child(username);

        friendResults = new ArrayList<Friend>();
        friendsRecyclerView = findViewById(R.id.friends_recycler_view);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsRecyclerView.setAdapter(new FriendTvAdapter(friendResults, this));

        // Get the "numOfStickerSent" reference for our database and add ValueEventListener to it.
        numOfStickersDatabaseReference = userDatabaseReference.child("numOfStickersSent");
        addEventListenerToNumOfStickersDatabaseReference();

        // Get the "friends" reference for our database and add ValueEventListener to it.
        friendsDatabaseReference = userDatabaseReference.child("friends");
        addEventListenerToFriendsDatabaseReference();
    }

    /**
     * Listening for a change to the friends database reference.
     */
    private void addEventListenerToFriendsDatabaseReference() {
        // Listening for a change to the numOfStickersSent item
        friendsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Need to check for null because user may not have any friends. SAD =)
                Object friendsValue = dataSnapshot.getValue();
                if (friendsValue == null) {
                    Log.v(TAG, "No friends detected for the current user.");
                    return;
                }
                Map<String, String> friendsMap = (Map<String, String>) friendsValue;
                friendResults.clear();
                for (Map.Entry<String, String> entry : friendsMap.entrySet()) {
                    String friendUsername = entry.getKey();
                    Log.v(TAG, String.format("FRIEND: %s.", friendUsername));
                    Friend friend = new Friend(friendUsername);
                    friendResults.add(friend);
                }
                friendsRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to update value
                String errorMessage = String.format("Failed to update value. %s", error.toException());
                Log.v(TAG, errorMessage);
            }
        });
    }

    /**
     * Listening for a change to the numOfStickersSent database reference.
     */
    private void addEventListenerToNumOfStickersDatabaseReference() {
        // Listening for a change to the numOfStickersSent item
        numOfStickersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data
                // at this location is updated.
                Integer numOfStickersValue = dataSnapshot.getValue(Integer.class);
                Log.v(TAG, String.format("Updated number of stickers sent: %d", numOfStickersValue));
                numOfStickersSentTextView.setText(String.valueOf(numOfStickersValue));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to update value
                String errorMessage = String.format("Failed to update value. %s", error.toException());
                Log.v(TAG, errorMessage);
            }
        });
    }

    /**
     * Clicking the STICKER HISTORY button will start the FirebaseStickerHistoryActivity activity.
     * @param view view
     */
    public void stickerHistoryActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), FirebaseStickerHistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Clicking the ADD FRIEND button will open up the add friend dialog.
     * @param view view
     */
    public void addFriendDialog(View view) {
        // Pop up window for adding friend
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setTitle("Add a Friend");
        b.setView(R.layout.dialog_add_friend);

        // Set up buttons
        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TO DO:
                // (0) Check if the given username is the same as the current username
                // (0a) [YES] Display message in Snackbar "Cannot add yourself as a friend."
                // (0b) [NO] Go to step (1)
                // (1) Check if the given username exists in the firebase realtime database
                // (1a) [YES] Go to step (2)
                // (1b) [NO] Display message in Snackbar "User does not exist."
                // (2) Check if the current user already has this person as a friend
                // (2a) [YES] Display message in Snackbar "You already have this user as a friend."
                // (2b) [NO] Go to step (3)
                // (3) Try to add the given username in current user's friends list
                // (3a) [YES] Display message in Snackbar "Friend added successfully."
                // (3b) [NO] Display error message in Snackbar "Failed to add user as friend."
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = b.create();
        alert.show();
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