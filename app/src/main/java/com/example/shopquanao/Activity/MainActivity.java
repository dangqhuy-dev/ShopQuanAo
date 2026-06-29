package com.example.shopquanao.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.shopquanao.Adapter.BannerAdapter;
import com.example.shopquanao.Adapter.DanhMucAdapter;
import com.example.shopquanao.Adapter.ProductAdapter;
import com.example.shopquanao.Model.BANNER;
import com.example.shopquanao.Model.DANHMUC;
import com.example.shopquanao.Model.GIOHANG;
import com.example.shopquanao.Model.SANPHAM;
import com.example.shopquanao.R;
import com.example.shopquanao.SERVER;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ActivityMainBinding;
import com.example.shopquanao.databinding.ItemProductBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProductAdapter productAdapter;

    ArrayList<SANPHAM> danhsachsp;
    private ArrayList<SANPHAM> tatCaSanPham;
    private ArrayList<BANNER> bannerList;
    private BannerAdapter bannerAdapter;
    private ArrayList<DANHMUC> danhmucList;
    private DanhMucAdapter danhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setAdapterSP();
        loadBanner();
        loadDanhMuc();
        loadSanPham();
        autoSlideBanner();
        eventClick();
        eventMenu();
    }


    private void loadDanhMuc() {

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SERVER.danhmuc,
                null,
                response -> {
                    try {
                        danhmucList.clear();
                        danhmucList.add(new DANHMUC(
                                0,
                                "Tất cả",
                                SERVER.localhost + "hinhanh/danhmuc/all.jpg"
                        ));
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            DANHMUC danhmuc = new DANHMUC(
                                    object.getInt("id"),
                                    object.getString("tendanhmuc"),
                                    object.getString("hinhdanhmuc")
                            );
                            danhmucList.add(danhmuc);
                        }
                        danhMucAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );
        Volley.newRequestQueue(this).add(request);
    }


    private void setAdapterSP() {
        danhsachsp = new ArrayList<>();
        tatCaSanPham = new ArrayList<>();
        bannerList = new ArrayList<>();
        danhmucList = new ArrayList<>();

        productAdapter = new ProductAdapter(this, danhsachsp);
        bannerAdapter = new BannerAdapter(this, bannerList);
        danhMucAdapter = new DanhMucAdapter(this, danhmucList, danhmuc -> {
            danhsachsp.clear();
                if (danhmuc.getId() == 0){
                    danhsachsp.addAll(tatCaSanPham);
                }else {
                    for (SANPHAM sp:tatCaSanPham){
                        if(sp.getDanhmucid()==danhmuc.getId()){
                            danhsachsp.add(sp);
                        }
                    }
                }
            productAdapter.notifyDataSetChanged();
        });

        binding.rvSanPham.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvSanPham.setAdapter(productAdapter);
        binding.rvSanPham.setNestedScrollingEnabled(false);

        binding.rvDanhMuc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvDanhMuc.setAdapter(danhMucAdapter);
//        binding.rvDanhMuc.setNestedScrollingEnabled(false);

        binding.viewPagerBanner.setAdapter(bannerAdapter);
    }
    private void loadSanPham() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SERVER.sanpham,
                null,
                response -> {
                    danhsachsp.clear();
                    tatCaSanPham.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            SANPHAM sp = new SANPHAM(
                                    object.getInt("id"),
                                    object.getString("tensanpham"),
                                    object.getDouble("gia"),
                                    object.getString("mota"),
                                    object.getString("hinh"),
                                    object.getInt("soluong"),
                                    object.getInt("danhmucid")
                            );
                            danhsachsp.add(sp);
                            tatCaSanPham.add(sp);
                        }
                        productAdapter.refreshFullList();
                        productAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void loadBanner() {
        StringRequest request=new StringRequest(Request.Method.GET,SERVER.banner,response -> {
            try{
                JSONArray array=new JSONArray(response);
                bannerList.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    bannerList.add(new BANNER(
                            object.getInt("id"),
                            object.getString("tieude"),
                            object.getString("hinh")
                    ));
                }
                bannerAdapter.notifyDataSetChanged();
            }catch(Exception e){
                e.printStackTrace();
            }
        },error -> error.printStackTrace());
        Volley.newRequestQueue(this).add(request);
    }

    private void eventClick() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home){
                return true;
            }else if (id == R.id.nav_cart){
                startActivity(new Intent(MainActivity.this,GioHangActivity.class));
                return true;
            }else if (id == R.id.nav_setting){
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return true;
            }
            return false;
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.filter(newText);
                return true;
            }

        });

        binding.toolbarMain.setNavigationOnClickListener(v -> {
            binding.drawerLayout.openDrawer(binding.navigationView);
        });
    }
    private void eventMenu() {
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.menu_home){
                loadTatCaSanPham();
            }else if(id == R.id.menu_aonam){
                locDanhMuc(1);
            }else if(id == R.id.menu_aonu){
                locDanhMuc(2);
            }else if(id == R.id.menu_quan){
                locDanhMuc(3);
            }else if(id == R.id.menu_phukien){
                locDanhMuc(4);
            }else if(id == R.id.menu_logout){
                dangXuat();
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }
    private void autoSlideBanner(){
        Handler handler=new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run(){
                if(bannerList.size()>0){
                    int current=binding.viewPagerBanner.getCurrentItem();
                    if(current==bannerList.size()-1){
                        binding.viewPagerBanner.setCurrentItem(0);
                    }else{
                        binding.viewPagerBanner.setCurrentItem(current+1);
                    }
                }
                handler.postDelayed(this,3000);
            }
        };
        handler.postDelayed(runnable,3000);
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
    private void loadTatCaSanPham(){
        danhsachsp.clear();
        danhsachsp.addAll(tatCaSanPham);
        productAdapter.refreshFullList();
        productAdapter.notifyDataSetChanged();
    }
    private void locDanhMuc(int idDanhMuc){
        danhsachsp.clear();
        for(SANPHAM sp : tatCaSanPham){
            if(sp.getDanhmucid() == idDanhMuc){
                danhsachsp.add(sp);
            }
        }
        productAdapter.refreshFullList();
        productAdapter.notifyDataSetChanged();
    }
    private void dangXuat(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }
}