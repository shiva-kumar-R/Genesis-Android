package com.genesis.android.network;

import android.content.Context;

import com.genesis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiServiceGenerator {
    private static Retrofit sRetrofit;
    private static ApiService apiService;

    public static <T> T createService(Context context, Class<T> serviceClass) {

        if (sRetrofit == null) {
            try {
                String baseUrl = String.valueOf(R.string.url);
                sRetrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sRetrofit.create(serviceClass);
    }
}
