package com.example.mob2041_vunlph12949;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mob2041_vunlph12949.dao.ThuThuDAO;
import com.example.mob2041_vunlph12949.model.ThuThu;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassWorld;
    Button btnLogin, btnCancel;
    CheckBox chkRememberPass;
    ThuThuDAO dao;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = findViewById(R.id.edUserName);
        edPassWorld = findViewById(R.id.edPassWorld);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancelUser);
        chkRememberPass = findViewById(R.id.chkRememberPass);

        dao = new ThuThuDAO(this);
        dao.open();
        if (dao.getUser("admin") < 0) {
            dao.insert(new ThuThu("admin", "admin", "admin"));
        }

        // Đọc Uer, Pass, Checkbox trong SharePreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUserName.setText(pref.getString("USERNAME", ""));
        edPassWorld.setText(pref.getString("PASSWORLD", ""));
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER", false));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassWorld.setText("");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    public void checkLogin() {
        String strUser = edUserName.getText().toString();
        String strPass = edPassWorld.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mất khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (dao.getLogin(strUser, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRememberPass.isChecked());
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String user, String pass, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            // Xóa tinh trạng lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", user);
            editor.putString("PASSWORLD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        // LƯu lại toàn bộ
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}