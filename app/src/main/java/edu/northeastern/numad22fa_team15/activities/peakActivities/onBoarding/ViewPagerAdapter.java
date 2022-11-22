package edu.northeastern.numad22fa_team15.activities.peakActivities.onBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import edu.northeastern.numad22fa_team15.R;

/**
 * This is the viewPagerAdapter designed for the ViewPager layout for the OnBoarding activity.
 */
public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    // list of images to be changed
    int[] images = {
            R.drawable.ic_onboarding_1,
            R.drawable.ic_onboarding_2,
            R.drawable.ic_onboarding_3,
            R.drawable.ic_onboarding_4
    };

    // list of headings to be changed
    int[] headings = {
            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
            R.string.heading_four
    };
    // list of description to be changed
    int[] description = {
            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three,
            R.string.desc_four,

    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slider_layout, container, false);

        ImageView slideTitleImage = (ImageView) view.findViewById(R.id.onboarding_image);
        TextView slideHeading =(TextView) view.findViewById(R.id.tv_onboarding_header);
        TextView slideDescription = (TextView) view.findViewById(R.id.tv_onboarding_desc);

        slideTitleImage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(description[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
