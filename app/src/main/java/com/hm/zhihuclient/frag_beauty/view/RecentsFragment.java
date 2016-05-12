package com.hm.zhihuclient.frag_beauty.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hm.zhihuclient.R;
import com.hm.zhihuclient.data.Contents;
import com.hm.zhihuclient.frag_beauty.BeautyShowActivity;
import com.hm.zhihuclient.frag_beauty.dapter.RecentsAdapter;
import com.hm.zhihuclient.frag_information.model.DetailsModel;
import com.hm.zhihuclient.frag_information.model.GitModel;
import com.hm.zhihuclient.model.HttpMode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2015-07-08.
 */
public class RecentsFragment extends Fragment {

    public static RecentsFragment f;
    private HttpMode httpMode;
    private RecentsAdapter recentsAdapter;
    private static RecentsList recents;
    private List<DetailsModel> detailsModels = new ArrayList<>();

    public static RecentsFragment newInstance() {
        f = new RecentsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.materialrecents_activity_recents, container, false);


        recents = (RecentsList) v.findViewById(R.id.recents);
        getHttp(1);

        recents.setOnItemClickListener(new RecentsList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (detailsModels.size() == 0) {
                    return;
                }
                Intent intent = new Intent(getActivity(), BeautyShowActivity.class);
                intent.putExtra("url", detailsModels.get(i).getUrl());
                startActivity(intent);
//                Toast.makeText(view.getContext(), "Card " + i + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void getHttp(final int pageNum) {
        httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(Object details) {
                setData(((GitModel) details).getResults());
            }
        });
        httpMode.getInformationFromNet(Contents.getMainBeautyListUrl(pageNum));
    }

    private void setData(final List<DetailsModel> list) {
        if (detailsModels.size() != 0) {
            detailsModels.clear();
        }
        detailsModels = list;
        recentsAdapter = new RecentsAdapter() {
            @Override
            public String getTitle(int position) {
                return list.get(position).getDesc();
            }

            @Override
            public View getView(int position) {
                ImageView iv = new ImageView(getActivity());
                iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
                Picasso.with(getActivity()).load(list.get(position).getUrl()).placeholder(R.mipmap.beauty_default_icon).error(R.mipmap.beauty_default_icon).into(iv);
                iv.setBackgroundColor(0xffffffff);
                return iv;
            }

            @Override
            public Drawable getIcon(int position) {
                return getResources().getDrawable(R.mipmap.beauty_default_icon);
            }

            @Override
            public int getHeaderColor(int position) {
                return 0xffffffff;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        if (recents == null) {
            Log.e("null", "recents is null");
            return;
        }
//        if (pageNum != 1) {
//            recents.onLoadMore();
//            recents.setAdapter(recentsAdapter);
//            recents.invalidate();
//            return;
//        }
        recents.setAdapter(recentsAdapter);


    }


}

