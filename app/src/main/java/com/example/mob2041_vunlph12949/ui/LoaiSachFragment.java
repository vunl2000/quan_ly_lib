package com.example.mob2041_vunlph12949.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.LoaiSachDAO;
import com.example.mob2041_vunlph12949.utilities.ItemLoaiSachClick;
import com.example.mob2041_vunlph12949.adapter.LoaiSachAdapter;
import com.example.mob2041_vunlph12949.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachFragment extends Fragment {

    RecyclerView rvLoaiSach;
    FloatingActionButton fab;
    LoaiSachAdapter adapter;
    List<LoaiSach> listLS;
    LoaiSachDAO daoLS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabLoaiSach);
        rvLoaiSach = view.findViewById(R.id.rvLoaiSach);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvLoaiSach.setLayoutManager(layoutManager);

        daoLS = new LoaiSachDAO(getActivity());
        daoLS.open();

        listLS = new ArrayList<>();
        listLS = daoLS.getAll();

        adapter = new LoaiSachAdapter(listLS);
        rvLoaiSach.setAdapter(adapter);
        adapter.setUpdateClick(new ItemLoaiSachClick() {
            @Override
            public void OnItemClick(LoaiSach loaiSach) {
                dialogUpdate(loaiSach);
            }
        });
        adapter.setDeleteClick(new ItemLoaiSachClick() {
            @Override
            public void OnItemClick(LoaiSach loaiSach) {
                dialogDelete(loaiSach);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    public void dialogAdd() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_loai_sach, null);
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(view);
        builder.setTitle("Thêm loại sách");

        builder.setIcon(R.drawable.ic_add_new);
        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
        Button btnSave = view.findViewById(R.id.btnSaveLS);
        Button btnCancel = view.findViewById(R.id.btnCancelLS);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenLoai.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    LoaiSach loaiSach = new LoaiSach();
                    loaiSach.setTenLoai(edTenLoai.getText().toString());
                    long kq = daoLS.insert(loaiSach);
                    if (kq > 0) {
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        listLS.clear();
                        listLS.addAll(daoLS.getAll());
                        adapter.notifyDataSetChanged();
                        builder.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }

    public void dialogDelete(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setIcon(R.drawable.ic_delete);
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kq = daoLS.delete(loaiSach.getMaLoai() + "");
                if (kq > 0) {
                    Toast.makeText(getContext(), "Xóa loại sách thành công", Toast.LENGTH_SHORT).show();
                    listLS.clear();
                    listLS.addAll(daoLS.getAll());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Xóa loại sách thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void dialogUpdate(LoaiSach loaiSach) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_loai_sach, null);
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(view);
        builder.setTitle("Cập nhập loại sách");
        builder.setIcon(R.drawable.ic_update);

        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
        Button btnSave = view.findViewById(R.id.btnSaveLS);
        Button btnCancel = view.findViewById(R.id.btnCancelLS);
        edTenLoai.setText(loaiSach.getTenLoai());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenLoai.getText().length() == 0) {

                    Toast.makeText(getContext(), "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else if (loaiSach.getTenLoai().equals(edTenLoai.getText().toString())) {

                    Toast.makeText(getContext(), "Không có thay đổi để cập nhập", Toast.LENGTH_SHORT).show();

                } else {
                    loaiSach.setTenLoai(edTenLoai.getText().toString());
                    int kq = daoLS.update(loaiSach);
                    if (kq > 0) {
                        Toast.makeText(getContext(), "Cập nhập loại sách thành công", Toast.LENGTH_SHORT).show();
                        listLS.clear();
                        listLS.addAll(daoLS.getAll());
                        adapter.notifyDataSetChanged();
                        builder.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Cập nhập sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }
}