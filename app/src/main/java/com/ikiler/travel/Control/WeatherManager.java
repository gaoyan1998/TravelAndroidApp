package com.ikiler.travel.Control;

import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Model.bean.WeatherBean;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

public class WeatherManager {
    public static void loadWeather(){
        Map<String,String> params = new HashMap();
        params.put("location","auto_ip");
        params.put("key","b2e77245d3424482ad0984ccb82f4b99");
        OkHttpUtil.post(APIconfig.WeatherUrl, params, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage){
                    WeatherBean weatherBean = GsonUtil.GsonToBean(data,WeatherBean.class);
                }
            }
        });
    }
}
