package com.hm.zhihuclient.frag_beauty;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hm.zhihuclient.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

public class BeautyShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_beauty_show);
        String url = getIntent().getStringExtra("url");
        PhotoView imageView = (PhotoView) findViewById(R.id.beauty_activity_imge);
        Picasso.with(this).load(url).placeholder(R.mipmap.default_icon).error(R.mipmap.default_icon).into(imageView);
    }
}
