package edu.northeastern.numad22fa_team15.activities.peakActivities.onBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseSignUpActivity;

public class onBoardingAnimation extends AppCompatActivity {

    private LottieAnimationView piggyAnimationView;
    private TextView piggyWelcomeHeader;
    private TextView piggyWelcomeSlogan;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boading_animation);

        piggyAnimationView = findViewById(R.id.lottie_animation_piggy);
        piggyWelcomeHeader = findViewById(R.id.welcome_splash_header);
        piggyWelcomeSlogan = findViewById(R.id.welcome_splash_slogan);

        piggyAnimationView.animate().translationY(0).setDuration(1000).setStartDelay(4000);
        piggyWelcomeHeader.animate().translationY(0).setDuration(1000).setStartDelay(4000);
        piggyWelcomeSlogan.animate().translationY(0).setDuration(1000).setStartDelay(4000);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(onBoardingAnimation.this, onBoarding.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}