package edu.northeastern.numad22fa_team15;

import static edu.northeastern.numad22fa_team15.utils.commonUtils.closeKeyboard;
import static edu.northeastern.numad22fa_team15.utils.commonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.firebaseUtils.checkUserExistenceInFirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

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

    /**
     * This method gets called when the user clicks the SIGN UP button. It will try to add
     * a new user to the firebase realtime database.
     * @param view view
     */
    public void firebaseUsernameSignUp(View view) {
        closeKeyboard(this.getApplicationContext(), view);
        String usernameInput = usernameEditText.getText().toString();
        if (!usernameInputChecker(usernameInput, view)) {
            return;
        }
        addUserToFirebase(usernameInput);
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
                    Intent intent = new Intent(getApplicationContext(), FirebaseFriendListActivity.class);
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

//    /**
//     * Return true if the given username exists in the database. Otherwise, return false.
//     * @param usernameInput username input
//     * @param t task
//     * @return true if user exists in the database. Otherwise, return false
//     */
//    public static boolean checkUserExistenceInFirebase(String usernameInput, Task<DataSnapshot> t) {
//        boolean existenceResult = false;
//        for (DataSnapshot dataSnapshot : t.getResult().getChildren()) {
//            Map<String, Object> userObjectMap = (Map<String, Object>) dataSnapshot.getValue();
//            for (Map.Entry<String, Object> entry : userObjectMap.entrySet()) {
//                if (entry.getKey().equals("username")) {
//                    String existingUsername = (String) entry.getValue();
//                    Log.v(TAG, String.format("Existing user: %s", existingUsername));
//                    if (usernameInput.equals(existingUsername)) {
//                        existenceResult = true;
//                        break;
//                    }
//                }
//            }
//        }
//        return existenceResult;
//    }

    /**
     * This helper method will try to add a User object with the given username to the firebase
     * realtime database if the given username does not exist in the database yet.
     * @param username a username
     */
    private void addUserToFirebase(@NonNull String username) {
        Log.v(TAG, String.format("Checking if user %s exists in the database", username));
        Task<DataSnapshot> task1 = usersDatabaseReference.get();

        task1.addOnCompleteListener(task -> {
            if(!task1.isSuccessful()){
                // A more specific message should be "Failed to retrieve users' info from firebase."
                String errorMessage = "Sign up feature is currently unavailable.";
                Log.v(TAG, errorMessage);
                displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
            } else {
                // Check if the given username exists in the firebase realtime database
                boolean existenceResult = checkUserExistenceInFirebase(TAG, username, task1);
                // If so, display "user already exists" message in a Snackbar.
                if (existenceResult) {
                    String errorMessage = String.format("User %s already exists in the database.", username);
                    Log.v(TAG, errorMessage);
                    displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
                } else { // If not, try to add user to the database.
                    Log.v(TAG, "Trying to add user " + username);
                    // Construct a User object using the given username
                    User user = new User(username);
                    DatabaseReference newUserDatabaseReference = usersDatabaseReference.child(user.getUsername());
                    // Set this user info at the given location
                    Task<Void> task2 = newUserDatabaseReference.setValue(user);

                    task2.addOnCompleteListener(anotherTask -> {
                        if(!task2.isSuccessful()){
                            String errorMessage = String.format("Failed to add user %s.", user.getUsername());
                            Log.v(TAG, errorMessage);
                            displayMessageInSnackbar(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT);
                        } else {
                            String message = String.format("User %s added.", user.getUsername());
                            Log.v(TAG, message);
                            displayMessageInSnackbar(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
                        }
                    });
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
