package com.ikiler.travel;

import com.ikiler.travel.Model.FoodViewModel;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.ui.Food.FoodListActivity;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.HttpConfig;
import com.ikiler.travel.util.OkHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.ViewModelProviders;

public class APIconfig {

    public static final String WeatherUrl = "https://free-api.heweather.net/s6/weather";


    //        public static final String BaseUrl = "http://192.168.1.105:8080";
    public static final String BaseUrl = "http://106.13.63.57:8080";
    public static final String Login = BaseUrl + "/travel/Login";
    public static final String Register = BaseUrl + "/travel/register";
    public static final String Food = BaseUrl + "/travel/FoodManager";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";
//    public static final String Login = BaseUrl +"";


    public static void refershFoods() {
        final FoodViewModel model = FoodViewModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "select");
        OkHttpUtil.post(APIconfig.Food, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    List<com.ikiler.travel.Model.bean.Food> foodList = GsonUtil.jsonToList(code.getData().replaceAll("\\\\", ""), Food.class);
                    model.getMutableLiveDatas().setValue(foodList);
                }
            }
        });
    }
    public static void deleteFood(int id){
        final FoodViewModel model = FoodViewModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "delete");
        map.put("id",id+"");
        OkHttpUtil.post(APIconfig.Food, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS){
                        refershFoods();
                    }
                }
            }
        });
    }

}

