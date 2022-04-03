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
import com.example.mob2041_vunlph12949.model.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    Context context;
    ArrayList<Sach> list;
    TextView tvTenSach;

    public SachSpinnerAdapter(@NonNull Context context, ArrayList<Sach> list) {
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
        if (list == null){

        }
        Sach sach = list.get(position);
        if (sach != null) {
            tvTenSach = view.findViewById(R.id.tvShow);
            tvTenSach.setText(sach.getTenSach());
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
        Sach sach = list.get(position);
        if (sach != null) {
            tvTenSach = view.findViewById(R.id.tvShow);
            tvTenSach.setText(sach.getTenSach());
        }

        return view;
    }
}
