package com.ikiler.travel.http;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    static OkHttpClient client;
    static Handler handler;
    static String name,pwd;

    static {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static void postWithJson(String url, String json, final DataCallBack callBack){
        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .addHeader("name",name)
                .addHeader("pwd",pwd)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.calback("",false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    callBack.calback(response.body().string(),true);
                }
            }
        });
    }



    interface DataCallBack{
        void calback(String data,boolean flage);
    }
}
