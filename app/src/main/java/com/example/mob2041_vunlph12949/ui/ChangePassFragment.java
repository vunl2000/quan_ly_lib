


package com.example.mob2041_vunlph12949.ui;

import android.content.Context;
import android.content.SharedPreferences;
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


public class ChangePassFragment extends Fragment {
    ThuThuDAO dao;
    EditText edPassOld, edPass, edRePass;
    Button btnSave, btnCancel;
    ThuThu thuThu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edPassOld = view.findViewById(R.id.edUserChange);
        edPass = view.findViewById(R.id.edPassChange);
        edRePass = view.findViewById(R.id.edRePassChange);
        btnSave = view.findViewById(R.id.btnSaveUserChange);
        btnCancel = view.findViewById(R.id.btnCancelUserChange);


        dao = new ThuThuDAO(getActivity());
        dao.open();

        String user = getActivity().getIntent().getStringExtra("user");

        thuThu = dao.getId(user);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate() > 0) {

                    thuThu.setMaKhau(edPass.getText().toString());
                    int kq = dao.updatePass(thuThu);
                    if (kq > 0) {
                        loadPref(user);
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    public int validate() {
        int check = 1;
        if (edPassOld.getText().length() == 0 || edPass.getText().length() == 0 || edRePass.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {

            String oldPass = thuThu.getMaKhau();
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!oldPass.equals(edPassOld.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }

    public void loadPref(String user) {
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (pref.getBoolean("REMEMBER", false)) {
            editor.clear();
            editor.putString("USERNAME", user);
            editor.putString("PASSWORLD", edPass.getText().toString());
            editor.putBoolean("REMEMBER", true);
            editor.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}