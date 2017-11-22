package com.james.pro;

/**
 * Created by qqq on 2017/11/9.
 */

import java.io.Serializable;


public class General implements Serializable{
    private int imageSrc;
    private String name;
    private String detail;
    public int getImageSrc() {
        return imageSrc;
    }
    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Override
    public String toString() {
        return "General [imageSrc=" + imageSrc + ", name=" + name + ", detail="
                + detail + "]";
    }
    public General(int imageSrc, String name, String detail) {
        super();
        this.imageSrc = imageSrc;
        this.name = name;
        this.detail = detail;
    }


}