package com.example.petshop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeDetailActivity extends AppCompatActivity {
    private String Id_nhanvien;
    TextView name, old, sex, sdt, gmail, diachi, chucvu, date, luong;
    Button btn_delete, btn_custom, btn_back;
    SQLiteDatabase sqLiteDatabase;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        database = new Database(this, "PetShop", null, 1);

        name = findViewById(R.id.DetailEmployee_Name);
        old = findViewById(R.id.DetailEmployee_Old);
        sex = findViewById(R.id.DetailEmployee_Sex);
        sdt = findViewById(R.id.DetailEmployee_Sdt);
        gmail = findViewById(R.id.DetailEmployee_Gmail);
        diachi = findViewById(R.id.DetailEmployee_Diachi);
        chucvu = findViewById(R.id.DetailEmployee_Chucvu);
        date = findViewById(R.id.DetailEmployee_Date);
        luong = findViewById(R.id.DetailEmployee_Luong);
        btn_delete = findViewById(R.id.buttonDeleteEmployee);
        btn_back = findViewById(R.id.buttonDetailEmployeeBack);
        btn_custom = findViewById(R.id.buttonDetailEmployeeCustom);

        Intent intent = getIntent();
        Id_nhanvien = intent.getStringExtra("id_nhanvien");
        name.setText(intent.getStringExtra("name"));
        old.setText(intent.getStringExtra("old"));
        sex.setText(intent.getStringExtra("sex"));
        sdt.setText(intent.getStringExtra("sdt"));
        gmail.setText(intent.getStringExtra("gmail"));
        diachi.setText(intent.getStringExtra("diachi"));
        chucvu.setText(intent.getStringExtra("chucvu"));
        date.setText(intent.getStringExtra("date"));
        luong.setText(intent.getStringExtra("luong"));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeDetailActivity.this, EmployeeActivity.class));
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = database.getWritableDatabase();
                String whereClause = "name = ?";
                String[] whereArgs = {Id_nhanvien};
                long recustom = sqLiteDatabase.delete("nhanvien", whereClause, whereArgs);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(EmployeeDetailActivity.this, EmployeeActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không xóa thành công", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EmployeeDetailActivity.this, CustomEmployeeActivity.class);
                it.putExtra("id_nhanvien", Id_nhanvien);
                startActivity(it);
            }
        });
    }
}