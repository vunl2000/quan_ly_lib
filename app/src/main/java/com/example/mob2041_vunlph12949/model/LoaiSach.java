package com.example.mob2041_vunlph12949.model;

public class LoaiSach {
    private int maLoai;
    private String tenLoai;


    public final static String TB_NAME = "LoaiSach";
    public final static String COL_ID = "maLoai";
    public final static String COL_NAME = "tenLoai";

    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
