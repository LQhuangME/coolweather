package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class CoolWeatherDB {
    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
                DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 存储province实例到数据库
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

/**
 * 从数据库读取全国所有省份信息
 */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将city实例存入数据库
     */
    public void saveCity(City city){
        if (city!=null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    /**
     * 从数据库获取某省下所有城市信息
     */

    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id=?",
                new String[]{String.valueOf(provinceId)},null,null,null);
        do {
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
            list.add(city);
        }while(cursor.moveToNext());
        return list;
    }

    /**
     * 将country实例存入数据库
     */
    public void saveCountries(Country country){
        if (country!=null){
            ContentValues values = new ContentValues();
            values.put("country_name",country.getCountryName());
            values.put("country_code",country.getCountryCode());
            values.put("city_id",country.getCityId());
            db.insert("Country",null,values);
        }
    }

    /**
     * 从数据库获取某城市下所有县信息
     */

    public List<Country> loadCountries(int cityId){
        List<Country> list = new ArrayList<Country>();
        Cursor cursor = db.query("Country",null,"city_id=?",
                new String[]{String.valueOf(cityId)},null,null,null);
        do {
            Country country = new Country();
            country.setId(cursor.getInt(cursor.getColumnIndex("id")));
            country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
            country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
            country.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
            list.add(country);
        }while(cursor.moveToNext());
        return list;
    }

}
