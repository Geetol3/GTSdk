package com.gtdev5.geetolsdk.mylibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gtdev5.geetolsdk.mylibrary.beans.LoginInfoBean;
import com.gtdev5.geetolsdk.mylibrary.beans.ResultBean;
import com.gtdev5.geetolsdk.mylibrary.beans.UpdateBean;
import com.gtdev5.geetolsdk.mylibrary.callback.BaseCallback;
import com.gtdev5.geetolsdk.mylibrary.callback.DataCallBack;
import com.gtdev5.geetolsdk.mylibrary.contants.API;
import com.gtdev5.geetolsdk.mylibrary.contants.Contants;
import com.gtdev5.geetolsdk.mylibrary.util.CPResourceUtils;
import com.gtdev5.geetolsdk.mylibrary.util.DataSaveUtils;
import com.gtdev5.geetolsdk.mylibrary.util.DeviceUtils;
import com.gtdev5.geetolsdk.mylibrary.util.GsonUtils;
import com.gtdev5.geetolsdk.mylibrary.util.LogUtils;
import com.gtdev5.geetolsdk.mylibrary.util.MapUtils;
import com.gtdev5.geetolsdk.mylibrary.util.SpUtils;
import com.gtdev5.geetolsdk.mylibrary.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cheng
 * PackageName ModelTest
 * 2018/1/4 9:28
 * Http请求类
 */

public class HttpUtils {
    public static final int GET_HTTP_TYPE = 1;//get请求
    public static final int POST_HTTP_TYPE = 2;//post请求
    public static final int UPLOAD_HTTP_TYPE = 3;//上传请求
    public static final int DOWNLOAD_HTTP_TYPE = 4;//下载请求
    private static HttpUtils mHttpUtils;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Request request = null;
    private MessageDigest alga;
    private Map<String, String> resultMap;
    private String string;
    private boolean isHave;
    private Gson gson;
    private String commonUrl;

    private HttpUtils() {
        try {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(
                    10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);
            mHandler = new Handler(Looper.getMainLooper());
            gson = new Gson();
            alga = MessageDigest.getInstance("SHA-1");
            //初始化域名
            commonUrl = SpUtils.getInstance().getString(Contants.COMMON_URL, API.COMMON_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (mHttpUtils == null) {
                    mHttpUtils = new HttpUtils();
                }
            }
        }
        return mHttpUtils;
    }

    /**
     * 提供对外调用的请求接口
     *
     * @param callBack   回调接口
     * @param url        路径
     * @param type       请求类型
     * @param paramKey   请求参数
     * @param paramValue 请求值
     */
    public static void externalHttpsNetWorkRequest(final DataCallBack callBack, final String url, final int type,
                                           final String[] paramKey, final Object[] paramValue) {
        getInstance().external_httpsNetWorkRequest(callBack, url, type, paramKey, paramValue);
    }

    /**
     * 提供对外调用的请求接口
     *
     * @param callBack   回调接口
     * @param url        路径
     * @param type       请求类型
     * @param paramKey   请求参数
     * @param paramValue 请求值
     */
    public static void httpsNetWorkRequest(final DataCallBack callBack, final String url, final int type,
                                           final String[] paramKey, final Object[] paramValue) {
        getInstance().inner_httpsNetWorkRequest(callBack, url, type, paramKey, paramValue);
    }

    /**
     * 内部处理请求的方法
     *
     * @param callBack   回调接口
     * @param url        路径
     * @param type       请求类型
     * @param paramKey   请求参数
     * @param paramValue 请求值
     */
    private void inner_httpsNetWorkRequest(final DataCallBack callBack, final String url, final int type,
                                           final String[] paramKey, final Object[] paramValue) {
        RequestBody requestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("appid", CPResourceUtils.getString("appid"));
        map.put("sign", null);
        map.put("device", DeviceUtils.getSpDeviceId());
        if (paramKey != null) {
            for (int i = 0; i < paramKey.length; i++) {
                map.put(paramKey[i], String.valueOf(paramValue[i]));
            }
            resultMap = sortMapByKey(map);
        }
        String str = "";
        int num = 0;
        boolean isFirst = true;
        switch (type) {
            case GET_HTTP_TYPE:
                request = new Request.Builder().url(commonUrl + url).build();
                break;
            case POST_HTTP_TYPE:
                /**
                 * 循环遍历获取key值，拼接sign字符串
                 */
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    num++;
                    if (isFirst) {
                        str += entry.getKey() + "=" + Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                        isFirst = !isFirst;
                    } else {
                        str = str.trim();
                        str += "&" + entry.getKey() + "=" + Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                        if (num == resultMap.size() - 1) {
                            str += "&" + "key" + "=" + CPResourceUtils.getString("appkey");
                        }
                    }
                }
                str = str.replace("\n", "");//去除换行
                str = str.replace("\\s", "");//去除空格
                isFirst = !isFirst;
                alga.update(str.getBytes());
                /**
                 * 循环遍历value值，添加到表单
                 */
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value == null) {
                        value = "";
                    }
                    if (key.equals("sign")) {
                        value = Utils.byte2hex(alga.digest());
                    } else if (key.equals("key")) {
                        continue;
                    }
                    builder.add(key, value);
                }
                requestBody = builder.build();
                request = new Request.Builder().url(commonUrl + url).post(requestBody).build();
                break;
            case UPLOAD_HTTP_TYPE:
                MultipartBody.Builder multipartBody = new MultipartBody.Builder("-----").setType(MultipartBody.FORM);
                if (paramKey != null && paramValue != null) {
                    for (int i = 0; i < paramKey.length; i++) {
                        multipartBody.addFormDataPart(paramKey[i], String.valueOf(paramValue[i]));
                    }
                    requestBody = multipartBody.build();
                }
                request = new Request.Builder().url(commonUrl + url).post(requestBody).build();
                break;
            default:
                break;
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }

    /**
     * 外部处理请求的方法
     *
     * @param callBack   回调接口
     * @param url        路径
     * @param type       请求类型
     * @param paramKey   请求参数
     * @param paramValue 请求值
     */
    private void external_httpsNetWorkRequest(final DataCallBack callBack, final String url, final int type,
                                           final String[] paramKey, final Object[] paramValue) {
        RequestBody requestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("appid", CPResourceUtils.getString("appid"));
        map.put("sign", null);
        map.put("device", DeviceUtils.getSpDeviceId());
        if (paramKey != null) {
            for (int i = 0; i < paramKey.length; i++) {
                map.put(paramKey[i], String.valueOf(paramValue[i]));
            }
            resultMap = sortMapByKey(map);
        }
        String str = "";
        int num = 0;
        boolean isFirst = true;
        switch (type) {
            case GET_HTTP_TYPE:
                request = new Request.Builder().url(url).build();
                break;
            case POST_HTTP_TYPE:
                /**
                 * 循环遍历获取key值，拼接sign字符串
                 */
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    num++;
                    if (isFirst) {
                        str += entry.getKey() + "=" + Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                        isFirst = !isFirst;
                    } else {
                        str = str.trim();
                        str += "&" + entry.getKey() + "=" + Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                        if (num == resultMap.size() - 1) {
                            str += "&" + "key" + "=" + CPResourceUtils.getString("appkey");
                        }
                    }
                }
                str = str.replace("\n", "");//去除换行
                str = str.replace("\\s", "");//去除空格
                isFirst = !isFirst;
                alga.update(str.getBytes());
                /**
                 * 循环遍历value值，添加到表单
                 */
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value == null) {
                        value = "";
                    }
                    if (key.equals("sign")) {
                        value = Utils.byte2hex(alga.digest());
                    } else if (key.equals("key")) {
                        continue;
                    }
                    builder.add(key, value);
                }
                requestBody = builder.build();
                request = new Request.Builder().url(url).post(requestBody).build();
                break;
            case UPLOAD_HTTP_TYPE:
                MultipartBody.Builder multipartBody = new MultipartBody.Builder("-----").setType(MultipartBody.FORM);
                if (paramKey != null && paramValue != null) {
                    for (int i = 0; i < paramKey.length; i++) {
                        multipartBody.addFormDataPart(paramKey[i], String.valueOf(paramValue[i]));
                    }
                    requestBody = multipartBody.build();
                }
                request = new Request.Builder().url(url).post(requestBody).build();
                break;
            default:
                break;
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }

    /**
     * 分发失败的时候回调
     */
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack) {
        mHandler.post(() -> {
            if (callBack != null) {
                callBack.requestFailure(request, e);
            }
        });
    }

    /**
     * 分发成功的时候回调
     */
    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        mHandler.post(() -> {
            if (callBack != null) {
                try {
                    callBack.requestSuceess(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * map根据key值比较大小
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>((str1, str2) -> str1.compareTo(str2));
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 内部处理Map集合
     * 得到from表单 (post请求)
     */
    private RequestBody getRequestBody(Map<String, String> map) {
        RequestBody requestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        resultMap = sortMapByKey(map);
        LogUtils.e("==========HttpUtils==========请求参数map:" + resultMap.toString());
        StringBuilder str = new StringBuilder();
        int num = 0;
        boolean isFirst = true;
        /**
         * 循环遍历获取key值，拼接sign字符串
         */
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            num++;
            if (isFirst) {
                String value = Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                str.append(entry.getKey()).append("=").append(value);
                isFirst = false;
            } else {
                str = new StringBuilder(str.toString().trim());
                String value = Base64.encodeToString(entry.getValue().getBytes(), Base64.DEFAULT).trim();
                str.append("&").append(entry.getKey()).append("=").append(value);
                if (num == resultMap.size() - 1) {
                    str.append("&" + "key" + "=").append(CPResourceUtils.getString("appkey"));
                }
            }
        }
        String key_value = str.toString().replace("\n", "") //去除换行
                .replace("\\s", "");//去除空格
        isFirst = !isFirst;
        /**
         * 循环遍历value值，添加到表单
         */
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            if (key.equals("sign")) {
                alga.update(key_value.getBytes());
                value = Utils.byte2hex(alga.digest());
            } else if (key.equals("key")) {
                continue;
            }
            String finalValue = value.replace("+", "%2B")
                    .replace("\n", "")
                    .replace("\\s", "");
            builder.add(key, finalValue);
        }
        requestBody = builder.build();
        return requestBody;
    }

    /**
     * 获取app的下载链接
     *
     * @param callback 回调函数
     */
    public void postGetAppUrl(long apid, BaseCallback callback) {
        post(commonUrl + API.GET_APPURL, MapUtils.getAppUrlMap(apid), callback);
    }

    /**
     * 添加反馈
     *
     * @param callback 回调函数
     */
    public void postAddService(String title, String descibe, String type, String img, BaseCallback callback) {
        post(commonUrl + API.ADD_SERVICE, MapUtils.getAddServiceMap(title, descibe, type, img), callback);
    }

    /**
     * 获取反馈列表
     */
    public void postGetServices(int page, int limit, BaseCallback callback) {
        post(commonUrl + API.GET_SERVICE, MapUtils.getGetServiceMap(page, limit), callback);
    }

    /**
     * 获取反馈详情
     */
    public void postGetServicesDetails(int service_id, BaseCallback callback) {
        post(commonUrl + API.GET_SERVICE_DETAILS, MapUtils.getServiceDetialsMap(service_id), callback);
    }

    /**
     * 添加反馈回复
     *
     * @param service_id 服务单id
     * @param repley     回复内容
     * @param img        回复图片   base64处理的图片 多个用，分割
     */
    public void postAddRepley(int service_id, String repley, String img, BaseCallback callback) {
        post(commonUrl + API.ADD_REPLEY, MapUtils.getAddRepleyMap(service_id, repley, img), callback);
    }

    /**
     * 结束反馈
     *
     * @param id       服务单id
     */
    public void postEndService(int id, BaseCallback callback) {
        post(commonUrl + API.END_SERVICE, MapUtils.getServiceDetialsMap(id), callback);
    }

    /**
     * 提供给外部调用的注册接口
     *
     * @param callback 回调函数
     */
    public void postRegister(BaseCallback callback) {
        post(commonUrl + API.REGIST_DEVICE, MapUtils.getRegistMap(), callback);
    }

    /**
     * 提供给外部调用的更新数据接口
     *
     * @param callback 回调函数
     */
    public void postUpdate(BaseCallback callback) {
        post(commonUrl + API.UPDATE, MapUtils.getCurrencyMap(), callback, API.UPDATE);
    }

    /**
     * 提供外部调用的更新数据接口(带用户信息的)
     * @param callback 回调函数
     */
    public void updateAllData(BaseCallback callback) {
        post(commonUrl + API.UPDATE, MapUtils.getCommonUserMap(), callback);
    }

    /**
     * 提供给外部调用的版本更新接口
     *
     * @param callback 回调函数
     */
    public void postNews(BaseCallback callback) {
        post(commonUrl + API.GETNEW, MapUtils.getNewMap(), callback);
    }

    /**
     * 提供给外部调用的意见反馈接口
     *
     * @param content  意见内容
     * @param phone    联系方式
     * @param callback 回调函数
     */
    public void postFeedBack(String content, String phone, BaseCallback callback) {
        post(commonUrl + API.FEEDBACK, MapUtils.getFeedBack(content, phone), callback);
    }

    /**
     * 提供给外部调用的支付订单接口
     *
     * @param type     订单类型    1:支付    2:打赏
     * @param pid      商品ID
     * @param amount   打赏订单必填,支付可不填
     * @param pway     支付类型    1:微信    2:支付宝
     * @param callback 回调函数
     */
    public void postOrder(int type, int pid, float amount, int pway, BaseCallback callback) {
        post(commonUrl + API.ORDER_ONE, MapUtils.getOrder(type, pid, amount, pway), callback);
    }

    /**
     * 新接口
     * 提供给外部调用的支付订单接口
     *
     * @param type     订单类型    1:支付    2:打赏
     * @param pid      商品ID
     * @param amount   打赏订单必填,支付可不填
     * @param pway     支付类型    1:微信    2:支付宝
     * @param callback 回调函数
     */
    public void PostOdOrder(int type, int pid, float amount, int pway, BaseCallback callback) {
        post(commonUrl + API.ORDER_OD, MapUtils.getOrder(type, pid, amount, pway), callback);
    }

    /**
     * 提供外部调用的获取验证码接口
     * @param tel 手机号
     * @param tpl 信息模板（SMSCode已提供基本类型）
     * @param sms_sign 短信签名
     * @param callback 回调函数
     */
    public void getVarCode(String tel, String tpl, String sms_sign, BaseCallback callback) {
        post(commonUrl + API.GET_VARCODE, MapUtils.getVarCode(tel, tpl, sms_sign), callback);
    }

    /**
     * 提供外部调用的注册接口
     * @param tel 手机号
     * @param code 验证码
     * @param pwd 密码
     * @param ckey 验证码接口返回的令牌
     * @param callback 回调函数
     */
    public void userRegister(String tel, String code, String pwd, String ckey, BaseCallback callback) {
        post(commonUrl + API.USER_REGISTER, MapUtils.userRegister(tel, code, pwd, ckey), callback);
    }

    /**
     * 提供外部调用的登陆接口
     * @param name 账号
     * @param pwd 密码
     * @param callback 回调函数
     */
    public void userLogin(String name, String pwd, BaseCallback callback) {
        post(commonUrl + API.USER_LOGIN, MapUtils.userLogin(name, pwd), callback, API.USER_LOGIN);
    }

    /**
     * 提供外部调用的修改密码接口
     * @param opwd 旧密码
     * @param npwd 新密码
     * @param callback 回调函数
     */
    public void modifyPwd(String opwd, String npwd, BaseCallback callback) {
        post(commonUrl + API.MODIFY_PWD, MapUtils.modifyPwd(opwd, npwd), callback);
    }

    /**
     * 提供外部调用的忘记密码接口
     * @param tel 手机号
     * @param code 验证码
     * @param npwd 新密码
     * @param ckey 验证码接口返回的令牌
     * @param callback 回调函数
     */
    public void forgetPwd(String tel, String code, String npwd, String ckey, BaseCallback callback) {
        post(commonUrl + API.FORGET_PWD, MapUtils.forgetPwd(tel, code, npwd, ckey), callback);
    }

    /**
     * 提供外部调用的设置头像接口
     * @param img 用户头像base64字符串
     * @param name 上传文件的名字，必须带上扩展名
     * @param callback 回调函数
     */
    public void setHeadImg(String img, String name, BaseCallback callback) {
        post(commonUrl + API.SET_HEADING, MapUtils.setUserHead(img, name), callback);
    }

    /**
     * 提供外部调用的获取头像接口
     * @param name 获取头像文件的名字
     * @param callback 回调函数
     */
    public void getHeadImg(String name, BaseCallback callback) {
        post(commonUrl + API.GET_HEADING, MapUtils.getUserHead(name), callback);
    }

    /**
     * 提供外部调用的获取阿里云返回参数接口
     * @param callback 回调函数
     */
    public void getAliOss(BaseCallback callback) {
        post(commonUrl + API.GET_ALIOSS, MapUtils.getCurrencyMap(), callback, API.GET_ALIOSS);
    }

    /**
     * 动态码登录接口
     * 2019.11.11新增
     * @param tel 手机号
     * @param smscode 短信验证码
     * @param smskey 短信认证码校验key
     * @param callback
     */
    public void userCodeLogin(String tel, String smscode, String smskey, BaseCallback callback) {
        post(commonUrl + API.USER_LOGIN_CODE, MapUtils.getUserCodeLogin(tel, smscode, smskey), callback,
                API.USER_LOGIN_CODE);
    }

    /**
     * 登陆校验
     */
    public void checkLogin(BaseCallback callback) {
        post(commonUrl + API.USER_LOGIN_CHECK, MapUtils.getCurrencyMap(), callback, API.USER_LOGIN_CHECK);
    }

    /**
     * 微信登录
     */
    public void wechatLogin(String open_id, String nickname, String sex, String headurl, BaseCallback callback) {
        alga.digest();
        post(commonUrl + API.USER_WECHAT_LOGIN, MapUtils.getWeChatLogin(open_id, nickname, sex, headurl), callback);
    }

    /**
     * 针对微信加密机制的问题，提供一个外部方法来解决
     */
    public void changeDigest() {
        if (alga != null) {
            alga.digest();
        }
    }

    /**
     * 内部提供的post请求方法
     *
     * @param url      请求路径
     * @param params   请求参数(表单)
     * @param callback 回调函数
     */
    public void post(String url, Map<String, String> params, final BaseCallback callback) {
        post(url, params, callback, "default");
    }

    public void post(String url, Map<String, String> params, final BaseCallback callback, String requestType) {
        //请求之前调用(例如加载动画)
        callback.onRequestBefore();
        mOkHttpClient.newCall(getRequest(url, params)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //返回失败
                callbackFailure(call.request(), callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //返回成功回调
                    try {
                        String result = response.body().string();
                        LogUtils.e("==========HttpUtils==========请求返回数据："+result);
                        if (requestType.equals(API.USER_LOGIN) || requestType.equals(API.USER_LOGIN_CODE)) {
                            // 保存用户信息
                            LoginInfoBean info = GsonUtils.getFromClass(result, LoginInfoBean.class);
                            if (info != null && info.isIssucc()) {
                                Utils.setLoginInfo(info.getData().getUser_id(),
                                        info.getData().getUkey(),
                                        info.getData().getHeadimg());
                            }
                        } else if (requestType.equals(API.GET_ALIOSS)) {
                            // 获取阿里云信息
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getBoolean("issucc")) {
                                    String data = jsonObject.getString("data");
                                    if (!TextUtils.isEmpty(data)) {
                                        LogUtils.e("==========HttpUtils==========请求返回数据,阿里云数据："+data);
                                        Utils.setAliOssParam(data);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (requestType.equals(API.UPDATE)) {
                            // 获取所有数据
                            UpdateBean updateBean = GsonUtils.getFromClass(result, UpdateBean.class);
                            if (updateBean != null && updateBean.getIssucc()) {
                                DataSaveUtils.getInstance().saveAppData(updateBean);
                            }
                        } else if (requestType.equals(API.USER_LOGIN_CHECK)) {
                            // 校验登陆
                            ResultBean resultBean = GsonUtils.getFromClass(result, ResultBean.class);
                            if (resultBean != null && !resultBean.isIssucc()) {
                                // 已在别的设备登陆，清空本机登陆状态
                                Utils.setLoginInfo("", "", "");
                            }
                        }
                        if (callback.mType == String.class) {
                            //如果我们需要返回String类型
                            callbackSuccess(response, result, callback);
                        } else {
                            //如果返回是其他类型,则用Gson去解析
                            try {
                                Object o = gson.fromJson(result, callback.mType);
                                callbackSuccess(response, o, callback);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                callbackError(response, callback, e);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackError(response, callback, null);
                    }
                } else {
                    callbackError(response, callback, null);
                }
            }
        });
    }

    /**
     * 得到Request
     *
     * @param url    请求路径
     * @param params from表单
     */
    private Request getRequest(String url, Map<String, String> params) {
        //可以从这么划分get和post请求，暂时只支持post
        LogUtils.e("==========HttpUtils==========请求参数url:"+url);
        return new Request.Builder().url(url).post(getRequestBody(params)).build();
    }

    /**
     * 在主线程中执行成功回调
     *
     * @param response 请求响应
     * @param o        类型
     * @param callback 回调函数
     */
    private void callbackSuccess(final Response response, final Object o, final BaseCallback<Object> callback) {
        mHandler.post(() -> callback.onSuccess(response, o));
    }

    /**
     * 在主线程中执行错误回调
     *
     * @param response 请求响应
     * @param callback 回调函数
     * @param e        响应错误异常
     */
    private void callbackError(final Response response, final BaseCallback callback, Exception e) {
        mHandler.post(() -> callback.onError(response, response.code(), e));
    }

    /**
     * 在主线程中执行失败回调
     *
     * @param request  请求链接
     * @param callback 回调韩素和
     * @param e        响应错误异常
     */
    private void callbackFailure(final Request request, final BaseCallback callback, final Exception e) {
        mHandler.post(() -> callback.onFailure(request, e));
    }
}
