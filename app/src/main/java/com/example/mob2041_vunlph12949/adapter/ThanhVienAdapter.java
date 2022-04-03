package com.example.mob2041_vunlph12949.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.model.ThanhVien;
import com.example.mob2041_vunlph12949.utilities.ItemThanhVienClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {

    List<ThanhVien> list;
    ItemThanhVienClick updateClick;
    ItemThanhVienClick deleteClick;

    public ThanhVienAdapter(List<ThanhVien> list) {
        this.list = list;
    }

    public void setUpdateClick(ItemThanhVienClick updateClick) {
        this.updateClick = updateClick;
    }

    public void setDeleteClick(ItemThanhVienClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    @NonNull
    @NotNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien == null) {
            return;
        }

        holder.tvMaTV.setText("Mã thành viên: " + thanhVien.getMaTV());
        holder.tvHoTen.setText("Tên thành viên: " + thanhVien.getHoTen());
        holder.tvNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.OnItemClick(thanhVien);
            }
        });
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClick.OnItemClick(thanhVien);
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

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTV, tvHoTen, tvNamSinh;
        ImageView imgUpdate, imgDelete;

        public ThanhVienViewHolder(@NonNull @NotNull View view) {
            super(view);
            tvMaTV = view.findViewById(R.id.tvMaTV);
            tvHoTen = view.findViewById(R.id.tvHoTen);
            tvNamSinh = view.findViewById(R.id.tvNamSinh);
            imgUpdate = view.findViewById(R.id.imgUpdateTV);
            imgDelete = view.findViewById(R.id.imgDeleteTV);
        }
    }

    public interface ItemClick {
        public void OnItemClick(ThanhVien thanhVien);
    }
}
