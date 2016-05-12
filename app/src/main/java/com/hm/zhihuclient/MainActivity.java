package com.hm.zhihuclient;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.hm.zhihuclient.frag_beauty.BeautyFragment;
import com.hm.zhihuclient.data.Contents;
import com.hm.zhihuclient.frag_hot.HotFragment;
import com.hm.zhihuclient.frag_information.InformationFragment;
import com.hm.zhihuclient.frag_setup.SetUpFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements InformationFragment.OnFragmentInteractionListener,
        BeautyFragment.OnFragmentInteractionListener, SetUpFragment.OnFragmentInteractionListener,
        HotFragment.OnFragmentInteractionListener {

    private MainButton mainButton;
    private ViewPager viewPager;
    private String[] menu = {"资讯 ", "最热", "美图", "设置"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是不是低存储设备
        checkIsLowDevice();

        setContentView(R.layout.activity_main);
        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainButton = (MainButton) findViewById(R.id.main_button);
        mainButton.setParentBlurView(main);

        MainButton.OnItemClickListener listener = new MainButton.OnItemClickListener() {
            @Override
            public void onItemClick(MainButton.GFloatingMenuItem view) {
                mainButton.closeMenu();
                if (view.getItemInfo().text.equals(menu[0])) {
                    //资讯
                    viewPager.setCurrentItem(0);
                } else if (view.getItemInfo().text.equals(menu[1])) {
                    //最热
                    viewPager.setCurrentItem(1);
                } else if (view.getItemInfo().text.equals(menu[2])) {
                    //美图
                    viewPager.setCurrentItem(2);
                } else if (view.getItemInfo().text.equals(menu[3])) {
                    //设置
                    viewPager.setCurrentItem(3);
                }
            }
        };

        ArrayList<Fragment> list = new ArrayList<>();
        InformationFragment informationFragment = new InformationFragment();
        list.add(informationFragment);
        HotFragment hotFragment = new HotFragment();
        list.add(hotFragment);
        BeautyFragment beautyFragment = new BeautyFragment();
        list.add(beautyFragment);
        SetUpFragment setUpFragment = new SetUpFragment();
        list.add(setUpFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setCurrentItem(0);

        for (int i = 0; i < menu.length; i++) {
            mainButton.AddMenuItem(BitmapFactory.decodeResource(getResources(), R.mipmap.default_icon), menu[i], listener);
        }
    }

    private void checkIsLowDevice() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (activityManager.isLowRamDevice()) {
                Contents.Num = 5;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e("456", uri.toString());
    }
}


