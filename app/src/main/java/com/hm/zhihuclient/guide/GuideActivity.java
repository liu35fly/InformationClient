package com.hm.zhihuclient.guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.hm.zhihuclient.MainActivity;
import com.hm.zhihuclient.R;
import com.hm.zhihuclient.utils.SharePerfenerceManager;

import java.util.List;
import java.util.Vector;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/5/4
 * time：   10:23
 * purpose：
 */
public class GuideActivity extends FragmentActivity implements GuideFragment.OnFragmentInteractionListener {

    private static String shareKey = "isFirst";
    private List<Fragment> fragments = new Vector<>();
    private PagerAdapter mPagerAdapter;
    private ViewPager viewPager;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if (SharePerfenerceManager.newInstance(this).isFirst(shareKey)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setData(fragments);
        viewPager.setAdapter(mPagerAdapter);

        addSlide(GuideFragment.newInstance(R.layout.guide_view1));
        addSlide(GuideFragment.newInstance(R.layout.guide_view2));
        addSlide(GuideFragment.newInstance(R.layout.guide_view3));


    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addSlide(@NonNull Fragment fragment) {
        fragments.add(fragment);
        mPagerAdapter.notifyDataSetChanged();
        if (viewPager.getCurrentItem() == 2) {
            button.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
