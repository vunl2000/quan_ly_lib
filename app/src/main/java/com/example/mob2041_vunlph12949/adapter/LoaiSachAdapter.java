package com.example.mob2041_vunlph12949.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.model.LoaiSach;
import com.example.mob2041_vunlph12949.utilities.ItemLoaiSachClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    List<LoaiSach> list;
    ItemLoaiSachClick updateClick;
    ItemLoaiSachClick deleteClick;

    public LoaiSachAdapter(List<LoaiSach> list) {
        this.list = list;
    }

    public void setUpdateClick(ItemLoaiSachClick updateClick) {
        this.updateClick = updateClick;
    }

    public void setDeleteClick(ItemLoaiSachClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    @NonNull
    @NotNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_loaisach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        if (loaiSach == null) {
            return;
        }
        holder.tvMaLoai.setText("Mã loại sách: " + loaiSach.getMaLoai());
        holder.tvTenLoai.setText("Tên loại sách: " + loaiSach.getTenLoai());
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClick.OnItemClick(loaiSach);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.OnItemClick(loaiSach);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaLoai, tvTenLoai;
        ImageView imgDelete, imgUpdate;

        public LoaiSachViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMaLoai = itemView.findViewById(R.id.tvMaloai);
            tvTenLoai = itemView.findViewById(R.id.tvShow);
            imgUpdate = itemView.findViewById(R.id.imgUpdateLS);
            imgDelete = itemView.findViewById(R.id.imgDeleteLS);
        }
    }
}
