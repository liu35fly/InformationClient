package com.hm.zhihuclient.data;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   15:22
 * purpose：
 */
public class Contents {

    public static int Num = 10;
    public static String BaseUrlGank = "http://gank.io/api/data/";

    public static String MainBannerUrl = "福利/5/1";

    private static String MainListUrl = "all/" + Num + "/";

    public static String getMainListUrl(int pageNum) {
        return MainListUrl + pageNum;
    }

    public static String getMainBeautyListUrl(int pageNum) {
        return "福利/" + Num + "/" + pageNum;
    }

    public static String BaseUrlZhiHu = "http://news-at.zhihu.com/api/4/news/";

    public static String MainHotList = "latest";
}
