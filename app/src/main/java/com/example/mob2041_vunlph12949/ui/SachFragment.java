package com.example.mob2041_vunlph12949.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.SachDAO;
import com.example.mob2041_vunlph12949.utilities.ItemSachClick;
import com.example.mob2041_vunlph12949.adapter.LoaiSachSpinnerAdapeter;
import com.example.mob2041_vunlph12949.adapter.SachAdapter;
import com.example.mob2041_vunlph12949.dao.LoaiSachDAO;
import com.example.mob2041_vunlph12949.model.LoaiSach;
import com.example.mob2041_vunlph12949.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SachFragment extends Fragment {

    SachAdapter adapter;
    RecyclerView rvSach;
    FloatingActionButton fab;
    ArrayList<LoaiSach> list;
    List<Sach> listSach;
    SachDAO daoSach;
    int maLoaiSach;
    LoaiSachDAO dao;
    LoaiSachSpinnerAdapeter spinnerAdapeter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabSach);
        rvSach = view.findViewById(R.id.rvSach);

        dao = new LoaiSachDAO(getActivity());
        dao.open();

        daoSach = new SachDAO(getActivity());
        daoSach.open();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSach.setLayoutManager(layoutManager);

        listSach = new ArrayList<>();
        listSach = daoSach.getAll();

        adapter = new SachAdapter(listSach, getActivity());
        rvSach.setAdapter(adapter);

        adapter.setDeleteClick(new ItemSachClick() {
            @Override
            public void OnItemClik(Sach sach) {
                dialogDelete(sach);
            }
        });
        adapter.setUpdateClick(new ItemSachClick() {
            @Override
            public void OnItemClik(Sach sach) {
                dialogUpdate(sach);
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
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    public void dialogAdd() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_sach, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(view);
        dialog.setIcon(R.drawable.ic_add_new);
        dialog.setTitle("Thêm sách");

        EditText edTenSach = (EditText) view.findViewById(R.id.edTenSach);
        EditText edGiaThue = (EditText) view.findViewById(R.id.edGiaThue);
        Spinner spinner = (Spinner) view.findViewById(R.id.spLoaiSach);
        Button btnSave = (Button) view.findViewById(R.id.btnSaveSach);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelSach);

        list = new ArrayList<>();
        list = (ArrayList<LoaiSach>) dao.getAll();
        spinnerAdapeter = new LoaiSachSpinnerAdapeter(getContext(), list);
        spinner.setAdapter(spinnerAdapeter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = list.get(position).getMaLoai();
                Toast.makeText(getContext(), "Chọn " + list.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maLoaiSach == 0) {
                    Toast.makeText(getContext(), "Bạn chưa thêm loại sách", Toast.LENGTH_SHORT).show();
                } else if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0 || edGiaThue.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int giaThue = Integer.parseInt(edGiaThue.getText().toString());
                        Sach sach = new Sach();
                        sach.setTenSach(edTenSach.getText().toString());
                        sach.setGiaThue(giaThue);
                        sach.setMaLoai(maLoaiSach);
                        long kq = daoSach.insert(sach);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                            listSach.clear();
                            listSach.addAll(daoSach.getAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }

    public void dialogDelete(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_delete);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long kq = daoSach.delete(sach.getMaSach() + "");
                if (kq > 0) {
                    Toast.makeText(getContext(), "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                    listSach.clear();
                    listSach.addAll(daoSach.getAll());
                    adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Xóa sách thát bại", Toast.LENGTH_SHORT).show();
                }
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

    public void dialogUpdate(Sach sach) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_sach, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(view);
        dialog.setIcon(R.drawable.ic_update);
        dialog.setTitle("Cập nhập sách");

        EditText edTenSach = (EditText) view.findViewById(R.id.edTenSach);
        EditText edGiaThue = (EditText) view.findViewById(R.id.edGiaThue);
        Spinner spinner = (Spinner) view.findViewById(R.id.spLoaiSach);
        Button btnSave = (Button) view.findViewById(R.id.btnSaveSach);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelSach);

        list = new ArrayList<>();
        list = (ArrayList<LoaiSach>) dao.getAll();
        spinnerAdapeter = new LoaiSachSpinnerAdapeter(getContext(), list);
        spinner.setAdapter(spinnerAdapeter);


        edTenSach.setText(sach.getTenSach());
        edGiaThue.setText(sach.getGiaThue() + "");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = list.get(position).getMaLoai();
                Toast.makeText(getContext(), "Chọn " + list.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            if (sach.getMaLoai() == (list.get(i).getMaLoai())) {
                position = i;
            }
        }
        spinner.setSelection(position);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0 || edGiaThue.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (sach.getTenSach().equals(edTenSach.getText().toString())
                            && sach.getGiaThue() == Integer.parseInt(edGiaThue.getText().toString())
                            && sach.getMaLoai() == maLoaiSach) {
                        Toast.makeText(getContext(), "Không có thay đổi để cập nhập", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            int giaThue = Integer.parseInt(edGiaThue.getText().toString());
                            sach.setTenSach(edTenSach.getText().toString());
                            sach.setGiaThue(giaThue);
                            sach.setMaLoai(maLoaiSach);
                            int kq = daoSach.update(sach);
                            if (kq > 0) {
                                Toast.makeText(getContext(), "Cập nhập sách thành công", Toast.LENGTH_SHORT).show();
                                listSach.clear();
                                listSach.addAll(daoSach.getAll());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Cập nhập sách thất bại", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
        daoSach.close();
    }
}