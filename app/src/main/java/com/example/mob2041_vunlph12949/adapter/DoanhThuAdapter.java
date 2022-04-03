package com.example.mob2041_vunlph12949.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.model.PhieuMuon;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class DoanhThuAdapter extends RecyclerView.Adapter<DoanhThuAdapter.DoanhThuViewHolder> {
    List<PhieuMuon> list;

    public DoanhThuAdapter(List<PhieuMuon> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public DoanhThuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_item_doanhthu, parent, false);
        return new DoanhThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DoanhThuViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        if (phieuMuon != null) {
            holder.tvNgay.setText("Ngày: " + phieuMuon.getNgay());
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String tien_fm = formatter.format(phieuMuon.getTienThue());
            holder.tvTien.setText("Số tiền: " + tien_fm + " vnđ");
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class DoanhThuViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgay, tvTien;

        public DoanhThuViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNgay = itemView.findViewById(R.id.tvNgayDT);
            tvTien = itemView.findViewById(R.id.tvTienDT);
        }
    }
}
