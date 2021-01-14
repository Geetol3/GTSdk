package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

/**
 * Info     ： Create by Zeoy
 * Introduce： 支付新接口回调实体类
 * Date     ： 2018/9/7
 */
public class OdResultBean implements Serializable{

    /**
     * appid : wx01c61151a96421a7
     * amount : 0.01
     * timestramp : 1535420549
     * nonce_str : BJJCTyKSerRKWRXwUtXDa7DC
     * package_str : Sign=WXPay
     * sign : 08F701BDB0392A59C4510226CB360162
     * qrcode :
     * mweburl :
     * partnerId : 1462965102
     * prepayid : wx28094229306750ba5586a6d63436360427
     * issucc : true
     * msg :
     * code :
     */

 /*   private boolean issucc;
    private String msg;
    private String code;
    private String appid;
    private float amount;
    private String package_str;*/

    private String appid;
    private String amount;
    private String timestramp;
    private String nonce_str;
    private String package_str;
    private String sign;

    public String getSign_str() {
        return sign_str;
    }

    public void setSign_str(String sign_str) {
        this.sign_str = sign_str;
    }

    private String sign_str;
    private String qrcode;
    private String mweburl;
    private String partnerId;
    private String prepayid;
    private boolean issucc;
    private String msg;
    private String code;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestramp() {
        return timestramp;
    }

    public void setTimestramp(String timestramp) {
        this.timestramp = timestramp;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPackage_str() {
        return package_str;
    }

    public void setPackage_str(String package_str) {
        this.package_str = package_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getMweburl() {
        return mweburl;
    }

    public void setMweburl(String mweburl) {
        this.mweburl = mweburl;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public boolean isIssucc() {
        return issucc;
    }

    public void setIssucc(boolean issucc) {
        this.issucc = issucc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
