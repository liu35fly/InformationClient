package com.hm.zhihuclient.API;

import com.hm.zhihuclient.frag_hot.model.HotModel;
import com.hm.zhihuclient.frag_hot.model.HotNewsDetialsModel;
import com.hm.zhihuclient.frag_information.model.GitModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   15:16
 * purpose：
 */
public interface GitApi<T> {
    @GET("{user}")
    Call<GitModel> getInformationServer(@Path("user") String user);
    @GET("{user}")
    Call<HotModel> getHotServer(@Path("user") String user);
    @GET("{user}")
    Call<HotNewsDetialsModel> getHotNewsServer(@Path("user") String user);
}
