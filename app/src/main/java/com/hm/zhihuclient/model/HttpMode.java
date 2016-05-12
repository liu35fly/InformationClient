package com.hm.zhihuclient.model;

import android.util.Log;

import com.hm.zhihuclient.API.GitApi;
import com.hm.zhihuclient.data.Contents;
import com.hm.zhihuclient.frag_hot.model.HotModel;
import com.hm.zhihuclient.frag_hot.model.HotNewsDetialsModel;
import com.hm.zhihuclient.frag_information.model.GitModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   15:09
 * purpose：
 */
public class HttpMode {

    private UrlListener urlListener;

    public HttpMode() {
    }


    public void getInformationFromNet(String DetialUrl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Contents.BaseUrlGank).addConverterFactory(GsonConverterFactory.create()).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<GitModel> gitModelCall = gitApi.getInformationServer(DetialUrl);
        if (gitModelCall == null) {
            return;
        }
        gitModelCall.enqueue(new Callback<GitModel>() {
            @Override
            public void onResponse(Call<GitModel> call, Response<GitModel> response) {

                Log.e("respose","Information or Beauty response is "+response.message());
                if (urlListener == null) {
                    return;
                }
                urlListener.onSucceed(response.body());

            }

            @Override
            public void onFailure(Call<GitModel> call, Throwable t) {
                Log.e("respose","Information or Beauty response is fail");
                Log.e("fail","Information or Beauty resion is "+t.getMessage());
            }
        });

    }

    public void getHotFromNet(String DetialUrl){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Contents.BaseUrlZhiHu).addConverterFactory(GsonConverterFactory.create()).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<HotModel> gitModelCall = gitApi.getHotServer(DetialUrl);
        if (gitModelCall == null) {
            return;
        }
        gitModelCall.enqueue(new Callback<HotModel>() {
            @Override
            public void onResponse(Call<HotModel> call, Response<HotModel> response) {

                Log.e("respose","hot response is "+response.message());
                if (urlListener == null) {
                    return;
                }
                urlListener.onSucceed(response.body());

            }

            @Override
            public void onFailure(Call<HotModel> call, Throwable t) {
                Log.e("respose","hot response is fail");
                Log.e("fail","hot resion is "+t.getMessage());
            }
        });
    }

    public void getHotNewsFromNet(String DetialUrl){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Contents.BaseUrlZhiHu).addConverterFactory(GsonConverterFactory.create()).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<HotNewsDetialsModel> gitModelCall = gitApi.getHotNewsServer(DetialUrl);
        if (gitModelCall == null) {
            return;
        }
        gitModelCall.enqueue(new Callback<HotNewsDetialsModel>() {
            @Override
            public void onResponse(Call<HotNewsDetialsModel> call, Response<HotNewsDetialsModel> response) {

                Log.e("respose","HotNews response is "+response.message());
                if (urlListener == null) {
                    return;
                }
                urlListener.onSucceed(response.body());

            }

            @Override
            public void onFailure(Call<HotNewsDetialsModel> call, Throwable t) {
                Log.e("respose","HotNews response is fail");
                Log.e("fail","HotNews resion is "+t.getMessage());
            }
        });
    }

    public void setUrlListener(UrlListener urlListener) {
        this.urlListener = urlListener;
    }

    public interface UrlListener<T> {
        void onSucceed(T details);
    }
}
