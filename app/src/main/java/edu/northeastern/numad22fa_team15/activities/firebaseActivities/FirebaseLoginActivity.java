package edu.northeastern.numad22fa_team15.activities.firebaseActivities;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.FirebaseUtils.checkUserExistenceInFirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.numad22fa_team15.R;

public class FirebaseLoginActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseLoginActivity___";

    private TextInputEditText usernameEditText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);

        usernameEditText = (TextInputEditText) findViewById(R.id.reg_username_input);

        // Get the instance of the Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v(TAG, "Firebase Database " + firebaseDatabase);
        // Get the "Users" reference for our database.
        usersDatabaseReference = firebaseDatabase.getReference().child("users");
    }

    public void openLogin(View view) {
        this.finish();
    }

    /**
     * This method gets called when the user clicks the LOGIN button. It will try to log in
     * with the username in the usernameEditText field.
     * @param view view
     */
    public void firebaseUsernameLogin(View view) {
        closeKeyboard(this.getApplicationContext(), view);
        String usernameInput = usernameEditText.getText().toString();
        if (!usernameInputChecker(usernameInput, view)) {
            return;
        }
        loginUserInFirebase(usernameInput);
    }

    /**
     * This helper method returns true if the given username input is valid. Otherwise, it
     * returns false.
     * @param usernameInput a username input
     * @param view view
     * @return true if username input is valid. Otherwise, return false
     */
    private boolean usernameInputChecker(String usernameInput, View view) {
        if (usernameInput == null || usernameInput.isEmpty()) {
            String usernameErrorMessage = "Username cannot be null or empty.";
            displayMessageInSnackbar(view, usernameErrorMessage, Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    /**
     * This helper method checks if the given username exists in the database. If yes, it will
     * open the FirebaseFriendListActivity activity with this user's info. If not, it will display
     * a "User does not exist" message in Snackbar.
     * @param usernameInput username input
     */
    private void loginUserInFirebase(String usernameInput) {
        Log.v(TAG, "Trying to retrieve existing users' info from firebase.");
        Task<DataSnapshot> t = usersDatabaseReference.get();

        t.addOnCompleteListener(task -> {
            if(!t.isSuccessful()){
                // A more specific message should be "Failed to retrieve users' info from firebase."
                String errorMessage = "Login feature is currently unavailable.";
                Log.v(TAG, errorMessage);
                displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
            } else {
                // Check if the given username exists in the firebase realtime database
                boolean existenceResult = checkUserExistenceInFirebase(TAG, usernameInput, t);
                // If yes, open the FirebaseFriendListActivity class.
                if (existenceResult) {
                    // Clear the EditText input field.
                    usernameEditText.setText("");
                    Intent intent = new Intent(FirebaseLoginActivity.this, FirebaseFriendListActivity.class);
                    // Add current user's username to the intent.
                    intent.putExtra("current_user", usernameInput);
                    startActivity(intent);
                } else { // If no, display message in a Snackbar.
                    String errorMessage = "User does not exist.";
                    Log.v(TAG, errorMessage);
                    displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
                }
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
