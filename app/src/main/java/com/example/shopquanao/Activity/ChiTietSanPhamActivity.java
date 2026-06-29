package com.example.shopquanao.Activity;

import static com.example.shopquanao.SERVER.sanpham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shopquanao.Model.SANPHAM;
import com.example.shopquanao.R;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ActivityChiTietSanPhamBinding;
import com.example.shopquanao.Model.GIOHANG;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    ActivityChiTietSanPhamBinding binding;

    SANPHAM sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChiTietSanPhamBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarChiTiet);

        getData();
        eventAddCart();

        binding.toolbarChiTiet.setNavigationOnClickListener(v -> finish());
    }

    private void getData(){
        sanPham = (SANPHAM) getIntent().getSerializableExtra("sanpham");
        binding.txtTen.setText(sanPham.getTensanpham());
        DecimalFormat format=new DecimalFormat("###,###,###");
        binding.txtGia.setText(format.format(sanPham.getGia())+" đ");
        binding.txtMoTa.setText(sanPham.getMota());
        Glide.with(this).load(sanPham.getHinh()).into(binding.imgSanPham);

    }

    private void eventAddCart(){

        binding.btnThemGioHang.setOnClickListener(v -> {
            boolean exist = false;
                for (GIOHANG item : Utils.gioHangList) {
                    if (item.getSanpham().getId() == sanPham.getId()) {
                        item.setSoluong(item.getSoluong() + 1);
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    Utils.gioHangList.add(new GIOHANG(sanPham,1));
                }
                Toast.makeText(this,"Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_cart) {
            startActivity(new Intent(this, GioHangActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}