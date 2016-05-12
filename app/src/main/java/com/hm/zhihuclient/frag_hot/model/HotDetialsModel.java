package com.hm.zhihuclient.frag_hot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/28
 * time：   14:17
 * purpose：
 */
public class HotDetialsModel {

    private List<String> images = new ArrayList<>();
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
