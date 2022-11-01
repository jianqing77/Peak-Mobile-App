package edu.northeastern.numad22fa_team15;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

public class FirebaseFriendList extends AppCompatActivity {

    Button sticker_history;
    Button add_friend;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_friend_list);

        sticker_history = findViewById(R.id.button_sticker_history);
        add_friend = findViewById(R.id.button_add_friend);

        add_friend.setOnClickListener(view -> addFriend());

    }

    private void addFriend() {
        // pop up window for adding friend
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setMessage("Enter Username to Add Friend");

        // set up input EditText view
        EditText userName_text = new EditText(this);
        userName_text.setInputType(InputType.TYPE_CLASS_TEXT);
        b.setView(userName_text);

        // set up buttons
        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // saving to local variable for now, need to be modified
                userName = userName_text.getText().toString();
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
}