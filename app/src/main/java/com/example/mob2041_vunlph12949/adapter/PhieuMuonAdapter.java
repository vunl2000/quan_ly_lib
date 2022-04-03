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
import com.example.mob2041_vunlph12949.dao.SachDAO;
import com.example.mob2041_vunlph12949.dao.ThanhVienDAO;
import com.example.mob2041_vunlph12949.model.PhieuMuon;
import com.example.mob2041_vunlph12949.model.Sach;
import com.example.mob2041_vunlph12949.model.ThanhVien;
import com.example.mob2041_vunlph12949.utilities.ItemPhieuMuonClick;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    Context context;
    List<PhieuMuon> list;
    ItemPhieuMuonClick updateClick;
    ItemPhieuMuonClick deleteClick;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_phieumuon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    public void setUpdateClick(ItemPhieuMuonClick updateClick) {
        this.updateClick = updateClick;
    }

    public void setDeleteClick(ItemPhieuMuonClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        if (phieuMuon == null) {
            return;
        }

        holder.tvMaPM.setText("Mã phiếu mượn: " + phieuMuon.getMaPM());

        ThanhVienDAO daoTV = new ThanhVienDAO(context);
        daoTV.open();
        ThanhVien thanhVien = daoTV.getId(phieuMuon.getMaTV() + "");
        daoTV.close();
        holder.tvThanhVien.setText("Thành viên: " + thanhVien.getHoTen());

        SachDAO daoSach = new SachDAO(context);
        daoSach.open();
        Sach sach = daoSach.getId(phieuMuon.getMaSach() + "");
        daoSach.close();
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tien_fm = formatter.format(sach.getGiaThue());
        holder.tvGiaThue.setText("Tiền thuê: " + tien_fm +" vnđ");

        holder.tvNgayThue.setText("Ngày thuê: " + phieuMuon.getNgay());

        if (phieuMuon.getTraSach() == 1) {
            holder.tvTraSach.setText("Đã chả sách");
            holder.tvTraSach.setTextColor(Color.BLUE);
        } else {
            holder.tvTraSach.setText("Chưa chả sách");
            holder.tvTraSach.setTextColor(Color.RED);
        }
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClick.OnItemClick(phieuMuon);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.OnItemClick(phieuMuon);
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

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPM, tvThanhVien, tvTenSach, tvNgayThue, tvGiaThue, tvTraSach;
        ImageView imgUpdate, imgDelete;

        public PhieuMuonViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvThanhVien = itemView.findViewById(R.id.tvThanhVienPM);
            tvTenSach = itemView.findViewById(R.id.tvTenSachPM);
            tvNgayThue = itemView.findViewById(R.id.tvNgayThuePM);
            tvGiaThue = itemView.findViewById(R.id.tvTienThuePM);
            tvTraSach = itemView.findViewById(R.id.tvTraSachPM);
            imgDelete = itemView.findViewById(R.id.imgDeletePM);
            imgUpdate = itemView.findViewById(R.id.imgUpdatePM);
        }
    }
}
