package com.example.mob2041_vunlph12949.model;

public class ThuThu {
    private String maTT;
    private String hoTen;
    private String maKhau;

    public static final String TB_NAME = "ThuThu";
    public static final String COL_MATT = "maTT";
    public static final String COL_TENTT = "hoTen";
    public static final String COL_MK = "matKhau";


    public ThuThu() {
    }

    public ThuThu(String maTT, String hoTen, String maKhau) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.maKhau = maKhau;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaKhau() {
        return maKhau;
    }

    public void setMaKhau(String maKhau) {
        this.maKhau = maKhau;
    }
}
