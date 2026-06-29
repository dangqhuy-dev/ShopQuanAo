package com.example.shopquanao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopquanao.databinding.ItemBannerBinding;
import com.example.shopquanao.Model.BANNER;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder>{

    Context context;
    ArrayList<BANNER> list;

    public BannerAdapter(Context context,ArrayList<BANNER> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
        BANNER banner=list.get(position);
        Glide.with(context).load(banner.getHinh()).into(holder.binding.imgBanner);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ItemBannerBinding binding;

        public MyViewHolder(ItemBannerBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}