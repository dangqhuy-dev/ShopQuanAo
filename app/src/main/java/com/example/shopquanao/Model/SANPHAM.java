package com.example.shopquanao.Model;

import java.io.Serializable;

public class SANPHAM implements Serializable {
    private int id;
    private String tensanpham;
    private double gia;
    private String mota;
    private String hinh;
    private int soluong;
    private int danhmucid;

    public SANPHAM(int id, String tensanpham, double gia, String mota, String hinh, int soluong, int danhmucid) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.mota = mota;
        this.hinh = hinh;
        this.soluong = soluong;
        this.danhmucid = danhmucid;
    }
    public SANPHAM() {
    }

    public int getId() {
        return id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public double getGia() {
        return gia;
    }

    public String getMota() {
        return mota;
    }

    public String getHinh() {
        return hinh;
    }

    public int getSoluong() {
        return soluong;
    }

    public int getDanhmucid() {
        return danhmucid;
    }
}
