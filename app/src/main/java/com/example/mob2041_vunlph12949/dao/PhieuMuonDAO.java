package com.example.mob2041_vunlph12949.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_vunlph12949.database.DbHelper;
import com.example.mob2041_vunlph12949.model.PhieuMuon;

import java.util.ArrayList;

import java.util.List;

public class PhieuMuonDAO {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();

        values.put(PhieuMuon.COL_MATT, phieuMuon.getMaTT());
        values.put(PhieuMuon.COL_MS, phieuMuon.getMaSach());
        values.put(PhieuMuon.COL_MTV, phieuMuon.getMaTV());
        values.put(phieuMuon.COL_DATE, phieuMuon.getNgay());
        values.put(PhieuMuon.COL_PRICE, phieuMuon.getTienThue());
        values.put(PhieuMuon.COL_CHECK, phieuMuon.getTraSach());

        return database.insert(PhieuMuon.TB_NAME, null, values);
    }

    public int update(PhieuMuon phieuMuon) {

        ContentValues values = new ContentValues();

        values.put(PhieuMuon.COL_MATT, phieuMuon.getMaTT());
        values.put(PhieuMuon.COL_MS, phieuMuon.getMaSach());
        values.put(PhieuMuon.COL_MTV, phieuMuon.getMaTV());
        values.put(PhieuMuon.COL_DATE, String.valueOf(phieuMuon.getNgay()));
        values.put(PhieuMuon.COL_PRICE, phieuMuon.getTienThue());
        values.put(PhieuMuon.COL_CHECK, phieuMuon.getTraSach());

        return database.update(PhieuMuon.TB_NAME, values, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int delete(String id) {
        return database.delete(PhieuMuon.TB_NAME, "maPM=?", new String[]{id});
    }

    public PhieuMuon getId(String id) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }

    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM PhieuMuon";
        List<PhieuMuon> list = getData(sql);
        return list;
    }

    public List<PhieuMuon> getData(String sql, String... Args) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, Args);
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_ID))));
            phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_MATT)));
            phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_MTV))));
            phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_MS))));
            phieuMuon.setNgay(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_DATE)));
            phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_PRICE))));
            phieuMuon.setTraSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_CHECK))));
            list.add(phieuMuon);
        }
        return list;
    }

    public int getDoanhThu(String startDay, String endDay) {
        String doanhThu = "SELECT SUM(tienThue) AS doanhThu FROM PhieuMuon WHERE ngay >= ? AND ngay <= ?";
        Cursor cursor = database.rawQuery(doanhThu, new String[]{startDay, endDay});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public List<PhieuMuon> getDoanhThuCT(String startDay, String endDay) {
        String sql = "SELECT * FROM PhieuMuon WHERE ngay >= ? AND ngay <= ? ORDER BY ngay";
        List<PhieuMuon> list = getData(sql, startDay, endDay);
        return list;
    }
}
