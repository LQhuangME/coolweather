package com.coolweather.app.util;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
public class WebServiceUtil {
    public static String TAG = "WebServiceUtil";
    private static WebServiceUtil webServiceUtil;
    private String nameSpace;
    private String methodName;
    private String endPoint;
    private String soapAction;

    public WebServiceUtil(String nameSpace,String methodName,String endPoint){
        this.nameSpace = nameSpace;
        this.methodName = methodName;
        this.endPoint = endPoint;
        this.soapAction = nameSpace + methodName;
    }

    /**
     *
     * @param nameSpace 命名空间
     * @param methodName 调用的方法名
     * @param endPoint   endPoint   soapAction=nameSpace+methodName
     * @return
     */
    public synchronized static WebServiceUtil getInstance(String nameSpace,String methodName,String endPoint){
        webServiceUtil = new WebServiceUtil( nameSpace, methodName, endPoint);
        return webServiceUtil;
    }

    /**
     *
     * @param parameter 需要传入soapObject的Property
     * @param resultName  获取返回结果传入的参数
     *
     * 可以同时获取省、市、县数据以及天气数据
     */
    public void getRemoteInfo(Map<String,Object> parameter,String resultName){

       // String nameSpace = "http://WebXml.com.cn/"; //命名空间信息
       // String methodName = "getSupportProvince";//调用的方法名称
       // String endPoint = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";//EndPoint
       // String soapAction = "http://WebXml.com.cn/getSupportProvince";

        SoapObject soapObject = new SoapObject(nameSpace,methodName);
        if (parameter!=null && parameter.size() >= 0){
            for (String key :parameter.keySet()){
                soapObject.addProperty(key,parameter.get(key));
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            transport.call(soapAction,envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        SoapObject object = (SoapObject) envelope.bodyIn;
        Log.i(TAG, "返回的结果："+object.getProperty(resultName).toString());
        String[] result_ = (String[]) object.getProperty(resultName);
        //return ;
    }
}
