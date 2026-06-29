package com.example.shopquanao.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopquanao.Adapter.AdminProductAdapter;
import com.example.shopquanao.Model.SANPHAM;
import com.example.shopquanao.SERVER;
import com.example.shopquanao.databinding.ActivityAdminBinding;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    ArrayList<SANPHAM> list;
    AdminProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        loadData();
        binding.fabThem.setOnClickListener(v -> {
            startActivity(new Intent(this,EditProductActivity.class));
        });
        binding.toolbarAdmin.setNavigationOnClickListener(v -> finish());
    }

    private void initView(){
        list=new ArrayList<>();
        adapter= new AdminProductAdapter(this, list);
        binding.rvAdmin.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAdmin.setAdapter(adapter);
    }

    private void loadData(){
        JsonArrayRequest request= new JsonArrayRequest(
            Request.Method.GET,
            SERVER.sanpham,
            null,
            response -> {
                try{
                    list.clear();
                    for(int i=0; i<response.length(); i++){
                        JSONObject object= response.getJSONObject(i);
                        list.add(new SANPHAM(object.getInt("id"), object.getString("tensanpham"), object.getDouble("gia"), object.getString("mota"), object.getString("hinh"), object.getInt("soluong"), object.getInt("danhmucid")));
                    }
                    adapter.notifyDataSetChanged();
                }catch(Exception e){
                    e.printStackTrace();
                }
            },
            error -> error.printStackTrace());
        Volley.newRequestQueue(this).add(request);
    }
}