package com.example.shopquanao.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopquanao.Model.GIOHANG;
import com.example.shopquanao.SERVER;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ActivityThanhToanBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    ActivityThanhToanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhToanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        tinhTongTien();
        binding.btnDatHang.setOnClickListener(v -> {
            datHang();
        });

    }


    private void tinhTongTien() {
        double tong = 0;
        for(GIOHANG item : Utils.gioHangList){
            tong += item.getTongTien();
        }
        DecimalFormat format = new DecimalFormat("###,###,###");
        binding.txtTongTien.setText("Tổng tiền: " + format.format(tong) + " đ");
    }
    private void datHang() {
        String hoTen = binding.edtHoTen.getText().toString().trim();
        String sdt = binding.edtSDT.getText().toString().trim();
        String diaChi = binding.edtDiaChi.getText().toString().trim();
        String ghiChu = binding.edtGhiChu.getText().toString().trim();
        if (hoTen.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONArray jsonArray = new JSONArray();
        StringRequest request = new StringRequest(Request.Method.POST, SERVER.dathang, response -> {
                    if (response.trim().equals("1")) {
                        Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        Utils.gioHangList.clear();
                        finish();
                    } else {
                        Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        ) {

            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                double tong = 0;
                for (GIOHANG item : Utils.gioHangList) {
                    tong += item.getTongTien();
                }
                params.put("hoten", hoTen);
                params.put("sdt", sdt);
                params.put("diachi", diaChi);
                params.put("tongtien", String.valueOf(tong));
                params.put("chitiet", jsonArray.toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
        try {
            for (GIOHANG item : Utils.gioHangList) {
                JSONObject object = new JSONObject();
                object.put("sanphamid", item.getSanpham().getId());
                object.put("soluong", item.getSoluong());
                object.put("gia", item.getSanpham().getGia());
                jsonArray.put(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}