package com.gtdev5.geetolsdk.mylibrary.beans;

public class Nads {
    private String code;//广告位置代码
    private String name;//广告名称
    private String number;//广告编号代码
    private String desc;//广告说明
    private int type;//（自营广告此字段无意义）广告类型：1：开屏，2：横幅，3：插屏，4：激励视频，5：全屏视频广告，6：draw信息流，7：新插屏广告等
    private String channel;//广告渠道，例如[通用][oppo][vivo][华为][小米][荣耀]
    private int ips;
    private String urlPoint;//（非自营广告此字段无意义）自营广告跳转链接地址
    private String urlResources;//（非自营广告此字段无意义）自营广告显示的资源连接
    private String remark;//备注
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setIps(int ips) {
        this.ips = ips;
    }
    public int getIps() {
        return ips;
    }

    public void setUrlPoint(String urlPoint) {
        this.urlPoint = urlPoint;
    }
    public String getUrlPoint() {
        return urlPoint;
    }

    public void setUrlResources(String urlResources) {
        this.urlResources = urlResources;
    }
    public String getUrlResources() {
        return urlResources;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }
}
