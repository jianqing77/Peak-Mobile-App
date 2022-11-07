package edu.northeastern.numad22fa_team15.activities.movieApiActivities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import edu.northeastern.numad22fa_team15.R;

public class FullImageActivity extends AppCompatActivity {

    private static final String TAG = "FullImageActivity______";

    ImageView fullImageView;
    ImageButton imageCloseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_image);

        Bundle imageBundle = getIntent().getExtras();
        String imageURL = imageBundle.getString("Image URL");

        fullImageView = findViewById(R.id.fullImageView);
        imageCloseButton = findViewById(R.id.image_close_button);

        // Clicking the image close button will dismiss the FullImageActivity
        imageCloseButton.setOnClickListener(v -> FullImageActivity.this.finish());

        Picasso.get().load(imageURL).into(fullImageView);
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
