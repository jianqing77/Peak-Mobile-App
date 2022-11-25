package edu.northeastern.numad22fa_team15.activities.peakActivities.profilePage;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.utils.DBHelper;
import edu.northeastern.numad22fa_team15.utils.IDBHelper;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity______";
//
//    private ImageView profilePictureImageView;
//    private TextView fullNameTextView;
//    private TextView usernameTextView;
//    private TextView profileBudgetTextView;

    private IDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dbHelper = new DBHelper(EditProfileActivity.this);
    }
}
