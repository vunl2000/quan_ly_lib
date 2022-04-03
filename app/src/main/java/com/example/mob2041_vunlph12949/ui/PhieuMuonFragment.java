package com.example.mob2041_vunlph12949.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.PhieuMuonDAO;

import com.example.mob2041_vunlph12949.utilities.ItemPhieuMuonClick;
import com.example.mob2041_vunlph12949.adapter.PhieuMuonAdapter;
import com.example.mob2041_vunlph12949.adapter.SachSpinnerAdapter;
import com.example.mob2041_vunlph12949.adapter.ThanhVienSpinnerAdapter;
import com.example.mob2041_vunlph12949.dao.SachDAO;
import com.example.mob2041_vunlph12949.dao.ThanhVienDAO;
import com.example.mob2041_vunlph12949.model.PhieuMuon;
import com.example.mob2041_vunlph12949.model.Sach;
import com.example.mob2041_vunlph12949.model.ThanhVien;
import com.example.mob2041_vunlph12949.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PhieuMuonFragment extends Fragment {
    RecyclerView rvPhieuMuon;
    FloatingActionButton fab;
    PhieuMuonAdapter adapter;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    int maTV, maSach, traSach;
    String maTT;
    List<PhieuMuon> listPM;
    PhieuMuonDAO daoPM;
    ThanhVienDAO daoTV;
    ArrayList<ThanhVien> listTV;
    SachDAO daoSach;
    ArrayList<Sach> listSach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabPhieuMuon);
        rvPhieuMuon = view.findViewById(R.id.rvPhieuMuon);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPhieuMuon.setLayoutManager(layoutManager);

        daoTV = new ThanhVienDAO(getActivity());
        daoTV.open();

        daoSach = new SachDAO(getActivity());
        daoSach.open();

        daoPM = new PhieuMuonDAO(getActivity());
        daoPM.open();

        listPM = new ArrayList<>();
        listPM = daoPM.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), listPM);
        rvPhieuMuon.setAdapter(adapter);

        adapter.setDeleteClick(new ItemPhieuMuonClick() {
            @Override
            public void OnItemClick(PhieuMuon phieuMuon) {
                dialogDelete(phieuMuon);
            }
        });
        adapter.setUpdateClick(new ItemPhieuMuonClick() {
            @Override
            public void OnItemClick(PhieuMuon phieuMuon) {
                dialogUpdate(phieuMuon);
            }
        });

        Intent intent = getActivity().getIntent();
        maTT = intent.getStringExtra("user");
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
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    public void dialogAdd() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_phieu_muon, null);

        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(view);
        dialog.setTitle("Thêm phiếu mượn");
        dialog.setIcon(R.drawable.ic_add_new);

        EditText edNgayThue = (EditText) view.findViewById(R.id.edNgayThue);
        EditText edTienThue = (EditText) view.findViewById(R.id.edTienThue);
        ImageView imgDate = (ImageView) view.findViewById(R.id.imgDate);
        Spinner spinnerTV = (Spinner) view.findViewById(R.id.spThanhVien);
        Spinner spinnerSach = (Spinner) view.findViewById(R.id.spTenSach);
        CheckBox chkTraSach = (CheckBox) view.findViewById(R.id.chkTraSach);
        Button btnSave = (Button) view.findViewById(R.id.btnSavePM);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelPM);

        // Spinner thành viên
        listTV = new ArrayList<>();
        listTV = (ArrayList<ThanhVien>) daoTV.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(), listTV);
        spinnerTV.setAdapter(thanhVienSpinnerAdapter);
        spinnerTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = listTV.get(position).getMaTV();
                Toast.makeText(getActivity(), "Thành viên chọn: " + listTV.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner sách
        listSach = new ArrayList<>();
        listSach = (ArrayList<Sach>) daoSach.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(), listSach);
        spinnerSach.setAdapter(sachSpinnerAdapter);
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                Toast.makeText(getActivity(), "Sách chọn: " + listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        edNgayThue.setText(Utilities.dateToString(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
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
                if (chkTraSach.isChecked()) {
                    traSach = 1;
                } else {
                    traSach = 0;
                }
                if (maTV == 0 || maSach == 0) {
                    Toast.makeText(getContext(), "Bạn cần thêm đầy đủ thành viên và sách", Toast.LENGTH_SHORT).show();
                } else if (edNgayThue.getText().length() == 0 || edTienThue.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkDate(edNgayThue) && checkPrice(edTienThue)) {
                        PhieuMuon phieuMuon = new PhieuMuon();

                        phieuMuon.setMaTT(maTT);
                        phieuMuon.setMaTV(maTV);
                        phieuMuon.setMaSach(maSach);
                        phieuMuon.setNgay(edNgayThue.getText().toString());
                        phieuMuon.setTienThue(Integer.parseInt(edTienThue.getText().toString()));
                        phieuMuon.setTraSach(traSach);
                        long kq = daoPM.insert(phieuMuon);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                            listPM.clear();
                            listPM.addAll(daoPM.getAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        dialog.show();
    }

    public void dialogDelete(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_delete);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kq = daoPM.delete(phieuMuon.getMaPM() + "");
                if (kq > 0) {
                    Toast.makeText(getContext(), "Xóa mượn thành công", Toast.LENGTH_SHORT).show();
                    listPM.clear();
                    listPM.addAll(daoPM.getAll());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
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

    public void dialogUpdate(PhieuMuon phieuMuon) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_phieu_muon, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(view);
        dialog.setIcon(R.drawable.ic_update);
        dialog.setTitle("Cập nhập phiếu mượn");

        EditText edNgayThue = (EditText) view.findViewById(R.id.edNgayThue);
        EditText edTienThue = (EditText) view.findViewById(R.id.edTienThue);
        ImageView imgDate = (ImageView) view.findViewById(R.id.imgDate);
        Spinner spinnerTV = (Spinner) view.findViewById(R.id.spThanhVien);
        Spinner spinnerSach = (Spinner) view.findViewById(R.id.spTenSach);
        CheckBox chkTraSach = (CheckBox) view.findViewById(R.id.chkTraSach);
        Button btnSave = (Button) view.findViewById(R.id.btnSavePM);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelPM);

        edNgayThue.setText(phieuMuon.getNgay());
        edTienThue.setText(phieuMuon.getTienThue() + "");

        // Spinner thành viên
        listTV = new ArrayList<>();
        listTV = (ArrayList<ThanhVien>) daoTV.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(), listTV);
        spinnerTV.setAdapter(thanhVienSpinnerAdapter);
        spinnerTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = listTV.get(position).getMaTV();
                Toast.makeText(getActivity(), "Thành viên chọn: " + listTV.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int indexTV = 0;
        for (int i = 0; i < listTV.size(); i++) {
            if (phieuMuon.getMaTV() == listTV.get(i).getMaTV()) {
                indexTV = i;
            }

        }
        spinnerTV.setSelection(indexTV);

        // spinner sách
        listSach = new ArrayList<>();
        listSach = (ArrayList<Sach>) daoSach.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(), listSach);
        spinnerSach.setAdapter(sachSpinnerAdapter);
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                Toast.makeText(getActivity(), "Sách chọn: " + listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int indexSach = 0;
        for (int i = 0; i < listTV.size(); i++) {
            if (phieuMuon.getMaSach() == listSach.get(i).getMaSach()) {
                indexSach = i;
            }

        }
        spinnerSach.setSelection(indexSach);

        if (phieuMuon.getTraSach() == 0) {
            chkTraSach.setChecked(false);
        } else {
            chkTraSach.setChecked(true);
        }

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        edNgayThue.setText(Utilities.dateToString(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
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
                if (chkTraSach.isChecked()) {
                    traSach = 1;
                } else {
                    traSach = 0;
                }
                if (edNgayThue.getText().length() == 0 || edTienThue.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (phieuMuon.getMaTV() == maTV &&
                            phieuMuon.getMaSach() == maSach &&
                            phieuMuon.getNgay() == edNgayThue.getText().toString() &&
                            phieuMuon.getTienThue() == Integer.parseInt(edTienThue.getText().toString())) {
                        Toast.makeText(getContext(), "Không có thay đổi để cập nhập", Toast.LENGTH_SHORT).show();
                    } else if (checkDate(edNgayThue) && checkPrice(edTienThue)) {
                        phieuMuon.setMaTT(maTT);
                        phieuMuon.setMaTV(maTV);
                        phieuMuon.setMaSach(maSach);
                        phieuMuon.setNgay(edNgayThue.getText().toString());
                        phieuMuon.setTienThue(Integer.parseInt(edTienThue.getText().toString()));
                        phieuMuon.setTraSach(traSach);
                        long kq = daoPM.update(phieuMuon);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Cập nhập phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                            listPM.clear();
                            listPM.addAll(daoPM.getAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Cập nhập phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        dialog.show();
    }

    public boolean checkDate(EditText ed) {
        Date date = null;
        String value = ed.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
                Toast.makeText(getContext(), "Sai dịnh dạng ngày!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException ex) {
            Toast.makeText(getContext(), "Sai dịnh dạng ngày!", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
        return date != null;
    }

    public boolean checkPrice(EditText ed) {
        boolean check = true;
        try {
            Integer.parseInt(ed.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), "Tiền thuê phải là số", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoPM.close();
        daoSach.close();
        daoTV.close();
    }
}