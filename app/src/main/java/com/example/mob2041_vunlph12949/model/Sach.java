package com.example.mob2041_vunlph12949.model;

public class Sach {
    private int maSach;
    private int maLoai;
    private String tenSach;
    private int giaThue;


    public final static String TB_NAME ="Sach";
    public final static String COL_ID ="maSach";
    public final static String COL_NAME ="tenSach";
    public final static String COL_PRICE ="giaThue";
    public final static String COL_LOAI ="maLoai";

    public Sach() {
    }

    public Sach(int maSach, int maLoai, String tenSach, int giaThue) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }
}
