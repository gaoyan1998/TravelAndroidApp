package com.ikiler.travel.http;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.Map;

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
    static MMKV mmkv = MMKV.defaultMMKV();

    static {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static void postWithJson(String url, String json, final DataCallBack callBack){
        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .addHeader("name",mmkv.decodeString("name",""))
                .addHeader("pwd",mmkv.decodeString("pwd",""))
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

    public static void post(String url, Map<String, String> paramters,
                            final DataCallBack callback) {
        try {
            OkHttpUtils
                    .post()
                    .url(url)
                    .addHeader("name",mmkv.decodeString("name",""))
                    .addHeader("pwd",mmkv.decodeString("pwd",""))
                    .params(paramters)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("888", "失败！" + e.toString());
                            callback.calback("",false);
                        }

                        @Override
                        public void onResponse(String responseString, int id) {
                            if (callback != null) {
                                callback.calback(responseString,true);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    interface DataCallBack{
        void calback(String data,boolean flage);
    }
}
