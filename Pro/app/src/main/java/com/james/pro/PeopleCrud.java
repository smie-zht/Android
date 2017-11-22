package com.james.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ER on 2017/11/12.
 */

public class PeopleCrud {
    private DBHelper dbHelper;
    private  int count;
    public PeopleCrud(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(People_info info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //查询要插入的元组的主键是否已经存在
        Cursor se = db.rawQuery(" select * from People where id = '"+info.id+" '", null);
        int count = se.getCount();
        if(count!=0){
            Log.v("要插入的人物id： ",String.valueOf(info.id)+" id存在，插入失败！");return -1;
        }
        else{
            //to save data into database,like hashmap
            values.put(People_info.KEY_FORCE, info.force);
            values.put(People_info.KEY_NAME, info.name);
            values.put(People_info.KEY_IMAGE, info.pimage);
            values.put(People_info.KEY_INFO, info.info);
            values.put(People_info.KEY_ID,info.id);
            values.put(People_info.KEY_MAP1,info.mapj);
            values.put(People_info.KEY_MAP2,info.mapw);
            db.insert(People_info.TABLE, null, values);
            db.close();
            Log.v("要插入的人物id： ",String.valueOf(info.id)+" 成功插入！");
            return info.id;
        }

    }

    public void delete(int People_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(People_info.TABLE, People_info.KEY_ID + " = ? ", new String[]{String.valueOf(People_id)});
        db.close();
    }

    public void update(People_info info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //to save data into database,like hashmap
        values.put(People_info.KEY_FORCE, info.force);
        values.put(People_info.KEY_NAME, info.name);
        values.put(People_info.KEY_IMAGE, info.pimage);
        values.put(People_info.KEY_INFO, info.info);
        values.put(People_info.KEY_MAP1,info.mapj);
        values.put(People_info.KEY_MAP2,info.mapw);
        db.update(People_info.TABLE, values, People_info.KEY_ID + " = ? ", new String[]{String.valueOf(info.id)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getpeopleList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = " SELECT " +
                People_info.KEY_ID + "," +
                People_info.KEY_NAME + "," +
                People_info.KEY_FORCE + "," +
                People_info.KEY_IMAGE + "," +
                People_info.KEY_MAP1 + "," +
                People_info.KEY_MAP2 + "," +
                People_info.KEY_INFO + " FROM " + People_info.TABLE;
        ArrayList<HashMap<String, String>> peopleList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // a set of every row
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> people = new HashMap<String, String>();
                people.put("image", cursor.getString(cursor.getColumnIndex(People_info.KEY_IMAGE)));
                people.put("name", cursor.getString(cursor.getColumnIndex(People_info.KEY_NAME)));
                peopleList.add(people);
            } while (cursor.moveToNext());
        }
        cursor.close();//release resource
        db.close();
        return peopleList;
    }

    public People_info getPeopleByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = " SELECT " +
                People_info.KEY_ID + "," +
                People_info.KEY_INFO + "," +
                People_info.KEY_IMAGE + "," +
                People_info.KEY_FORCE + "," +
                People_info.KEY_MAP1 + "," +
                People_info.KEY_MAP2 + "," +
                People_info.KEY_NAME + " FROM " + People_info.TABLE
                + " WHERE " + People_info.KEY_NAME + " = ? ";
        People_info people_info = new People_info();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(name)});
        int count = cursor.getCount();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.v("查询ing-id:",String.valueOf(cursor.getInt(cursor.getColumnIndex(People_info.KEY_ID))));
                Log.v("查询ing-name:",cursor.getString(cursor.getColumnIndex(People_info.KEY_NAME)));

                people_info.id = cursor.getInt(cursor.getColumnIndex(People_info.KEY_ID));
                people_info.force = cursor.getString(cursor.getColumnIndex(People_info.KEY_FORCE));
                people_info.info = cursor.getString(cursor.getColumnIndex(People_info.KEY_INFO));
                people_info.name = cursor.getString(cursor.getColumnIndex(People_info.KEY_NAME));
                people_info.pimage = cursor.getInt(cursor.getColumnIndex(People_info.KEY_IMAGE));
                people_info.mapj=cursor.getDouble(cursor.getColumnIndex(People_info.KEY_MAP1));
                people_info.mapw=cursor.getDouble(cursor.getColumnIndex(People_info.KEY_MAP2));
            }
            while (cursor.moveToNext());
        }
//        if(!cursor.moveToNext()) people_info.id = people_info.id -1;
        cursor.close();
        db.close();
        Log.v("使用名字查询结果：",String.valueOf(people_info.id));
        return people_info;
    }
}
