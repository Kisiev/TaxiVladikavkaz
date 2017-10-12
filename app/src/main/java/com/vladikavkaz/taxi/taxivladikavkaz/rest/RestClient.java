package com.vladikavkaz.taxi.taxivladikavkaz.rest;
import com.vladikavkaz.taxi.taxivladikavkaz.ConstantManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RestClient {

    private YandexApi yandexApi;

    public RestClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantManager.BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        yandexApi = retrofit.create(YandexApi.class);
    }



    public YandexApi getYandexApi() {
        return yandexApi;
    }

}