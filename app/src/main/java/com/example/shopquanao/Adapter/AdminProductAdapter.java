package com.example.shopquanao.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.shopquanao.Admin.EditProductActivity;
import com.example.shopquanao.Model.SANPHAM;
import com.example.shopquanao.SERVER;
import com.example.shopquanao.databinding.ItemAdminProductBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.MyViewHolder>{

    Context context;
    ArrayList<SANPHAM> list;

    public AdminProductAdapter(Context context, ArrayList<SANPHAM> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        ItemAdminProductBinding binding= ItemAdminProductBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
        SANPHAM sp=list.get(position);
        DecimalFormat format=new DecimalFormat("###,###,###");
        holder.binding.AdminTen.setText(sp.getTensanpham());
        holder.binding.AdminGia.setText(format.format(sp.getGia())+" đ");
        Glide.with(context).load(sp.getHinh()).into(holder.binding.img);
        holder.binding.btnADminSua.setOnClickListener(v->{
            Intent intent= new Intent(context, EditProductActivity.class);
            intent.putExtra("sanpham",sp);
            context.startActivity(intent);
        });

        holder.binding.btnAdminXoa.setOnClickListener(v->{
            new AlertDialog.Builder(context)
                    .setTitle("Xóa")
                    .setMessage("Bạn chắc chắn muốn xóa?")
                    .setPositiveButton("Có", (dialog,which)->{xoaSanPham(sp.getId(),position);})
                    .setNegativeButton("Không",null).show();
        });
    }
    private void xoaSanPham(int id,int position){
        StringRequest request = new StringRequest(Request.Method.POST, SERVER.xoasanpham, response -> {
            list.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
        }, error -> {
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                        params.put("id", String.valueOf(id));
                        return params;
                    }
                };
        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        ItemAdminProductBinding binding;

        public MyViewHolder(ItemAdminProductBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}