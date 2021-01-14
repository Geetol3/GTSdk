package com.gtdev5.geetolsdk.mylibrary.beans;

import java.io.Serializable;

/**
 * 商品的返回
 */

public class Ads implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String name;
    private String link;
    private String pos;
    private String img;
}
