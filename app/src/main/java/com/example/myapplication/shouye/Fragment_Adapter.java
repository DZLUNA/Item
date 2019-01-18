package com.example.myapplication.shouye;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 叟 on 2018/12/20.
 */

public class Fragment_Adapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;
    private List<Demo.ChannelsBean> channels;

    public Fragment_Adapter(FragmentManager fm, ArrayList<Fragment> list, List<Demo.ChannelsBean> channels) {
        super(fm);
        this.list = list;
        this.channels = channels;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channels.get(position).getName();
    }
}