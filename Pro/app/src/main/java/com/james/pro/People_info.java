package com.james.pro;

import android.util.Log;

/**
 * Created by ER on 2017/11/12.
 */

public class People_info {
    public static final String TABLE="People";
    //table name
    public static final String KEY_NAME="name";
    public static final String KEY_FORCE="force";
    public static final String KEY_IMAGE="pimage";
    public static final String KEY_INFO="info";
    public static final String KEY_ID="id";
    public static final String KEY_MAP1="mapj";
    public static final String KEY_MAP2="mapw";

    public String name;
    public String force;
    public int pimage;
    public String info;
    public int id;
    public double mapj;
    public double mapw;

    public void complete(int id,String name,String force,int pimage,String info,String mapj,String mapw){
        this.id =id;
        this.force = force;
        this.info = info;
        this.pimage = pimage;
        this.name = name;
        this.mapj=Double.valueOf(mapj);
        Log.v("输入人物",mapj);
        this.mapw=Double.valueOf(mapw);
    }
    public int getImageSrc() {
        return pimage;
    }
    public String getName() {
        return name;
    }
    public String getForce() {return force;}
    public String getInfo() {return info;}
    public double getMapj() {return mapj;}
    public double getMapw() {return mapw;}
    public int getId() {return id;}

}
