package com.ikiler.travel.util;

import android.text.TextUtils;
import android.util.Log;

import com.ikiler.travel.Base.BaseLiveData;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.FeedLiveDataModel;
import com.ikiler.travel.Model.FoodLiveDataModel;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.Model.bean.Addr;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.Model.bean.HotCity;
import com.ikiler.travel.Model.bean.Note;
import com.ikiler.travel.Model.bean.PersonTicket;
import com.ikiler.travel.Model.bean.Phone;
import com.ikiler.travel.Model.bean.Ticket;
import com.ikiler.travel.Model.bean.WeatherBean;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import androidx.lifecycle.MutableLiveData;

public class APIconfig {

    //    public static final String BaseUrl = "http://192.168.1.105:8080";
    public static final String BaseUrl = "http://106.13.63.57:8080";




    public static final String WeatherUrl = "https://free-api.heweather.net/s6/weather";
    public static final String FeedUrl = "http://www.travel5156.com/index.php?m=content&c=rss&rssid=9";
    public static final String HotCityUrl = "https://search.heweather.net/top";
    public static final String CitySearch = "https://search.heweather.net/find";

    public static final String Login = BaseUrl + "/travel/Login";
    public static final String Register = BaseUrl + "/travel/register";
    public static final String Food = BaseUrl + "/travel/FoodManager";
    public static final String Spot = BaseUrl + "/travel/SpotManager";
    public static final String AddrManager = BaseUrl + "/travel/AddrManager";
    public static final String TicketManager = BaseUrl + "/travel/TicketManager";
    public static final String NoteMnager = BaseUrl + "/travel/getNote";
    public static final String NoteDel = BaseUrl + "/travel/deleteNote";
    public static final String NoteAdd = BaseUrl + "/travel/addNote";
    public static final String PhoneManager = BaseUrl + "/travel/PhoneManager";


    private static <T> T execut(String url, Object bean, MutableLiveData<T> mutableLiveData, Class<T> cls) {
        Log.e("ml", "EXECUT_NET_START");
        OkHttpUtil.postJsonBody(url, GsonUtil.GsonString(bean), new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage && !TextUtils.isEmpty(data)) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("NetState").setValue(code.getCode());
                        T t = null;
                        if (!TextUtils.isEmpty(code.getData()) && cls != null) {
                            t = GsonUtil.GsonToBean(code.getData(), cls);
                        }
                        mutableLiveData.postValue(t);
                    } else
                        LiveBus.getDefault().subscribe("NetState").setValue(HttpConfig.NET_ERR);
                } else
                    LiveBus.getDefault().subscribe("NetState").setValue(HttpConfig.NET_ERR);
            }
        });
        return null;
    }

    public static void refershWeather(String location) {
        OkHttpUtil.postJsonBody(WeatherUrl + "?key=b2e77245d3424482ad0984ccb82f4b99&location="+location, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage && !TextUtils.isEmpty(data)) {
                    WeatherBean weatherBean = GsonUtil.GsonToBean(data, WeatherBean.class);
                    LiveBus.getDefault().subscribe("Weather").postValue(weatherBean);
                }
            }
        });
    }

    public static void refershHotCity(String city) {
        String url ;
        if (city.equals("")){
            url = HotCityUrl + "?group=cn&number=30&key=b2e77245d3424482ad0984ccb82f4b99";
        }else {
            url = CitySearch + "?mode=match&group=cn&number=30&key=b2e77245d3424482ad0984ccb82f4b99&location="+city;
        }
        OkHttpUtil.postJsonBody(url, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage && !TextUtils.isEmpty(data)) {
                    HotCity hotCity = GsonUtil.GsonToBean(data, HotCity.class);
                    if (hotCity.getHeWeather6().get(0).getStatus().equals("ok"))
                        LiveBus.getDefault().subscribe("City").postValue(hotCity);
                }
            }
        });
    }

    public static void addPhone(Phone phone, MutableLiveData mutableLiveData) {
        execut(PhoneManager + "?action=add", phone, mutableLiveData, null);
    }

    public static void delPhone(Phone phone) {
        Log.e("ml", "DEL_PHONE");
        execut(PhoneManager + "?id=" + phone.getId() + "&action=delete", null, LiveBus.getDefault().subscribe("PhoneNet"), null);
        Map<String, String> map = new HashMap<>();
        map.put("id", phone.getId() + "");
        map.put("action", "delete");
        OkHttpUtil.post(PhoneManager, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("Net").setValue(true);
                        return;
                    }
                }
                LiveBus.getDefault().subscribe("Net").setValue(false);
            }
        });
    }

    public static void refeshPhone() {
        Log.e("ml", "GET_PHONE");
        Map<String, String> map = new HashMap<>();
        map.put("action", "select");
        OkHttpUtil.post(PhoneManager, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        List<Phone> list = GsonUtil.jsonToList(code.getData(), Phone.class);
                        LiveBus.getDefault().subscribe("Phone").postValue(new Phone(list));
                    }
                }
            }
        });
    }

    public static void refeshNote() {
        Log.e("ml", "GET_NOTE");
        OkHttpUtil.postJsonBody(NoteMnager, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        List<Note> list = GsonUtil.jsonToList(code.getData(), Note.class);
                        LiveBus.getDefault().subscribe("Note").postValue(new Note(list));
                    }
                }
            }
        });
    }

    public static void addNote(Note note) {
        Log.e("ml", "ADD_NOTE");
        OkHttpUtil.postJsonBody(NoteAdd, GsonUtil.GsonString(note), new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("Net").setValue(true);
                        return;
                    }
                }
                LiveBus.getDefault().subscribe("Net").setValue(false);
            }
        });
    }

    public static void delNote(Note note) {
        Log.e("ml", "DEL_NOTE");
        Map<String, String> map = new HashMap<>();
        map.put("id", note.getId() + "");
        OkHttpUtil.post(NoteDel, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("Net").setValue(true);
                        return;
                    }
                }
                LiveBus.getDefault().subscribe("Net").setValue(false);
            }
        });
    }

    public static void getCity(BaseLiveData<Addr> liveData) {
        Log.e("ml", "GET_CITY");
        OkHttpUtil.postJsonBody(AddrManager, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        List<Addr> list = GsonUtil.jsonToList(code.getData(), Addr.class);
                        liveData.getMutableLiveDatas().setValue(list);
                    }
                }
            }
        });
    }

    public static void getTickets(Ticket ticket) {
        String json = GsonUtil.GsonString(ticket);
        OkHttpUtil.postJsonBody(TicketManager + "?action=select", json, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        List<Ticket> list = GsonUtil.jsonToList(code.getData(), Ticket.class);
                        Log.e("ml", code.getData());
                        Log.e("ml", "ReceiveNETTTTTTTTT" + list.get(0).getTimeFrom());
                        LiveBus.getDefault().subscribe("Ticket").setValue(new Ticket(list));
                    }
                }
            }
        });

    }

    public static void buyTicket(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("action", "buy");
        map.put("id", id);
        OkHttpUtil.post(TicketManager, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("Net").setValue(true);
                        return;
                    }
                }
                LiveBus.getDefault().subscribe("Net").setValue(false);
            }
        });
    }

    public static void delTicket(PersonTicket personTicket) {
        OkHttpUtil.postJsonBody(TicketManager + "?action=delete", GsonUtil.GsonString(personTicket), new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        LiveBus.getDefault().subscribe("Net").setValue(true);
                        return;
                    }
                }
                LiveBus.getDefault().subscribe("Net").setValue(false);
            }
        });
    }

    public static void getMyTicket() {
        Map<String, String> map = new HashMap<>();
        map.put("action", "selectPerson");
        OkHttpUtil.post(TicketManager, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage) {
                    Code code = GsonUtil.GsonToBean(data, Code.class);
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
                        List<PersonTicket> list = GsonUtil.jsonToList(code.getData(), PersonTicket.class);
                        PersonTicket p = new PersonTicket();
                        p.setList(list);
                        LiveBus.getDefault().subscribe("MyTicket").setValue(p);
                    }
                }
            }
        });

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
                    if (code != null && code.getData().equals("null")) {
                        return;
                    }
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
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
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
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
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
                    if (code.getData().equals("null")) {
                        return;
                    }
                    String data1 = code.getData().replaceAll("\\\\", "");
                    Log.e("ml", data1);
                    List<com.ikiler.travel.Model.bean.Food> foodList = GsonUtil.jsonToList(data1, Food.class);
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
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
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
                    if (code != null && code.getCode() == HttpConfig.REQUEST_SUCCESS) {
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
                if (flage) {
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
                    } catch (Exception e) {
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

