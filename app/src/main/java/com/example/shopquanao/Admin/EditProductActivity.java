package com.example.shopquanao.Admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopquanao.Model.SANPHAM;
import com.example.shopquanao.SERVER;
import com.example.shopquanao.databinding.ActivityEditProductBinding;

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    ActivityEditProductBinding binding;

    SANPHAM sanPham;

    boolean isEdit = false;

    String[] danhMuc = {"Áo Nam", "Áo Nữ", "Quần", "Phụ kiện"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        getData();
        eventClick();
    }

    private void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMuc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinDanhMuc.setAdapter(adapter);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void getData() {
        sanPham = (SANPHAM) getIntent().getSerializableExtra("sanpham");

        if (sanPham != null) {
            isEdit = true;
            binding.edtTen.setText(sanPham.getTensanpham());
            binding.edtGia.setText(String.valueOf(sanPham.getGia()));
            binding.edtMoTa.setText(sanPham.getMota());
            binding.edtHinh.setText(sanPham.getHinh());
            binding.edtSoLuong.setText(String.valueOf(sanPham.getSoluong()));
            binding.spinDanhMuc.setSelection(sanPham.getDanhmucid() - 1);
        }
    }

    private void eventClick() {
        binding.btnLuu.setOnClickListener(v -> {
            if (isEdit) {
                suaSanPham();
            } else {
                themSanPham();
            }
        });
    }
    private void themSanPham(){
        StringRequest request = new StringRequest(Request.Method.POST, SERVER.themsanpham, response -> {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            finish();
            },
        error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show())
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<>();
                        params.put("tensanpham", binding.edtTen.getText().toString());
                        params.put("gia", binding.edtGia.getText().toString());
                        params.put("mota", binding.edtMoTa.getText().toString());
                        params.put("hinh", binding.edtHinh.getText().toString());
                        params.put("soluong", binding.edtSoLuong.getText().toString());
                        params.put("danhmucid", String.valueOf(binding.spinDanhMuc.getSelectedItemPosition()+1));
                        return params;
                    }
                };
        Volley.newRequestQueue(this).add(request);
    }
    private void suaSanPham(){
        StringRequest request = new StringRequest(Request.Method.POST, SERVER.suasanpham, response -> {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            finish();
            },
            error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show())
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<>();
                        params.put("id", String.valueOf(sanPham.getId()));
                        params.put("tensanpham", binding.edtTen.getText().toString());
                        params.put("gia", binding.edtGia.getText().toString());
                        params.put("mota", binding.edtMoTa.getText().toString());
                        params.put("hinh", binding.edtHinh.getText().toString());
                        params.put("soluong", binding.edtSoLuong.getText().toString());
                        params.put("danhmucid", String.valueOf(binding.spinDanhMuc.getSelectedItemPosition()+1));
                        return params;
                    }
                };
        Volley.newRequestQueue(this).add(request);
    }
}