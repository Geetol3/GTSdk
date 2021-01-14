package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

/**
 * Created by cheng
 * PackageName APP_Lock
 * 2018/1/24 11:58
 */

public class Gds implements Serializable {
    /**
     * value : 1                                      商品值
     * name : 测试                                    商品标题
     * price : 0.01                                   支付宝价格
     * gid : 145                                      支付gid   支付接口需要传此gid
     * remark :                                       介绍
     * original : 0.01                                原价格
     * bg1 : http://gtapp.ngrok.80xc.com:82           背景图片
     * bg2 : http://gtapp.ngrok.80xc.com:82           图标图片
     * xwprice : 0.02                                 微信价格
     * payway : [1][2]                                [1]有支付宝支付   [2]有微信支付
     */

    private String value;
    private String name;
    private String price;
    private int gid;
    private String remark;
    private String original;
    private String bg1;
    private String bg2;
    private String xwprice;
    private String payway;

    /*private String value;               //会员等级
    private String remark;           //标记
    private String original;          //原价
    private int gid;
    private String name;
    private String price;
    private String bg1;             //商品背景
    private String bg2;             //会员图标*/

    public String getXwprice() {
        return xwprice;
    }

    public void setXwprice(String xwprice) {
        this.xwprice = xwprice;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }
   public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private boolean select;

    public String getBg1() {
        return bg1;
    }

    public void setBg1(String bg1) {
        this.bg1 = bg1;
    }

    public String getBg2() {
        return bg2;
    }

    public void setBg2(String bg2) {
        this.bg2 = bg2;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

}