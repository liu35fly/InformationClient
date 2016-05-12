package com.hm.zhihuclient.frag_hot;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hm.zhihuclient.R;

public class NewShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_new_show);
        String s = getIntent().getStringExtra("info");
        ((TextView) findViewById(R.id.newshow_detial)).setText(Html.fromHtml(s));
    }
}
