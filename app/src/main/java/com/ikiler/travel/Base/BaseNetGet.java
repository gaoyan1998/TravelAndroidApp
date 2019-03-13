package com.ikiler.travel.Base;

public class BaseNetGet {
    public static final String WeatherUrl = "https://free-api.heweather.net/s6/weather";
    public static final String FeedUrl = "http://www.travel5156.com/index.php?m=content&c=rss&rssid=9";

    //    public static final String BaseUrl = "http://192.168.1.105:8080";
    public static final String BaseUrl = "http://106.13.63.57:8080";



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

}
