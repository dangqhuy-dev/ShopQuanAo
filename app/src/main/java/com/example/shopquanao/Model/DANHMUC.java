package com.example.shopquanao.Model;

public class DANHMUC {
    private int id;
    private String tendanhmuc;
    private String hinhdanhmuc;

    public DANHMUC() {
    }

    public DANHMUC(int id, String tendanhmuc, String hinhdanhmuc) {
        this.id = id;
        this.tendanhmuc = tendanhmuc;
        this.hinhdanhmuc = hinhdanhmuc;
    }

    public int getId() {
        return id;
    }

    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public String getHinh() {
        return hinhdanhmuc;
    }
}
