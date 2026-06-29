package com.example.shopquanao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopquanao.Model.DANHMUC;
import com.example.shopquanao.databinding.ItemDanhmucBinding;
import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>{

    Context context;
    ArrayList<DANHMUC> list;
    public interface OnItemClick{
        void onClick(DANHMUC danhmuc);
    }
    private OnItemClick listener;

    public DanhMucAdapter(Context context, ArrayList<DANHMUC> list, OnItemClick listener){
        this.context=context;
        this.list=list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        ItemDanhmucBinding binding=ItemDanhmucBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        DANHMUC danhmuc=list.get(position);
        holder.binding.txtDanhMuc.setText(danhmuc.getTendanhmuc());
        Glide.with(context).load(danhmuc.getHinh()).into(holder.binding.imgDanhMuc);

        holder.itemView.setOnClickListener( v ->{
            listener.onClick(danhmuc);
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemDanhmucBinding binding;

        public ViewHolder(ItemDanhmucBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}