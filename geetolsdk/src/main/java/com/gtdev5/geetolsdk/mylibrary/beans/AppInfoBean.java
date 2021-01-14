package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

/**
 * Info     ： Create by Zeoy
 * Introduce：
 * Date     ： 2018/8/4
 */
public class AppInfoBean implements Serializable{

    /**
     * app_id : 3
     * id : 36
     * ctime : 2018-04-18T09:04:05.627
     * downurl : http://app.wm002.cn/upload/app/com.gtdev5.applock/7e7f65af-774b-4ca4-bd66-18c7f8b908bc.apk|应用锁1.6.6已混淆加固发布版.apk|apk|2.24Mb
     * vername : 1.6.6
     */

    private int app_id;
    private int id;
    private String ctime;
    private String downurl;
    private String vername;

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getVername() {
        return vername;
    }

    public void setVername(String vername) {
        this.vername = vername;
    }
}
