package com.example.mob2041_vunlph12949.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.LoaiSachDAO;
import com.example.mob2041_vunlph12949.model.LoaiSach;
import com.example.mob2041_vunlph12949.model.Sach;
import com.example.mob2041_vunlph12949.utilities.ItemSachClick;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    List<Sach> list;
    Context context;
    ItemSachClick updateClick;
    ItemSachClick deleteClick;

    public SachAdapter(List<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setUpdateClick(ItemSachClick updateClick) {
        this.updateClick = updateClick;
    }

    public void setDeleteClick(ItemSachClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    @NonNull
    @NotNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SachViewHolder holder, int position) {
        final Sach sach = list.get(position);
        if (sach == null) {
            return;
        }
        LoaiSachDAO dao = new LoaiSachDAO(context);
        dao.open();
        LoaiSach loaiSach = dao.getId(sach.getMaLoai() + " ");
        dao.close();
        holder.tvMaSach.setText("Mã sách: " + sach.getMaSach());
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tien_fm = formatter.format(sach.getGiaThue());
        holder.tvGiaThue.setText("Giá thuê: " + tien_fm +" vnđ");

        holder.tvMaLoai.setText("Mã loại: " + loaiSach.getTenLoai());
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClick.OnItemClik(sach);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.OnItemClik(sach);
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

    public class SachViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaSach, tvTenSach, tvGiaThue, tvMaLoai;
        ImageView imgUpdate, imgDelete;

        public SachViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvMaLoai = itemView.findViewById(R.id.tvMaLoaiSach);
            imgDelete = itemView.findViewById(R.id.imgDeleteSach);
            imgUpdate = itemView.findViewById(R.id.imgUpdateSach);
        }
    }
}
