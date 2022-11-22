package edu.northeastern.numad22fa_team15.activities.peakActivities.onBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.peakActivities.PeakFirstPage;

public class onBoarding extends AppCompatActivity {

    private static final String TAG = "OnBoardingPage___";

    ViewPager slideViewPager;
    LinearLayout dotLinearLayout;
    Button backBtn, nextBtn, skipBtn;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        backBtn = findViewById(R.id.btn_back);
        nextBtn = findViewById(R.id.btn_next);
        skipBtn = findViewById(R.id.btn_skip);

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotLinearLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        // set adapter to the slide view pager
        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);

        // set initial value to 0
        setUpIndicator(0);
        backBtn.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        slideViewPager.addOnPageChangeListener(viewListener);
    }

    /**
     * Change the bottom dots indicator correspondingly with the positon
     * @param position
     */
    public void setUpIndicator(int position) {
        dots = new TextView[4];
        dotLinearLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
          dots[i] = new TextView(this);
          dots[i].setText(Html.fromHtml("&#8226"));
          dots[i].setTextSize(35);
          dots[i].setTextColor(getResources().getColor(R.color.FinalProj_Light, getApplicationContext().getTheme()));
          // add the view to the linear layout
          dotLinearLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.FinalProj_Dark, getApplicationContext().getTheme()));
    }

    // when user scroll the page, call the listener
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // set up the indicator
            setUpIndicator(position);
            // only if user move to the second page, the back button become visible
            if (position > 0) {
                backBtn.setVisibility(View.VISIBLE);
            } else {
                // on the first page, the back button is not visible
                backBtn.setVisibility(View.INVISIBLE);
            }
            // if user move to the last page, the skip btn become invisible
            if (position > 2) {
                skipBtn.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            } else {
                skipBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * Helper method to return the position of the page.
     * @param i (int)
     * @return the position of the page (pos start with 0)
     */
    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }

    public void backToPrevious(View view) {
        // if the user is NOT on the first page
        if (getItem(0) > 0) {
            // show the previous image by deduct i by 1
            slideViewPager.setCurrentItem(getItem(-1), true);
        }
    }

    public void moveToNext(View view) {
        // if the user is not on the final page
        if (getItem(0) < 3) {
            // show the next page by add i by 1
            slideViewPager.setCurrentItem(getItem(1), true);
        } else {
            // if user scroll to the final page, jump to the sign Up page
            // TODO: change  PeakFirstPage.class correspondingly by the signup page
            Intent i = new Intent(onBoarding.this, PeakFirstPage.class);
            startActivity(i);
            finish();
        }
    }

    public void skipToEnd(View view) {
        // TODO: change  PeakFirstPage.class correspondingly by the signup page
        Intent i = new Intent(onBoarding.this, PeakFirstPage.class);
        startActivity(i);
        finish();

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