package com.ikiler.travel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by hehongjun on 2018/1/7.
 */

public class DianHuaMyDatabaseHelper extends SQLiteOpenHelper {


    public static final String CREATE_NUMBER = "create table t_number(" +
            "id Integer primary key autoincrement," +
            "number text," +
            "name text" +
            ")";

    Context context;

    public DianHuaMyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NUMBER);
        Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
