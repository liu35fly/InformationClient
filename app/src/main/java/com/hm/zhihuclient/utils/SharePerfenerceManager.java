package com.hm.zhihuclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/5/4
 * time：   15:58
 * purpose：
 */
public class SharePerfenerceManager {

    private static String firstKey = "isFirst";
    private static String shortCut = "isShortCut";

    private static volatile SharePerfenerceManager sharePerfenerceManager;
    private SharedPreferences sharedPreferences;

    private SharePerfenerceManager(Context context) {
        sharedPreferences = context.getSharedPreferences("zhihu", Activity.MODE_PRIVATE);
    }

    public static SharePerfenerceManager newInstance(Context context) {
        if (sharePerfenerceManager == null) {
            synchronized (SharePerfenerceManager.class) {
                if (sharePerfenerceManager == null) {
                    sharePerfenerceManager = new SharePerfenerceManager(context);
                }
            }
        }
        return sharePerfenerceManager;
    }

    private void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    private boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void isFirst(boolean value) {
        setBoolean(firstKey, value);
    }

    public boolean isFirst() {
        return getBoolean(firstKey);
    }

    public void isShortCut(boolean value) {
        setBoolean(shortCut, value);
    }

    public boolean isShortCut() {
        return getBoolean(shortCut);
    }

}
