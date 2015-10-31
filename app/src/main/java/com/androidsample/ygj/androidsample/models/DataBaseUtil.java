package com.androidsample.ygj.androidsample.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Objects;

/**
 * Created by YGJ on 2015/10/19 0019.
 */
public class DataBaseUtil extends SQLiteOpenHelper {
    public  static final  String DBName = "";
    public  static final int Version = 1;

    public DataBaseUtil(Context context) {
        super(context, DBName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "careate table location(id int prmary key,lw int not null,le int not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
