package com.example.shopquanao.Model;

public class BANNER {
    private int id;
    private String tieude;
    private String hinh;

    public BANNER() {
    }

    public BANNER(int id, String tieude, String hinh) {
        this.id = id;
        this.tieude = tieude;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getTieude() {
        return tieude;
    }

    public String getHinh() {
        return hinh;
    }
}
