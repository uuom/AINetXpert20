package com.asiainfo.netxpert.message.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by uuom on 16-8-8.
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private String[] tabTitles;
    private Fragment[] fragments;

    public ViewPageAdapter(FragmentManager fm,String[] tabTitles, Fragment[] fragments) {
        super(fm);
        this.tabTitles = tabTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
