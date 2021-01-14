package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;
import java.util.List;

public class ListResultBean <T>implements Serializable {
    private int page;

    private int count;

    private List<T> items;

    private boolean issucc;

    private String msg;

    private String code;

    public int getPage(){
        return this.page;
    }
    public void setPage(int page){
        this.page = page;
    }

    public int getCount(){
        return this.count;
    }
    public void setCount(int count){
        this.count = count;
    }

    public List<T> getItems(){
        return this.items;
    }
    public void setItems(List<T> items){
        this.items = items;
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
