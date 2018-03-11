package com.example.rapcbo.sy.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rapcbo.sy.MainActivity.Tab1;
import com.example.rapcbo.sy.MainActivity.Tab2;

/**
 * Created by RaPCBO on 13/12/17.
 */

public class PaqerAdapter extends FragmentStatePagerAdapter {

   int mNoOfTabs;


    public PaqerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return  tab2;
            case 2:

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
