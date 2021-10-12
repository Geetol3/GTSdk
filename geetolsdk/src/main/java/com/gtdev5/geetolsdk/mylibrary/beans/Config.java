package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

/**
 * Created by ZL on 2019/10/11
 *
 * 微信appid
 */

public class Config implements Serializable {
    private String wxid;
    private String wxsecret;

    public String getWxsecret() {
        return wxsecret;
    }

    public void setWxsecret(String wxsecret) {
        this.wxsecret = wxsecret;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }
}
