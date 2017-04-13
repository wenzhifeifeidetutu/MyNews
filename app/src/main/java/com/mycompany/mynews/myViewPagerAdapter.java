package com.mycompany.mynews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/4/13.
 */

public class myViewPagerAdapter  extends FragmentPagerAdapter{

    private Context myContext;

    public myViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
