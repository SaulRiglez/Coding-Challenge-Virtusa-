package com.yoprogramo.isspasses.util;

import com.yoprogramo.isspasses.model.ISSPass;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by User on 1/4/2018.
 */

public class RetrofitAdapter {

    private static final String TAG = "Retrofit";
    private static final String BASE_URL = "http://api.open-notify.org/";

    public static class Factory {
        public static Retrofit create(){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
        public static Observable<ISSPass> createObservable(String latitude, String longitude){
            Retrofit retrofit = create();
            WebService webService = retrofit.create(WebService.class);
            return webService.getInfo(latitude,longitude);
        }
    }
}
