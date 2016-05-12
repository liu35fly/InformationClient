package com.hm.zhihuclient.frag_information.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.zhihuclient.R;
import com.hm.zhihuclient.frag_information.InformationShowActivity;
import com.hm.zhihuclient.frag_information.model.DetailsModel;

import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   17:54
 * purpose：
 */
public class ListViewAdapter extends BaseAdapter {

    //数据类别
    public static String Android = "Android";
    public static String Ios = "iOS";
    public static String Other = "拓展资源";

    private List<DetailsModel> data;
    private ViewHolder viewHolder;
    private Context context;

    public ListViewAdapter(Context ctx) {
        viewHolder = null;
        context = ctx;
    }

    public void setData(List<DetailsModel> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.waterdroplistview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setHolder(data.get(position));
        return convertView;
    }

    public void onRefresh(List<DetailsModel> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void onLoadMore(List<DetailsModel> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private View root;
        public TextView name;
        public ImageView icon;
        public TextView content;

        public ViewHolder(View v) {
            root = v;
            name = (TextView) v.findViewById(R.id.item_name);
            icon = (ImageView) v.findViewById(R.id.item_icon);
            content = (TextView) v.findViewById(R.id.item_content);
        }

        public void setHolder(final DetailsModel detailsModel) {
            //设置item标题
            name.setText(detailsModel.getDesc());

            //设置item类别图标
            if (detailsModel.getType().equals(Android)) {
                icon.setBackgroundResource(R.mipmap.android);
            } else if (detailsModel.getType().equals(Ios)) {
                icon.setBackgroundResource(R.mipmap.ios);
            } else {
                icon.setBackgroundResource(R.mipmap.other);
            }
            //设置item显示时间及作者
            String time = detailsModel.getPublishedAt();
            String[] t = time.split("T");
            content.setText(t[0].substring(t[0].indexOf("-") + 1) + "  " + detailsModel.getWho());
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, InformationShowActivity.class);
                    intent.putExtra("web",detailsModel.getUrl());
                    context.startActivity(intent);
                }
            });

        }
    }
}


