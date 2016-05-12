package com.hm.zhihuclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   12:29
 * purpose：
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> lists) {
        super(fm);
        list = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
