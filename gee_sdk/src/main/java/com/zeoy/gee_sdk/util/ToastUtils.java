package com.zeoy.gee_sdk.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zeoy.gee_sdk.initialization.GeetolSDK;


/**
 * Created by cheng
 * PackageName ModelTest
 * 2018/1/4 14:24
 *          Toast工具类
 */

public class ToastUtils {
    private static Toast mToast = null;

    private static Context mContext;

    public static void init(Context context){
        if (mContext == null){
            mContext = context;
        }else {
            Log.e(GeetolSDK.TAG,"未初始化lib");
            return;
        }
    }

    /**
     *          short Toast
     * @param msg
     */
    public static void showShortToast(String msg){
        if (mToast == null){
            mToast = Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     *          long Toast
     * @param msg
     */
    public static void showLongToast(String msg){
        if (mToast == null){
            mToast = Toast.makeText(mContext,msg,Toast.LENGTH_LONG);
        }else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
