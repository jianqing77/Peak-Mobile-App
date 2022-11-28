package edu.northeastern.numad22fa_team15.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;

public class CommonUtils {

    /**
     * Display the given message in the given view for a certain duration.
     * @param view the view
     * @param message a message
     * @param duration the duration of the snackbar
     */
    public static void displayMessageInSnackbar(View view, @NonNull String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    /**
     * This method close the keyboard on the given view.
     * @param view the view
     */
    public static void closeKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    /**
     * This method checks if any of the inputs is null or empty.
     * @param inputs a list of input strings
     * @return true if any put is null or empty. Otherwise, return false
     */
    public static boolean nullOrEmptyInputChecker(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether the passcode input is a 4 digit number.
     * @param passcodeInput passcode input
     * @return true if the passcode is a 4 digit number. Otherwise, return false
     */
    public static boolean fourDigitPasscodeChecker(@NonNull String passcodeInput) {
        try {
            Integer passcode = Integer.valueOf(passcodeInput);
        } catch (NumberFormatException e) {
            return false;
        }
        return passcodeInput.length() == 4;
    }

    public static void setProfilePictureToGivenImageView(@NonNull IDBHelper dbHelper, @NonNull ImageView imageView) {
        UserModel user = dbHelper.retrieveUserInfoTableUser();
        if (user == null) {
            return;
        }
        byte[] profilePictureByteArray = user.getProfilePicture();
        if (profilePictureByteArray == null || profilePictureByteArray.length == 0) {
            // Default profile picture.
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(profilePictureByteArray,0, profilePictureByteArray.length);
        imageView.setImageBitmap(bitmap);
    }

}
