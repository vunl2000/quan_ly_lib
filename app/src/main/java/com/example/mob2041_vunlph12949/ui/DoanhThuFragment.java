package com.example.mob2041_vunlph12949.ui;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.adapter.DoanhThuAdapter;
import com.example.mob2041_vunlph12949.dao.PhieuMuonDAO;
import com.example.mob2041_vunlph12949.model.PhieuMuon;
import com.example.mob2041_vunlph12949.utilities.Utilities;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DoanhThuFragment extends Fragment {
    EditText edTuNgay, edDenNgay;
    ImageView imgTuNgay, imgDenNgay;
    PhieuMuonDAO daoPM;
    Button btnXem;
    DoanhThuAdapter adapter;
    RecyclerView rvPM;
    TextView tvDoanhThu;
    List<PhieuMuon> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edTuNgay = view.findViewById(R.id.edTuNgay);
        edDenNgay = view.findViewById(R.id.edDenNgay);
        imgTuNgay = view.findViewById(R.id.imgTuNgay);
        imgDenNgay = view.findViewById(R.id.imgDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnXem = view.findViewById(R.id.btnXem);
        rvPM = view.findViewById(R.id.rvDoanhThu);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rvPM.setLayoutManager(manager);

        daoPM = new PhieuMuonDAO(getActivity());
        daoPM.open();

        list = new ArrayList<>();
        adapter = new DoanhThuAdapter(list);
        rvPM.setAdapter(adapter);

        imgTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(edTuNgay);
            }
        });
        imgDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(edDenNgay);
            }
        });
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTuNgay.getText().length() == 0 && edDenNgay.getText().length() == 0) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    String tien_fm = formatter.format(daoPM.getDoanhThu(edTuNgay.getText().toString(), edDenNgay.getText().toString()));
                    tvDoanhThu.setText(tien_fm + " vnđ");

                    list.clear();
                    list.addAll(daoPM.getDoanhThuCT(edTuNgay.getText().toString(), edDenNgay.getText().toString()));
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    public void datePicker(EditText ed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                ed.setText(Utilities.dateToString(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoPM.close();
    }
}