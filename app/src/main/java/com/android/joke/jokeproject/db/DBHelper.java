package com.android.joke.jokeproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.android.joke.jokeproject.common.BaseBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "collection.db";
    private static final String TBL_NAME = "collection_name";
    private static final String CREATE_TBL = " create table "+TBL_NAME+"("
            +"_id integer primary key autoincrement,"
            +"name_id VARCHAR(255),"
            +"time VARCHAR(255),"
            +"name text);";

    private static SQLiteDatabase db;
    DBHelper(Context c) {
        super(c, DB_NAME, null, 1);
        db = getWritableDatabase();
    }

    private static DBHelper helperDB;
    public static DBHelper getIntences(Context context) {
        if (helperDB == null) {
            helperDB = new DBHelper(context);
        }
        return helperDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 添加一条数据
     *
     * @param  map 集合
     * */
    public void insert(Map<String,String> map) {
        if(map == null){
            return;
        }
        ContentValues values = new ContentValues();
        //迭代出集合的可以和value
        Iterator inter = map.entrySet().iterator();
        while (inter.hasNext()) {
            Map.Entry entry = (Map.Entry ) inter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            values.put(String.valueOf(key),String.valueOf(val));
        }
        if (db == null){
            db = getWritableDatabase();
        }
        db.insert(TBL_NAME, null, values);
        //db.close();
    }

    /** 从表中删除指定的一条数据 */
    public void del(String nameid) {
        if (db == null){
            db = getWritableDatabase();
        }
        db.delete(TBL_NAME, "name_id=?", new String[] { String.valueOf(nameid) });
    }

    /**
     * 获取记录
     */
    public ArrayList<BaseBean> getDataList() {
        if (db == null){
            db = getWritableDatabase();
        }
        Cursor cursor = db.rawQuery("select * from " + TBL_NAME,
                null);
        ArrayList<BaseBean> list = new ArrayList<BaseBean>();
        try {
            while (cursor.moveToNext()) {
                BaseBean bean = new BaseBean();
                bean.set("hid",cursor.getString(cursor.getColumnIndex("name_id")));
                bean.set("ptime",cursor.getString(cursor.getColumnIndex("time")));
                bean.set("intor",cursor.getString(cursor.getColumnIndex("name")));
                list.add(bean);
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**某条记录是否存在*/
    public synchronized boolean isExits(String name_id) {
        boolean isexit = false;
        if (db == null){
            db = getWritableDatabase();
        }
        Cursor cursor = db.query(TBL_NAME, null, "name_id=?",
                new String[] { name_id }, null, null, null, null);
        if (cursor.getCount() > 0) {
            isexit = true;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return isexit;
    }

    public void close() {
        if (db != null)
            db.close();
    }

}