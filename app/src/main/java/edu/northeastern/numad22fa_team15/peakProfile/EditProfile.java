package edu.northeastern.numad22fa_team15.peakProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import edu.northeastern.numad22fa_team15.R;

public class EditProfile extends AppCompatActivity {

    ImageView bottomsheet;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bottomsheet = findViewById(R.id.btn_edit_profile_picture);
        profilePicture = findViewById(R.id.profile_picture);

        bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.change_profile_picture_bottomsheet);

        ImageButton takePhoto = dialog.findViewById(R.id.btn_take_profile_picture);
        ImageButton uploadPhoto = dialog.findViewById(R.id.btn_upload_profile_picture);
        ImageButton deletePhoto = dialog.findViewById(R.id.btn_delete_profile_picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: access camera to take photo

            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: access photo album to choose photo


            }
        });

        deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: delete current photo.
                //  Should not delete photo if current profile picture is default avatar.
                profilePicture.setImageResource(R.drawable.ic_anonymous_person_24);

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}