package edu.northeastern.numad22fa_team15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad22fa_team15.model.User;

public class FirebaseLoginActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseLoginActivity___";

    private EditText usernameEditText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);

        usernameEditText = (EditText) findViewById(R.id.username_edit_text);

        // Get the instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v(TAG, "Firebase Database " + firebaseDatabase.toString());
        // Get the "Users" reference for our database.
        usersDatabaseReference = firebaseDatabase.getReference().child("users");
    }

    public void firebaseUsernameSignUp(View view) {
        closeKeyboard(view);
        String usernameInput = usernameEditText.getText().toString();
        if (usernameInput == null || usernameInput.isEmpty()) {
            String usernameErrorMessage = "Username cannot be null or empty.";
            displayMessageInSnackbar(view, usernameErrorMessage, Snackbar.LENGTH_SHORT);
            return;
        }
        addUserToFirebase(usernameInput);
    }

    public void firebaseUsernameLogin(View view) {
        closeKeyboard(view);
        // XH TO DO:
        // Need to add "username login" logic.
        Intent intent = new Intent(getApplicationContext(), FirebaseFriendListActivity.class);
        startActivity(intent);
    }

    private void addUserToFirebase(@NonNull String username) {
        Log.v(TAG, "Trying to add user " + username);
        // Construct a User object using the given username
        User user = new User(username);
        DatabaseReference newUserDatabaseReference = usersDatabaseReference.child(user.getUsername());
        // Set this user info at the given location
        Task<Void> t = newUserDatabaseReference.setValue(user);

        t.addOnCompleteListener(task -> {
            if(!t.isSuccessful()){
                String errorMessage = String.format("Failed to add user %s.", user.getUsername());
                Log.v(TAG, errorMessage);
                displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
            } else {
                String message = String.format("User %s added.", user.getUsername());
                Log.v(TAG, message);
                displayMessageInSnackbar(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
            }
        });

        // Read from the database by listening for a change to that item.
        newUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data
                // at this location is updated.
                User userValue = dataSnapshot.getValue(User.class);
                Log.v(TAG, String.format("Updated username: %s", userValue.getUsername()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to add value
                String errorMessage = String.format("Failed to add value. %s", error.toException());
                Log.v(TAG, errorMessage);
            }
        });
    }

    /**
     * Display the given message in the given view for a certain duration.
     * @param view the view
     * @param message a message
     * @param duration the duration of the snackbar
     */
    private void displayMessageInSnackbar(View view, @NonNull String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    /**
     * This method close the keyboard on the given view.
     * @param view the view
     */
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
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
