package com.zeoy.gee_sdk.util;

import android.content.Context;

import com.zeoy.gee_sdk.util.CPResourceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng
 * PackageName ModelTest
 * 2018/1/22 15:44
 *      数据对接管理类
 */

public class MapUtils {

    private static Context mContext;

    /**
     *          在Application里面初始化，就能全局调用
     * @param context
     */
    public static void init(Context context){
        if (mContext == null){
            mContext = context;
        }else {
            return;
        }
    }

    /**
     *      通用Map
     *      (无参的方法通用调取)
     * @return
     */
    public static Map<String,String> getCurrencyMap(){
        Map<String,String> map = new HashMap<>();
        map.put("appid", CPResourceUtils.getString("appid"));
        map.put("sign",null);
        map.put("device",CPResourceUtils.getDevice());
        return map;
    }

    /**
     *      注册
     * @return
     */
    public static Map<String,String> getRegistMap(){
        Map<String,String> map = new HashMap<>();
        map.putAll(getCurrencyMap());
        map.put("brand", SystemUtils.getDeviceBrand());
        map.put("model",SystemUtils.getSystemModel());
        map.put("widthpix",SystemUtils.getWith(mContext)+"");
        map.put("heightpix",SystemUtils.getHeight(mContext)+"");
        map.put("vercode",SystemUtils.getSystemVersion());
        return map;
    }

    /**
     *      版本更新
     * @return
     */
    public static Map<String,String> getNewMap(){
        Map<String,String> map = new HashMap<>();
        map.putAll(getCurrencyMap());
        map.put("code",Utils.getVersion(mContext));
        return map;
    }

    /**
     *      意见反馈
     * @param content       意见内容
     * @param phone         联系方式
     * @return
     */
    public static Map<String,String> getFeedBack(String content,String phone){
        Map<String,String> map = new HashMap<>();
        map.putAll(getCurrencyMap());
        map.put("content",content);
        map.put("contact",phone);
        return map;
    }

    /**
     *      订单详情
     * @param type          订单类型    1:支付    2:打赏
     * @param pid           商品ID
     * @param amount        打赏订单必填,支付可不填
     * @param pway          支付类型    1:微信    2:支付宝
     * @return
     */
    public static Map<String,String> getOrder(int type,int pid,float amount,int pway){
        Map<String,String> map = new HashMap<>();
        map.putAll(getCurrencyMap());
        map.put("type",String.valueOf(type));
        map.put("pid",String.valueOf(pid));
        map.put("amount",String.valueOf(amount));
        map.put("pway",String.valueOf(pway));
        return map;
    }

}
