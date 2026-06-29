package com.example.shopquanao.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopquanao.Adapter.GioHangAdapter;
import com.example.shopquanao.Model.GIOHANG;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ActivityGioHangBinding;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ActivityGioHangBinding binding;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        tinhTongTien();
        binding.toolbarCart.setNavigationOnClickListener(v -> finish());
        binding.btnThanhToan.setOnClickListener(v -> {
            startActivity(new Intent(
                    GioHangActivity.this,
                    ThanhToanActivity.class
            ));
        });

        tinhTongTien();
    }
    private void initView() {
        gioHangAdapter = new GioHangAdapter(this, Utils.gioHangList, this::tinhTongTien);
        binding.rvGioHang.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGioHang.setAdapter(gioHangAdapter);
    }
    private void tinhTongTien() {

        double tong = 0;

        for (GIOHANG item : Utils.gioHangList) {
            tong += item.getTongTien();
        }

        DecimalFormat format = new DecimalFormat("###,###,###");
        binding.txtTongTien.setText(format.format(tong) + " đ");
    }
    @Override
    protected void onResume() {
        super.onResume();
        gioHangAdapter.notifyDataSetChanged();
        tinhTongTien();
    }
}