package com.hm.zhihuclient.frag_hot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/28
 * time：   14:16
 * purpose：
 */
public class HotModel {
    private String date;
    List<HotDetialsModel> stories=new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HotDetialsModel> getList() {
        return stories;
    }

    public void setList(List<HotDetialsModel> list) {
        this.stories = list;
    }
}
