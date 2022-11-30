package edu.northeastern.numad22fa_team15.activities.peakActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.northeastern.numad22fa_team15.R;

public class PeakCreateBudget extends AppCompatActivity {

    SeekBar sb_dining, sb_groceries, sb_shopping, sb_living;
    SeekBar sb_entertainment, sb_education, sb_beauty, sb_transportation;
    SeekBar sb_health, sb_travel, sb_pet, sb_other;

    TextView tv_dining, tv_groceries, tv_shopping, tv_living;
    TextView tv_entertainment, tv_education, tv_beauty, tv_transportation;
    TextView tv_health, tv_travel, tv_pet, tv_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget);

        // Dining SeekBar
        sb_dining = (SeekBar) findViewById(R.id.seekbar_dining);
        tv_dining = (TextView) findViewById(R.id.tv_seekbar_dining);

        sb_dining.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_dining, int progress,
                                          boolean b) {
                tv_dining.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_dining) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_dining) {
                // TODO Auto-generated method stub
            }
        });

        // Groceries SeekBar
        sb_groceries = (SeekBar) findViewById(R.id.seekbar_groceries);
        tv_groceries = (TextView) findViewById(R.id.tv_seekbar_groceries);

        sb_groceries.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_groceries, int progress,
                                          boolean b) {
                tv_groceries.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_groceries) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_groceries) {
                // TODO Auto-generated method stub
            }
        });

        // Shopping SeekBar
        sb_shopping = (SeekBar) findViewById(R.id.seekbar_shopping);
        tv_shopping = (TextView) findViewById(R.id.tv_seekbar_shopping);

        sb_shopping.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_shopping, int progress,
                                          boolean b) {
                tv_shopping.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_shopping) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_shopping) {
                // TODO Auto-generated method stub
            }
        });

        // Living SeekBar
        sb_living = (SeekBar) findViewById(R.id.seekbar_living);
        tv_living = (TextView) findViewById(R.id.tv_seekbar_living);

        sb_living.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_living, int progress,
                                          boolean b) {
                tv_living.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_living) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_living) {
                // TODO Auto-generated method stub
            }
        });

        // Entertainment SeekBar
        sb_entertainment = (SeekBar) findViewById(R.id.seekbar_entertainment);
        tv_entertainment = (TextView) findViewById(R.id.tv_seekbar_entertainment);

        sb_entertainment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_entertainment, int progress,
                                          boolean b) {
                tv_entertainment.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_entertainment) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_entertainment) {
                // TODO Auto-generated method stub
            }
        });

        // Education SeekBar
        sb_education = (SeekBar) findViewById(R.id.seekbar_education);
        tv_education = (TextView) findViewById(R.id.tv_seekbar_education);

        sb_education.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_education, int progress,
                                          boolean b) {
                tv_education.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_education) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_education) {
                // TODO Auto-generated method stub
            }
        });

        // Beauty SeekBar
        sb_beauty = (SeekBar) findViewById(R.id.seekbar_beauty);
        tv_beauty = (TextView) findViewById(R.id.tv_seekbar_beauty);

        sb_beauty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_beauty.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Transportation SeekBar
        sb_transportation = (SeekBar) findViewById(R.id.seekbar_transportation);
        tv_transportation = (TextView) findViewById(R.id.tv_seekbar_transportation);

        sb_transportation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_transportation.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Health SeekBar
        sb_health = (SeekBar) findViewById(R.id.seekbar_health);
        tv_health = (TextView) findViewById(R.id.tv_seekbar_health);

        sb_health.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_health.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Travel SeekBar
        sb_travel = (SeekBar) findViewById(R.id.seekbar_travel);
        tv_travel = (TextView) findViewById(R.id.tv_seekbar_travel);

        sb_travel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_travel.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Pet SeekBar
        sb_pet = (SeekBar) findViewById(R.id.seekbar_pet);
        tv_pet = (TextView) findViewById(R.id.tv_seekbar_pet);

        sb_pet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_pet.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Other SeekBar
        sb_other = (SeekBar) findViewById(R.id.seekbar_other);
        tv_other = (TextView) findViewById(R.id.tv_seekbar_other);

        sb_other.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean b) {
                tv_other.setText("$" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }
}