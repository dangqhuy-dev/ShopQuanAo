package com.example.shopquanao.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopquanao.Admin.AdminActivity;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        eventClick();
    }

    private void eventClick(){

        binding.layoutThongTin.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        binding.layoutLichSu.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        binding.layoutDoiMatKhau.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        binding.layoutADMIN.setOnClickListener(v ->{
            startActivity(new Intent(this, AdminActivity.class));
        });
        binding.layoutGioiThieu.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        binding.btnDangXuat.setOnClickListener(v -> {
            Utils.gioHangList.clear();
            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

}