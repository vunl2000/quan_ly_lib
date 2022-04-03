package com.example.mob2041_vunlph12949.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_vunlph12949.database.DbHelper;
import com.example.mob2041_vunlph12949.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();

        values.put(ThuThu.COL_MATT, thuThu.getMaTT());
        values.put(ThuThu.COL_TENTT, thuThu.getHoTen());
        values.put(ThuThu.COL_MK, thuThu.getMaKhau());
        return db.insert(ThuThu.TB_NAME, null, values);
    }

    public int update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put(ThuThu.COL_MATT, thuThu.getMaTT());
        values.put(ThuThu.COL_TENTT, thuThu.getHoTen());
        values.put(ThuThu.COL_MK, thuThu.getMaKhau());

        return db.update(ThuThu.TB_NAME, values, "maTT=?", new String[]{thuThu.getMaTT()});
    }

    public int updatePass(ThuThu thuThu) {
        ContentValues values = new ContentValues();

        values.put(ThuThu.COL_MK, thuThu.getMaKhau());

        return db.update(ThuThu.TB_NAME, values, "maTT=?", new String[]{thuThu.getMaTT()});
    }

    public int delete(String maTT) {
        return db.delete(ThuThu.TB_NAME, "maTT=?", new String[]{maTT});
    }

    public List<ThuThu> getAll() {
        String select = "SELECT * FORM ThuThu";
        return getData(select);
    }

    public ThuThu getId(String maTT) {
        String selectId = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(selectId, maTT);
        return list.get(0);
    }

    private List<ThuThu> getData(String sql, String... selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThuThu thuThu = new ThuThu();
            thuThu.setMaTT(cursor.getString(cursor.getColumnIndex(ThuThu.COL_MATT)));
            thuThu.setHoTen(cursor.getString(cursor.getColumnIndex(ThuThu.COL_TENTT)));
            thuThu.setMaKhau(cursor.getString(cursor.getColumnIndex(ThuThu.COL_MK)));
            list.add(thuThu);
        }
        return list;
    }

    public int getLogin(String user, String pass) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, user, pass);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getUser(String user) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? ";
        List<ThuThu> list = getData(sql, user);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
