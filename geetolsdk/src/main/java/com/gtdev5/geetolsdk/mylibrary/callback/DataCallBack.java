package com.gtdev5.geetolsdk.mylibrary.callback;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by cheng
 * PackageName ModelTest
 * 2018/1/4 9:41
 *          回调接口
 */

public interface DataCallBack {

    void requestFailure(Request request, IOException io);//失败

    void requestSuceess(String result) throws Exception;//成功
}
