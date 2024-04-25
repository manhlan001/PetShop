package com.example.petshop;

public class NhanVien {
    private String name;
    private String old;
    private String sex;
    private String sdt;
    private String gmail;
    private String diachi;
    private String chucvu;
    private String date;
    private String luong;
    public NhanVien(String name, String old, String sex, String sdt, String gmail, String diachi, String chucvu,String date, String luong) {
        this.name = name;
        this.old = old;
        this.sex = sex;
        this.sdt = sdt;
        this.gmail = gmail;
        this.diachi = diachi;
        this.chucvu = chucvu;
        this.date = date;
        this.luong = luong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLuong(String luong) { this.luong = luong; }

    public String getOld() {
        return old;
    }

    public String getSex() {
        return sex;
    }

    public String getSdt() {
        return sdt;
    }

    public String getGmail() {
        return gmail;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getChucvu() {
        return chucvu;
    }

    public String getDate() {
        return date;
    }

    public String getLuong() { return luong; }
}
