package com.example.mob2041_vunlph12949.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.dao.ThuThuDAO;
import com.example.mob2041_vunlph12949.model.ThuThu;

import org.jetbrains.annotations.NotNull;


public class AddUserFragment extends Fragment {
    ThuThuDAO dao;
    EditText edUser, edPass, edRePass, edHoTen;
    Button btnSave, btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edUser = view.findViewById(R.id.edUser);
        edHoTen = view.findViewById(R.id.edHoTenTV);
        edPass = view.findViewById(R.id.edPass);
        edRePass = view.findViewById(R.id.edRePass);
        btnSave = view.findViewById(R.id.btnSaveUser);
        btnCancel = view.findViewById(R.id.btnCancelUser);

        dao = new ThuThuDAO(getActivity());
        dao.open();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUser.setText("");
                edHoTen.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThu thuThu = new ThuThu();

                thuThu.setMaTT(edUser.getText().toString());
                thuThu.setHoTen(edHoTen.getText().toString());
                thuThu.setMaKhau(edPass.getText().toString());
                if (validate() > 0) {
                    long kq = dao.insert(thuThu);
                    if (kq > 0) {
                        Toast.makeText(getActivity(), "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                        edUser.setText("");
                        edHoTen.setText("");
                        edPass.setText("");
                        edRePass.setText("");

                    } else {
                        Toast.makeText(getActivity(), "Thêm tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tao_tai_khoan, container, false);
    }

    public int validate() {
        int check = 1;
        if (edUser.getText().length() == 0 || edHoTen.getText().length() == 0 ||
                edPass.getText().length() == 0 || edRePass.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}