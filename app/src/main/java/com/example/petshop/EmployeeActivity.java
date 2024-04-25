package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeActivity extends AppCompatActivity {
    Button btn_addEmployee, btn_back;
    HashMap<String, Object> item;
    ArrayList list_nhanvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        btn_addEmployee = findViewById(R.id.buttonAddEmployee);
        btn_back = findViewById(R.id.buttonEmployeeBack);
        list_nhanvien = new ArrayList();

        Database database = new Database(this, "PetShop", null, 1);

        ArrayList<NhanVien> NhanVienList = database.getAllNhanVien();

        for (NhanVien NhanVien : NhanVienList) {
            item = new HashMap<String, Object>();
            item.put("id_nhanvien", NhanVien.getName());
            item.put("name", "Họ và tên:" + NhanVien.getName());
            item.put("old", "Ngày sinh: " + NhanVien.getOld());
            item.put("sex", "Giới tính: " + NhanVien.getSex());
            item.put("sdt", "Số điện thoại: " + NhanVien.getSdt());
            item.put("gmail", "Gmail: " + NhanVien.getGmail());
            item.put("diachi", "Địa chỉ: " + NhanVien.getDiachi());
            item.put("chucvu", "Chức vụ: " + NhanVien.getChucvu());
            item.put("date", "Ngày vào làm: " + NhanVien.getDate());
            item.put("luong", "Lương: " + NhanVien.getLuong());
            list_nhanvien.add(item);
        }

        if (list_nhanvien.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng thêm nhân viên cho cửa hàng", Toast.LENGTH_LONG).show();
        } else {
            SimpleAdapter sa = new SimpleAdapter(this, list_nhanvien, R.layout.nhanvien_multi_line,
                    new String[]{"name", "chucvu"},
                    new int[]{R.id.nameEmployeeMulti, R.id.ChucVuEmployeeMulti}
            );

            ListView lst = findViewById(R.id.listViewEmployee);
            lst.setAdapter(sa);

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(EmployeeActivity.this, EmployeeDetailActivity.class);
                    HashMap<String, Object> selectedItem = (HashMap<String, Object>) parent.getItemAtPosition(position);
                    it.putExtra("id_nhanvien", selectedItem.get("id_nhanvien").toString());
                    it.putExtra("name", selectedItem.get("name").toString());
                    it.putExtra("old", selectedItem.get("old").toString());
                    it.putExtra("sex", selectedItem.get("sex").toString());
                    it.putExtra("sdt", selectedItem.get("sdt").toString());
                    it.putExtra("gmail", selectedItem.get("gmail").toString());
                    it.putExtra("diachi", selectedItem.get("diachi").toString());
                    it.putExtra("chucvu", selectedItem.get("chucvu").toString());
                    it.putExtra("date", selectedItem.get("date").toString());
                    it.putExtra("luong", selectedItem.get("luong").toString());
                    startActivity(it);
                }
            });
        }
        btn_addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
                startActivity(it);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this, HomeActivity.class));
            }
        });
    }
}