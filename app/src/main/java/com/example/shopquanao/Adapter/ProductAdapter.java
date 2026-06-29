package com.example.shopquanao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopquanao.Activity.ChiTietSanPhamActivity;
import com.example.shopquanao.Model.GIOHANG;
import com.example.shopquanao.Utils;
import com.example.shopquanao.databinding.ItemProductBinding;
import com.example.shopquanao.Model.SANPHAM;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    private ArrayList<SANPHAM> list;
    private ArrayList<SANPHAM> listFull;

    public ProductAdapter(Context context, ArrayList<SANPHAM> list) {
        this.context = context;
        this.list = list;
        this.listFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SANPHAM sp = list.get(position);
        holder.binding.txtTen.setText(sp.getTensanpham());
        DecimalFormat format = new DecimalFormat("###,###,###");

        holder.binding.txtGia.setText(format.format(sp.getGia()) + " đ");
        Glide.with(context).load(sp.getHinh()).into(holder.binding.imgSanPham);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
            intent.putExtra("sanpham", sp);
            context.startActivity(intent);
        });

        holder.binding.btnThem.setOnClickListener(v -> {
            boolean tonTai = false;
            for (GIOHANG item : Utils.gioHangList) {
                if (item.getSanpham().getId() == sp.getId()) {
                    item.setSoluong(item.getSoluong() + 1);
                    tonTai = true;
                    break;
                }
            }
            if (!tonTai) {
                Utils.gioHangList.add(new GIOHANG(sp,1));
            }
            Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemProductBinding binding;
        public MyViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void filter(String keyword) {
        list.clear();
        if(keyword.trim().isEmpty()){
            list.addAll(listFull);
        }else{
            keyword = keyword.toLowerCase();
            for(SANPHAM sp : listFull){
                if(sp.getTensanpham().toLowerCase().contains(keyword)){
                    list.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void refreshFullList() {
        listFull.clear();
        listFull.addAll(list);
    }
}