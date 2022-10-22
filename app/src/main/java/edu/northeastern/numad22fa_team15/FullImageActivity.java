package edu.northeastern.numad22fa_team15;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FullImageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_image);

        Bundle imageBundle = getIntent().getExtras();
        String imageURL = imageBundle.getString("Image URL");


        ImageView fullImageView;
        ImageButton imageCloseButton;


        fullImageView = findViewById(R.id.fullImageView);
        imageCloseButton = findViewById(R.id.image_close_button);

        imageCloseButton.setOnClickListener(v -> FullImageActivity.this.finish());

        Picasso.get().load(imageURL).into(fullImageView);
    }
}
