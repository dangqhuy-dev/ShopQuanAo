package com.example.shopquanao.Model;

public class GIOHANG {

    private SANPHAM sanpham;
    private int soluong;

    public GIOHANG(SANPHAM sanpham, int soluong) {
        this.sanpham = sanpham;
        this.soluong = soluong;
    }

    public SANPHAM getSanpham() {
        return sanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getTongTien() {
        return sanpham.getGia() * soluong;
    }
}