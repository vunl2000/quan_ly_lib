package com.example.mob2041_vunlph12949.model;

public class PhieuMuon {
    private int maPM;
    private String maTT;
    private int maSach;
    private int maTV;
    private String ngay;
    private int tienThue;
    private int traSach;

    public final static String TB_NAME = "PhieuMuon";
    public final static String COL_ID = "maPM";
    public final static String COL_MATT = "maTT";
    public final static String COL_MS = "maSach";
    public final static String COL_MTV = "maTV";
    public final static String COL_DATE = "ngay";
    public final static String COL_PRICE = "tienThue";
    public final static String COL_CHECK = "traSach";

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, String maTT, int maSach, int maTV, String ngay, int tienThue, int traSach) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maSach = maSach;
        this.maTV = maTV;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
