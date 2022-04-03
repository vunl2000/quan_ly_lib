package com.example.mob2041_vunlph12949.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.model.Top;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder> {
    List<Top> list;

    public ThongKeAdapter(List<Top> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ThongKeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_thongke, parent, false);
        return new ThongKeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ThongKeViewHolder holder, int position) {
        Top top = list.get(position);
        holder.tvSach.setText("Tên sách: " + top.getTenSach());
        holder.tvSL.setText("Số lượng: " + top.getSoLuong());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ThongKeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSach, tvSL;

        public ThongKeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvSach = itemView.findViewById(R.id.tvTenSachTK);
            tvSL = itemView.findViewById(R.id.tvSoLuongTK);
        }
    }
}
