package com.hm.zhihuclient.frag_beauty.dapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hm.zhihuclient.frag_beauty.view.RecentsFragment;

import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/26
 * time：   15:40
 * purpose：
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private int pageNum = 1;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> list) {
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(0);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void onLoadMore() {
//        list.clear();
        pageNum++;
        RecentsFragment.newInstance().getHttp(pageNum);
//        RecentsFragment.newInstance().getHttp(pageNum);
//        RecentsFragment recentsFragment=new RecentsFragment();
//        recentsFragment.getHttp(pageNum);
//        list.add(recentsFragment);
        notifyDataSetChanged();
    }
}
