package edu.northeastern.numad22fa_team15.activities.peakActivities.addTransaction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import edu.northeastern.numad22fa_team15.R;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionPage___";

    ImageButton dinningImgBtn;
    ImageButton groceryBtn;
    ImageButton shoppingBtn;
    ImageButton livingBtn;
    ImageButton entertainmentBtn;
    ImageButton educationBtn;
    ImageButton beautyBtn;
    ImageButton transportationBtn;
    ImageButton healthBtn;
    ImageButton travelBtn;
    ImageButton petBtn;
    ImageButton otherBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        dinningImgBtn = (ImageButton) findViewById(R.id.btn_dining);
        groceryBtn =  (ImageButton) findViewById(R.id.btn_groceries);
        shoppingBtn =  (ImageButton) findViewById(R.id.btn_shopping);
        livingBtn =  (ImageButton) findViewById(R.id.btn_living);
        entertainmentBtn =  (ImageButton) findViewById(R.id.btn_entertainment);
        educationBtn =  (ImageButton) findViewById(R.id.btn_education);
        beautyBtn =  (ImageButton) findViewById(R.id.btn_beauty);
        transportationBtn =  (ImageButton) findViewById(R.id.btn_transportation);
        healthBtn =  (ImageButton) findViewById(R.id.btn_health);
        travelBtn =  (ImageButton) findViewById(R.id.btn_travel);
        petBtn =  (ImageButton) findViewById(R.id.btn_pet);


        dinningImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Clicked dining");
                // set the dialog style
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        AddTransactionActivity.this,
                        R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet_add_transaction, (LinearLayout)findViewById(R.id.bottomTransactionContainer));

//                bottomSheetDialog.getBehavior().setPeekHeight(bottomSheetView.getHeight());
//                Toast.makeText(getApplicationContext(), "Height is" + bottomSheetView.getHeight(), Toast.LENGTH_LONG).show();
                // TODO: modify the bottom sheet height
                bottomSheetDialog.getBehavior().setPeekHeight(1500);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                // TODO: link the buttons to functions
            }
        });


    }

}