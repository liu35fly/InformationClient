package com.hm.zhihuclient.frag_hot.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.zhihuclient.R;
import com.hm.zhihuclient.frag_hot.NewShowActivity;
import com.hm.zhihuclient.frag_hot.model.HotDetialsModel;
import com.hm.zhihuclient.frag_hot.model.HotNewsDetialsModel;
import com.hm.zhihuclient.frag_hot.view.HotLayout;
import com.hm.zhihuclient.model.HttpMode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/28
 * time：   10:04
 * purpose：
 */
public class HotRecyclerAdapter extends RecyclerView.Adapter<HotRecyclerAdapter.HotRecyclerViewHolder> {

    private Context context;
    private Map<Integer, Boolean> mFoldStates = new HashMap<>();
    private List<HotDetialsModel> list = new ArrayList<>();

    public HotRecyclerAdapter(Context ctx) {
        context = ctx;
    }

    public void setData(List<HotDetialsModel> lists) {
        list = lists;
    }

    @Override
    public HotRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotRecyclerViewHolder(new HotLayout(context));
    }

    @Override
    public void onBindViewHolder(final HotRecyclerViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImages().get(0)).into(holder.itemCoverIcon);
        holder.itemCoverTitle.setText(list.get(position).getTitle());
        holder.itemCoverAuthor.setText("hehe");

        setDetail(holder.itemDetailWeb, holder.itemDetailMore,list.get(position).getId());

        if (mFoldStates.containsKey(position)) {
            if (mFoldStates.get(position) == Boolean.TRUE) {
                if (!holder.hotLayout.isFolded()) {
                    holder.hotLayout.foldWithoutAnimation();
                }
            } else if (mFoldStates.get(position) == Boolean.FALSE) {
                if (holder.hotLayout.isFolded()) {
                    holder.hotLayout.unfoldWithoutAnimation();
                }
            }
        } else {
            holder.hotLayout.foldWithoutAnimation();
        }
        holder.hotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hotLayout.isFolded()) {
                    holder.hotLayout.unfoldWithAnimation();
                } else {
                    holder.hotLayout.foldWithAnimation();
                }
            }
        });

        holder.hotLayout.setFoldListener(new HotLayout.FoldListener() {
            @Override
            public void onUnFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.hotLayout.setElevation(5);
                }
            }

            @Override
            public void onUnFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.hotLayout.setElevation(0);
                }
                mFoldStates.put(holder.getAdapterPosition(), false);
            }

            @Override
            public void onFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.hotLayout.setElevation(5);
                }
            }

            @Override
            public void onFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.hotLayout.setElevation(0);
                }
                mFoldStates.put(holder.getAdapterPosition(), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setDetail(final TextView detail,final Button btn, final int id) {
        HttpMode httpMode = new HttpMode();
        httpMode.setUrlListener(new HttpMode.UrlListener() {
            @Override
            public void onSucceed(final Object details) {
                detail.setText(Html.fromHtml(((HotNewsDetialsModel) details).getBody()));

                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, NewShowActivity.class);
                        intent.putExtra("info",((HotNewsDetialsModel) details).getBody());
                        context.startActivity(intent);
                    }
                });
            }
        });
        httpMode.getHotNewsFromNet(id + "");
    }

    protected static class HotRecyclerViewHolder extends RecyclerView.ViewHolder {

        protected HotLayout hotLayout;
        public ImageView itemCoverIcon;
        public TextView itemCoverTitle;
        public TextView itemCoverAuthor;
        public TextView itemDetailWeb;
        public Button itemDetailMore;

        public HotRecyclerViewHolder(HotLayout hotLayouts) {
            super(hotLayouts);
            hotLayout = hotLayouts;
            hotLayout.setupViews(R.layout.hot_item_cover, R.layout.hot_list_detail, R.dimen.card_cover_height, itemView.getContext());

            itemCoverIcon = (ImageView) hotLayout.findViewById(R.id.hot_list_cover_icon);
            itemCoverTitle = (TextView) hotLayout.findViewById(R.id.hot_list_cover_title);
            itemCoverAuthor = (TextView) hotLayout.findViewById(R.id.hot_list_cover_author);
            itemDetailWeb = (TextView) hotLayout.findViewById(R.id.hot_list_detail_web);
            itemDetailMore =(Button)hotLayout.findViewById(R.id.hot_list_detail_btn);
        }
    }
}
