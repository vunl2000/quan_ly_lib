package com.example.mob2041_vunlph12949.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_vunlph12949.database.DbHelper;
import com.example.mob2041_vunlph12949.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();

        values.put(LoaiSach.COL_NAME, loaiSach.getTenLoai());

        return database.insert(LoaiSach.TB_NAME, null, values);
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();

        values.put(LoaiSach.COL_NAME, loaiSach.getTenLoai());

        return database.update(LoaiSach.TB_NAME, values, "maLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public int delete(String id) {
        return database.delete(LoaiSach.TB_NAME, "maLoai = ?", new String[]{id});
    }

    public LoaiSach getId(String id) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }

    public List<LoaiSach> getAll() {
        String sql = "SELECT * FROM LoaiSach";
        List<LoaiSach> list = getData(sql);
        return list;
    }

    public List<LoaiSach> getData(String sql, String... Args) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, Args);
        while (cursor.moveToNext()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LoaiSach.COL_ID))));
            loaiSach.setTenLoai(cursor.getString(cursor.getColumnIndex(LoaiSach.COL_NAME)));
            list.add(loaiSach);
        }
        return list;
    }
}
