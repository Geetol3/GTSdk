package com.zeoy.gee_sdk.callback;

import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by cheng
 * PackageName ModelTest
 * 2018/1/23 15:38
 * 回调接口
 */

public abstract class BaseCallback<T> {

    /**
     * tpye用于方便json解析
     */
    public Type mType;


    /**
     *      把type转换成对应的类
     * @param subclass
     * @return
     */
    public static Type getSuperclassTypeParamter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class){
            throw new RuntimeException("Missing type paramter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }


    /**
     * 构造方法
     *      实例化的时候获取type的Class类型
     */
    public BaseCallback(){
        mType = getSuperclassTypeParamter(getClass());
    }

    /**
     * 请求之前调用
     */
    public abstract void onRequestBefore();

    /**
     * 请求失败调用
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request, Exception e);

    /**
     * 请求成功调用
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response, T t);

    /**
     * 请求成功但是有错误的时候调用,例如Gson解析
     * @param response
     * @param errorCode
     * @param e
     */
    public abstract void onError(Response response,int errorCode,Exception e);

}
