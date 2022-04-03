package com.example.mob2041_vunlph12949.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_vunlph12949.database.DbHelper;
import com.example.mob2041_vunlph12949.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();

        values.put(ThanhVien.COL_NAME, thanhVien.getHoTen());
        values.put(ThanhVien.COL_DATE, thanhVien.getNamSinh());

        return database.insert(ThanhVien.TB_NAME, null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();

        values.put(ThanhVien.COL_NAME, thanhVien.getHoTen());
        values.put(ThanhVien.COL_DATE, thanhVien.getNamSinh());

        return database.update(ThanhVien.TB_NAME, values, "maTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public int delete(String maTV) {
        return database.delete(ThanhVien.TB_NAME, "maTV=?", new String[]{maTV});
    }

    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM ThanhVien";
        List<ThanhVien> list = getData(sql);
        return list;
    }

    public ThanhVien getId(String maTV) {
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql, maTV);
        return list.get(0);
    }

    public List<ThanhVien> getData(String sql, String... Args) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, Args);
        while (cursor.moveToNext()) {
            ThanhVien thanhVien = new ThanhVien();

            thanhVien.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_ID))));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME)));
            thanhVien.setNamSinh(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_DATE)));

            list.add(thanhVien);
        }
        return list;
    }
}
