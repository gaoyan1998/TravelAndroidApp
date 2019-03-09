package com.ikiler.travel;

import android.util.Log;

import com.ikiler.travel.Base.BaseLiveData;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.FeedLiveDataModel;
import com.ikiler.travel.Model.FoodLiveDataModel;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.Model.bean.Addr;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.HttpConfig;
import com.ikiler.travel.util.OkHttpUtil;
import com.ikiler.travel.util.RssHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class APIconfig {

    public static final String WeatherUrl = "https://free-api.heweather.net/s6/weather";
    public static final String FeedUrl = "http://www.travel5156.com/index.php?m=content&c=rss&rssid=9";

    //        public static final String BaseUrl = "http://192.168.1.105:8080";
    public static final String BaseUrl = "http://106.13.63.57:8080";
    public static final String Login = BaseUrl + "/travel/Login";
    public static final String Register = BaseUrl + "/travel/register";
    public static final String Food = BaseUrl + "/travel/FoodManager";
    public static final String Spot = BaseUrl + "/travel/SpotManager";
    public static final String Ticket = BaseUrl +"/travel/AddrManager";
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


    public static void getCity(BaseLiveData<Addr> liveData){
        Log.e("ml", "GET_CITY");
        OkHttpUtil.postJsonBody(APIconfig.Ticket, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage){
                    Code code = GsonUtil.GsonToBean(data,Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS){
                        List<Addr> list = GsonUtil.jsonToList(code.getData(),Addr.class);
                        liveData.getMutableLiveDatas().setValue(list);
                    }
                }
            }
        });
    }
    public static void getTicket(){

    }
    public static void refershFoods() {
        Log.e("ml", "REF_FOOD");
        final FoodLiveDataModel model = FoodLiveDataModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "select");
        OkHttpUtil.post(APIconfig.Food, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    List<com.ikiler.travel.Model.bean.Food> foodList = GsonUtil.jsonToList(code.getData(), Food.class);
                    model.getMutableLiveDatas().setValue(foodList);
                }
            }
        });
    }

    public static void deleteFood(int id, final CallBack callBack) {
        Log.e("ml", "DEL_FOOD");
        final FoodLiveDataModel model = FoodLiveDataModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "delete");
        map.put("id", id + "");
        OkHttpUtil.post(APIconfig.Food, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        callBack.calBack(true, code.getCode());
                    } else {
                        callBack.calBack(false, code.getCode());
                    }
                }
                callBack.calBack(false, HttpConfig.NET_ERR);
            }
        });
    }

    public static void editFood(String action, Food food, final CallBack callBack) {
        Log.e("ml", "EDIT_FOOD");
        Map<String, String> map = new HashMap<>();
        map.put("json", GsonUtil.GsonString(food));
        OkHttpUtil.postWithFile(APIconfig.Food + "?action=" + action, food.getImagePath(), map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        callBack.calBack(true, code.getCode());
                    } else callBack.calBack(false, code.getCode());
                } else callBack.calBack(false, HttpConfig.NET_ERR);
            }
        });
    }


    public static void refershSpots() {
        Log.e("ml", "REF_SPOT");
        final FoodLiveDataModel model = FoodLiveDataModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "select");
        OkHttpUtil.post(APIconfig.Spot, map, new OkHttpUtil.DataCallBack() {
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

    public static void deleteSpot(int id, final CallBack callBack) {
        Log.e("ml", "DEL_SPOT");
        final FoodLiveDataModel model = FoodLiveDataModel.instance();
        Map<String, String> map = new HashMap<>();
        map.put("action", "delete");
        map.put("id", id + "");
        OkHttpUtil.post(APIconfig.Spot, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        callBack.calBack(true, code.getCode());
                    } else {
                        callBack.calBack(false, code.getCode());
                    }
                }
                callBack.calBack(false, HttpConfig.NET_ERR);
            }
        });
    }

    public static void editSpot(String action, Food food, final CallBack callBack) {
        Log.e("ml", "EDIT_SPOT");
        Map<String, String> map = new HashMap<>();
        map.put("json", GsonUtil.GsonString(food));
        OkHttpUtil.postWithFile(APIconfig.Spot + "?action=" + action, food.getImagePath(), map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        callBack.calBack(true, code.getCode());
                    } else callBack.calBack(false, code.getCode());
                } else callBack.calBack(false, HttpConfig.NET_ERR);
            }
        });
    }


    public static void getFeed() {
        OkHttpUtil.postJsonBody(FeedUrl, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage){
                    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); //构建SAX解析工厂
                    SAXParser saxParser = null; //解析工厂生产解析器
                    XMLReader xmlReader;
                    RssHandler rssHandler = null;
                    try {
                        saxParser = saxParserFactory.newSAXParser();
                        xmlReader = saxParser.getXMLReader(); //通过saxParser构建xmlReader阅读器
                        rssHandler = new RssHandler();
                        xmlReader.setContentHandler(rssHandler);
                        xmlReader.parse(new InputSource(new StringReader(data)));
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<RssItem> list = rssHandler.getRssFeed().getRssItems();
                    FeedLiveDataModel.instance().getMutableLiveDatas()
                            .setValue(list);
                }
            }
        });
    }
}

