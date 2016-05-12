package com.hm.zhihuclient.frag_information;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.hm.zhihuclient.R;

public class InformationShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_information_show);
        WebView web = (WebView) findViewById(R.id.infomationshow_web);
        String s = getIntent().getStringExtra("web");
        web.loadUrl(s);
    }
}
