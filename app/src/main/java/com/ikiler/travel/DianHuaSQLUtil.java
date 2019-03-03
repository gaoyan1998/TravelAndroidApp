package com.ikiler.travel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hehongjun on 2018/1/7.
 */

public class DianHuaSQLUtil {
    private final String[] phonename = {"武汉市旅游投诉电话", "湖北省旅游投诉电话", "国家旅游投诉电话",
            "武汉市假日办_值班电话", "武汉市假日办_投诉电话", "武昌火车站", "汉口火车站", "新华路汽车客运站",
            "金家墩汽车客运站", "青年路汽车客运站", "宏基汽车客运站", "汉阳长途汽车客运站", "汉阳客运中心", "天河机场",
            "武汉客运港长途汽车站", "武汉市110联动投诉", "江岸区110联动办", "硚口区110联动办", "汉阳区110联动办",
            "武昌区110联动办", "洪山区110联动办", "青山区110联动办", "东湖开发区110联动办", "新洲区110联动办"};
    private final String[] phonenumber = {"02782855773", "02787124701",
            "01065275315", "02782761686", "02782855773", "02788068888",
            "65650666", "85870482", "85870482", "85731761", "85731761",
            "84842395", "68841281", "83666666", "82857625", "8532110",
            "82410810", "83798277", "84843446", "88851360", "87393996",
            "688865375", "87491700", "86912345"};

    private SQLiteOpenHelper helper;
    private Context context;

    public DianHuaSQLUtil(Context context){
        this.context = context;
    }

    public void add(){
        helper = new DianHuaMyDatabaseHelper(context, "number.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < phonename.length; i++) {
            contentValues.put("name",phonename[i]);
            contentValues.put("number",phonenumber[i]);
            sqLiteDatabase.insert("t_number",null,contentValues);
        }
    }

    public List<Map<String,String >> select(String str){
        helper = new DianHuaMyDatabaseHelper(context, "number.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("t_number",null,"name LIKE '%"+str+"%' or number LIKE '%"+str+"%'",null,null,null,null);
        List<Map<String,String >> list = new ArrayList<>();
        if(cursor.moveToNext()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                Map<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("number",number);
                list.add(map);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
