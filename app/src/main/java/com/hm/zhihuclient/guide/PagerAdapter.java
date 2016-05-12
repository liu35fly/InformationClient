package com.hm.zhihuclient.guide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/5/4
 * time：   14:12
 * purpose：
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Map<Integer, Fragment> retainedFragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        this.retainedFragments = new HashMap<>();
    }

    public void setData(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (retainedFragments.containsKey(position)) {
            return retainedFragments.get(position);
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public Collection<Fragment> getRetainedFragments() {
        return retainedFragments.values();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        retainedFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (retainedFragments.containsKey(position)) {
            retainedFragments.remove(position);
        }
        super.destroyItem(container, position, object);
    }
}
