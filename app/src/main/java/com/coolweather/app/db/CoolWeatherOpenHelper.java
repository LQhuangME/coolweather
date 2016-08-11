package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huanglq on 2016/8/11.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper{

    /**
     * Province表创建语句
     */
    public static final String CREATE_PROVINCE = "create table Province " +
            "(id Integer primary key autoincrement," +
            "province_name text," +
            "province_code text)";

    /**
     * City表创建语句
     */
    public static final String CREATE_CITY = "create table City " +
            "(id Integer primary key autoincrement," +
            "city_name text," +
            "city_code text," +
            "province_id text)";

    /**
     * Country表创建
     */
    public static final String CREATE_COUNTRY = "create table Country " +
            "(id Integer primary key autoincrement," +
            "country_name text," +
            "country_code text," +
            "city_id text)";

    /**
     * 构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
