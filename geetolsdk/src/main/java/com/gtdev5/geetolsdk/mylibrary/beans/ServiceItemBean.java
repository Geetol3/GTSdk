package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

public class ServiceItemBean implements Serializable {
    private int id;

    private String addtime;

    private int status;

    private String title;

    private String describe;

    private String type;

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getAddtime(){
        return this.addtime;
    }
    public void setAddtime(String addtime){
        this.addtime = addtime;
    }

    public int getStatus(){
        return this.status;
    }
    public void setStatus(int status){
        this.status = status;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescribe(){
        return this.describe;
    }
    public void setDescribe(String describe){
        this.describe = describe;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
}
