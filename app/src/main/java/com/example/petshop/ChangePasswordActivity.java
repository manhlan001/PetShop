package com.example.petshop;

import static com.example.petshop.SignupActivity.isValid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edOldPassword, edNewPassword, edNewPasswordConfirm;
    Button btn_changepassword, btn_back;
    SQLiteDatabase sqLiteDatabase;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edOldPassword = findViewById(R.id.editTextOldPassword);
        edNewPassword = findViewById(R.id.editTextNewPassword);
        edNewPasswordConfirm = findViewById(R.id.editTextNewPasswordConfirm);
        btn_changepassword = findViewById(R.id.buttonChangePassword);
        btn_back = findViewById(R.id.buttonChangePasswordBack);

        database = new Database(this, "PetShop", null, 1);

        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword = edOldPassword.getText().toString();
                String newpassword = edNewPassword.getText().toString();
                String newpasswordconfirmn = edNewPasswordConfirm.getText().toString();
                if(oldpassword.isEmpty() || newpassword.isEmpty() || newpasswordconfirmn.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Chưa nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    if(newpassword.compareTo(oldpassword) == 0){
                        Toast.makeText(getApplicationContext(), "Mật khẩu mới không được giống mật khẩu cũ", Toast.LENGTH_LONG).show();
                    } else {
                        if(newpassword.compareTo(newpasswordconfirmn) == 0){
                            if(isValid(newpassword)) {
                                ContentValues cv = new ContentValues();
                                cv.put("password", newpassword);
                                sqLiteDatabase = database.getWritableDatabase();
                                String whereClause = "password = ?";
                                String[] whereArgs = {oldpassword};
                                long rechangepassword = sqLiteDatabase.update("users", cv, whereClause, whereArgs);
                                if(rechangepassword != -1){
                                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 8 ký tự, có chữ, số và ký tự đặt biệt", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại chưa trùng khớp", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this, HomeActivity.class));
            }
        });
    }
}