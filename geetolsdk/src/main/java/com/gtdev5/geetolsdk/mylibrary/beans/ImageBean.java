package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

public class ImageBean implements Serializable {
    private String path;

    public String getPath(){
        return this.path;
    }
    public void setPath(String path){
        this.path = path;
    }
}
