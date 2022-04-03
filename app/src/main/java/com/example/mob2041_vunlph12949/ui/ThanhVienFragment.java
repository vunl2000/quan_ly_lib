package com.example.mob2041_vunlph12949.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.ThanhVienDAO;
import com.example.mob2041_vunlph12949.utilities.ItemThanhVienClick;
import com.example.mob2041_vunlph12949.adapter.ThanhVienAdapter;
import com.example.mob2041_vunlph12949.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ThanhVienFragment extends Fragment {
    RecyclerView rvThanhVien;
    FloatingActionButton fab;
    ThanhVienAdapter adapter;
    List<ThanhVien> listTV;
    ThanhVienDAO daoTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabThanhVien);
        rvThanhVien = view.findViewById(R.id.rvThanhVien);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvThanhVien.setLayoutManager(layoutManager);

        daoTV = new ThanhVienDAO(getActivity());
        daoTV.open();
        listTV = new ArrayList<>();
        listTV = daoTV.getAll();

        adapter = new ThanhVienAdapter(listTV);
        rvThanhVien.setAdapter(adapter);
        adapter.setDeleteClick(new ItemThanhVienClick() {
            @Override
            public void OnItemClick(ThanhVien thanhVien) {
                dialogDelete(thanhVien);
            }
        });
        adapter.setUpdateClick(new ItemThanhVienClick() {
            @Override
            public void OnItemClick(ThanhVien thanhVien) {
                dialogUpdate(thanhVien);
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
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    public void dialogAdd() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_thanh_vien, null);
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(view);
        builder.setTitle("Thêm thành viên");
        builder.setIcon(R.drawable.ic_add_new);

        EditText edHoten = (EditText) view.findViewById(R.id.edHoTenTV);
        EditText edNamSinh = (EditText) view.findViewById(R.id.edNamSinhTV);
        Button btnSave = (Button) view.findViewById(R.id.btnSaveTV);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelTV);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edHoten.getText().length() == 0 || edNamSinh.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkYear(edNamSinh)) {
                        ThanhVien thanhVien = new ThanhVien();
                        thanhVien.setHoTen(edHoten.getText().toString());
                        thanhVien.setNamSinh(edNamSinh.getText().toString());
                        long kq = daoTV.insert(thanhVien);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show();
                            listTV.clear();
                            listTV.addAll(daoTV.getAll());
                            adapter.notifyDataSetChanged();
                            builder.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    public void dialogDelete(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setIcon(R.drawable.ic_delete);
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long kq = daoTV.delete(thanhVien.getMaTV() + "");
                if (kq > 0) {
                    Toast.makeText(getContext(), "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                    listTV.clear();
                    listTV.addAll(daoTV.getAll());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Xóa thành viên thất bại", Toast.LENGTH_SHORT).show();
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

    public void dialogUpdate(ThanhVien thanhVien) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_thanh_vien, null);
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(view);
        builder.setTitle("Cập nhập thành viên");
        builder.setIcon(R.drawable.ic_update);

        EditText edHoten = (EditText) view.findViewById(R.id.edHoTenTV);
        EditText edNamSinh = (EditText) view.findViewById(R.id.edNamSinhTV);
        Button btnSave = (Button) view.findViewById(R.id.btnSaveTV);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelTV);

        edHoten.setText(thanhVien.getHoTen());
        edNamSinh.setText(thanhVien.getNamSinh());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edHoten.getText().length() == 0 || edNamSinh.getText().length() == 0) {

                    Toast.makeText(getContext(), "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else if (thanhVien.getHoTen().equals(edHoten.getText().toString()) &&
                        thanhVien.getNamSinh().equals(edNamSinh.getText().toString())) {

                    Toast.makeText(getContext(), "Không có thay đổi để cập nhập", Toast.LENGTH_SHORT).show();

                } else {
                    if (checkYear(edNamSinh)) {
                        thanhVien.setHoTen(edHoten.getText().toString());
                        thanhVien.setNamSinh(edNamSinh.getText().toString());
                        int kq = daoTV.update(thanhVien);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Cập nhập thành viên thành công", Toast.LENGTH_SHORT).show();
                            listTV.clear();
                            listTV.addAll(daoTV.getAll());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Cập nhập thành viên thất bại", Toast.LENGTH_SHORT).show();
                        }
                        builder.dismiss();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    public boolean checkYear(EditText ed) {
        try {
            Integer.parseInt(ed.getText().toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(getContext(), "Năm sinh phải là số!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoTV.close();
    }
}