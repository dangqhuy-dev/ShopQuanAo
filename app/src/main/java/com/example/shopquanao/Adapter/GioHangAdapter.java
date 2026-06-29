package com.example.shopquanao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopquanao.Model.GIOHANG;
import com.example.shopquanao.databinding.ItemGiohangBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {

    Context context;
    ArrayList<GIOHANG> list;
    public interface OnCartChangeListener{
        void onCartChanged();
    }

    private OnCartChangeListener listener;

    public GioHangAdapter(Context context, ArrayList<GIOHANG> list, OnCartChangeListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGiohangBinding binding = ItemGiohangBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GIOHANG item = list.get(position);
        DecimalFormat format = new DecimalFormat("###,###,###");
        holder.binding.txtTen.setText(item.getSanpham().getTensanpham());
        holder.binding.txtGia.setText(format.format(item.getSanpham().getGia()) + " đ");
        holder.binding.txtSoLuong.setText(String.valueOf(item.getSoluong()));
        holder.binding.txtThanhTien.setText("Thành tiền: " + format.format(item.getTongTien()) + " đ");
        Glide.with(context).load(item.getSanpham().getHinh()).into(holder.binding.imgSanPham);
        holder.binding.btnCong.setOnClickListener(v -> {
            item.setSoluong(item.getSoluong() + 1);
            notifyItemChanged(position);
            listener.onCartChanged();
        });
        holder.binding.btnTru.setOnClickListener(v -> {
            if (item.getSoluong() > 1) {
                item.setSoluong(item.getSoluong() - 1);
                notifyItemChanged(position);
                listener.onCartChanged();
            }
        });
        holder.binding.btnXoa.setOnClickListener(v -> {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
            listener.onCartChanged();
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemGiohangBinding binding;
        public MyViewHolder(ItemGiohangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
