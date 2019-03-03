package com.ikiler.travel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hehongjun on 2018/1/8.
 */

public class BijiSQLUtil {
    private SQLiteOpenHelper helper;
    private Context context;
    public SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");


    public BijiSQLUtil(Context context){
        this.context = context;
    }

    public void add(String title,String content){
        helper = new BijiMyDataBaseHelper(context, "biji.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Date date = new Date();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("date",dates.format(date));
        contentValues.put("time",time.format(date));
        sqLiteDatabase.insert("t_biji",null,contentValues);

    }

    public List<Map<String,String >> select(){
        helper = new BijiMyDataBaseHelper(context, "biji.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("t_biji",null,null,null,null,null,null);
        List<Map<String,String >> list = new ArrayList<>();
        if(cursor.moveToNext()){
            do {
                String id = cursor.getInt(cursor.getColumnIndex("id"))+"";
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                Map<String,String> map = new HashMap<>();
                map.put("id",id);
                map.put("title",title);
                map.put("content",content);
                map.put("date",date);
                map.put("time",time);
                list.add(map);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void update(String id,String title,String content){
        helper = new BijiMyDataBaseHelper(context, "biji.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Date date = new Date();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("date",dates.format(date));
        contentValues.put("time",time.format(date));
        sqLiteDatabase.update("t_biji",contentValues,"id="+id,null);
    }

    public void delete(){
        helper = new DianHuaMyDatabaseHelper(context, "biji.db", null, 1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.delete("t_biji",null,null);
    }
}
