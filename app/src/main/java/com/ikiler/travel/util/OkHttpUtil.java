package com.ikiler.travel.util;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    static OkHttpClient client;
    static MMKV mmkv = MMKV.defaultMMKV();
    static Headers headers;
    static CookieJar cookieJar;

    static {
        cookieJar = new CookieJar() {
            //这里一定一定一定是HashMap<String, List<Cookie>>,是String,不是url.
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
    }
    public static void postJsonBody(String url,String jsonContent,final DataCallBack callback){
        try {
            client = new OkHttpClient.Builder().cookieJar(cookieJar).build();
            OkHttpUtils.initClient(client)
                    .postString()
                    .addHeader("name",mmkv.decodeString("name",""))
                    .addHeader("pwd",mmkv.decodeString("pwd",""))
                    .url(url)
                    .mediaType(MediaType.parse("application/json; charset=utf-8")) //设置post的字符串为json字符串并设置编码
                    .content(jsonContent)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("NetInfo", "失败！" + e.toString());
                            callback.calback("",false);
                        }

                        @Override
                        public void onResponse(String responseString, int id) {
                            Log.i("NetInfo",responseString);
                            if (callback != null) {
                                callback.calback(responseString,true);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            Log.e("NetInfo", "失败！" + e.toString());
                            callback.calback("",false);
                        }

                        @Override
                        public void onResponse(String responseString, int id) {
                            Log.i("NetInfo",responseString);
                            if (callback != null) {
                                callback.calback(responseString,true);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface DataCallBack{
        void calback(String data,boolean flage);
    }
}
