package com.example.mob2041_vunlph12949.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_vunlph12949.database.DbHelper;
import com.example.mob2041_vunlph12949.model.LoaiSach;
import com.example.mob2041_vunlph12949.model.Sach;
import com.example.mob2041_vunlph12949.model.Top;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(Sach sach) {
        ContentValues values = new ContentValues();

        values.put(Sach.COL_NAME, sach.getTenSach());
        values.put(Sach.COL_PRICE, sach.getGiaThue());
        values.put(Sach.COL_LOAI, sach.getMaLoai());
        return database.insert(Sach.TB_NAME, null, values);
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();

        values.put(Sach.COL_NAME, sach.getTenSach());
        values.put(Sach.COL_PRICE, sach.getGiaThue());
        values.put(Sach.COL_LOAI, sach.getMaLoai());

        return database.update(Sach.TB_NAME, values, "maSach = ?", new String[]{String.valueOf(sach.getMaSach())});
    }

    public int delete(String id) {
        return database.delete(Sach.TB_NAME, "maSach = ?", new String[]{id});
    }

    public Sach getId(String id) {
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }

    public List<Sach> getAll() {
        String sql = "SELECT * FROM Sach";
        List<Sach> list = getData(sql);
        return list;
    }

    public List<Sach> getData(String sql, String... Args) {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, Args);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_ID))));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME)));
            sach.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_PRICE))));
            sach.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_LOAI))));
            list.add(sach);
        }
        return list;
    }
    public List<Top> getTop() {
        List<Top> list = new ArrayList<>();
        String selectTop = "SELECT maSach, count(maSach) AS soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        Cursor cursor = database.rawQuery(selectTop, null);
        while (cursor.moveToNext()) {
            Top top = new Top();
            Sach sach = getId(cursor.getString(cursor.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }
}
