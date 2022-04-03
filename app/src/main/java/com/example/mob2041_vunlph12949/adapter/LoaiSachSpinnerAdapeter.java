package com.example.mob2041_vunlph12949.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.model.LoaiSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapeter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> list;
    TextView tvTenLoai;

    public LoaiSachSpinnerAdapeter(@NonNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_custom, null);
        }
        final LoaiSach item = list.get(position);
        if (item != null) {
            tvTenLoai = view.findViewById(R.id.tvShow);
            tvTenLoai.setText(item.getTenLoai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_custom, null);
        }
        final LoaiSach item = list.get(position);
        if (item != null) {
            tvTenLoai = view.findViewById(R.id.tvShow);
            tvTenLoai.setText(item.getTenLoai());
        }
        return view;
    }

}
