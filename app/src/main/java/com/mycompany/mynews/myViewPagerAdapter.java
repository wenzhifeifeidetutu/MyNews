package com.mycompany.mynews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/4/13.
 */

public class myViewPagerAdapter  extends FragmentPagerAdapter {

    private Context myContext;

    public myViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SocialNewsFragment();
        }else if (position == 1) {
            return new ScienceNewsFragment();
        }else if (position == 2) {
            return new GymFragment();
        }else  {
            return new AmusementFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return myContext.getString(R.string.view_one);
        }else if (position == 1) {
            return myContext.getString(R.string.view_two);
        }else if (position == 2) {
            return myContext.getString(R.string.view_three);
        }else {
            return myContext.getString(R.string.view_four);
        }
    }
}
