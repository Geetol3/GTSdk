package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

public class DataResultBean<T> implements Serializable {
    private T data;

    private boolean issucc;

    private String msg;

    private String code;

    public T getData(){
        return this.data;
    }
    public void setData(T data){
        this.data = data;
    }

    public boolean getIssucc(){
        return this.issucc;
    }
    public void setIssucc(boolean issucc){
        this.issucc = issucc;
    }

    public String getMsg(){
        return this.msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
}
