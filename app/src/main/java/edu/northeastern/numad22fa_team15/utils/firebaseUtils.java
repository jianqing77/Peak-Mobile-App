package edu.northeastern.numad22fa_team15.utils;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

public class firebaseUtils {

    /**
     * Return true if the given username exists in the database. Otherwise, return false.
     * @param TAG acitivity tag
     * @param usernameInput username input
     * @param t task
     * @return true if user exists in the database. Otherwise, return false
     */
    public static boolean checkUserExistenceInFirebase(String TAG, String usernameInput, Task<DataSnapshot> t) {
        boolean existenceResult = false;
        for (DataSnapshot dataSnapshot : t.getResult().getChildren()) {
            Map<String, Object> userObjectMap = (Map<String, Object>) dataSnapshot.getValue();
            for (Map.Entry<String, Object> entry : userObjectMap.entrySet()) {
                if (entry.getKey().equals("username")) {
                    String existingUsername = (String) entry.getValue();
                    Log.v(TAG, String.format("Existing user: %s", existingUsername));
                    if (usernameInput.equals(existingUsername)) {
                        existenceResult = true;
                        break;
                    }
                }
            }
        }
        return existenceResult;
    }

}
