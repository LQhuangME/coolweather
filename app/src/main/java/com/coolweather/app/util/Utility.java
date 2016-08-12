package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.Province;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Utility {

    /**
     *
     * @param coolWeatherDB
     * @param result 获取网络省信息结果
     * @return 如果有数据插入则返回true，否则返回false
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,
                                                               String[] result){
        if(result!=null&&result.length>0){
            for(String s:result){
                Province province = new Province();
                province.setProvinceName(s);
                coolWeatherDB.saveProvince(province);
            }
            return true;
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
                                                               String[] result){
        if(result!=null&&result.length>0){
            for(String s:result){
                //截取字符串中的城市名称和城市代号

                City city = new City();
                city.setCityName(s);
                coolWeatherDB.saveCity(city);
            }
            return true;
        }
        return false;
    }

    public synchronized static boolean handleCountryResponse(CoolWeatherDB coolWeatherDB,
                                                               String[] result){
       //目前用的接口没有县级数据信息

        return false;
    }

}
