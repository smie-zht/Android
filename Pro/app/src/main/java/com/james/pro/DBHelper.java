package com.james.pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ER on 2017/11/12.
 */


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=3;
    //database version
    private static final String DATABASE_NAME="sqlitesanguo.db";
    //database name
    public  DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PEOPLE="CREATE TABLE People"
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, force TEXT,pimage INTEGER,name TEXT,info TEXT,mapj DOUBLE,mapw DOUBLE)";
        db.execSQL(CREATE_TABLE_PEOPLE);
    }
    //create database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS"+People_info.TABLE);
        //delete old database
        onCreate(db);
    }
}