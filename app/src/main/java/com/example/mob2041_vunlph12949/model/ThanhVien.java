package com.example.mob2041_vunlph12949.model;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private String namSinh;

    public final static String TB_NAME = "ThanhVien";
    public final static String COL_ID = "maTV";
    public final static String COL_NAME = "hoTen";
    public final static String COL_DATE = "namSinh";


    public ThanhVien(int maTV, String hoTen, String namSinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVien() {
    }

    public ThanhVien(ThanhVien thanhVien) {
        maTV = thanhVien.getMaTV();
        hoTen = thanhVien.getHoTen();
        namSinh = thanhVien.getNamSinh();
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
