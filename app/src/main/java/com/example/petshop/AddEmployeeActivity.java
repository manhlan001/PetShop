package com.example.petshop;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddEmployeeActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private final String[] sexEmployee = {"Nam","Nữ"};
    private final String[] ChucVuEmployee = {"Quản lý", "Nhân viên", "Thu ngân"};
    Spinner sexEmployee_spinner;
    Spinner ChucVuEmployee_spinner;
    EditText edname, edold, edsdt, edgmail, eddiachi, edluong;
    TextView tvdate;
    Button btn_add, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        sexEmployee_spinner = findViewById(R.id.sexEmployeeSpinner);
        ChucVuEmployee_spinner = findViewById(R.id.ChucVuSpinner);
        edname = findViewById(R.id.nameEmployee);
        edold = findViewById(R.id.oldEmployee);
        edsdt = findViewById(R.id.SDTEmployee);
        edgmail = findViewById(R.id.GmailEmployee);
        eddiachi = findViewById(R.id.DiaChiEmployee);
        edluong = findViewById(R.id.LuongEmployee);
        tvdate = findViewById(R.id.DateEmployee);
        btn_add = findViewById(R.id.buttonAddEmployee);
        btn_back = findViewById(R.id.buttonAddEmployeeBack);

        ArrayAdapter<String> sexEmployee_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexEmployee);
        ArrayAdapter<String> ChucVuEmployee_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ChucVuEmployee);

        sexEmployee_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChucVuEmployee_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexEmployee_spinner.setAdapter(sexEmployee_Adapter);
        ChucVuEmployee_spinner.setAdapter(ChucVuEmployee_Adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddEmployeeActivity.this,EmployeeActivity.class);
                startActivity(it);
            }
        });

        initDatePicker();
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String old = edold.getText().toString();
                String sdt = edsdt.getText().toString();
                String gmail = edgmail.getText().toString();
                String diachi = eddiachi.getText().toString();
                String luong = edluong.getText().toString();
                String date = tvdate.getText().toString();
                Database db = new Database(getApplicationContext(), "PetShop", null, 1);
                if(name.isEmpty() || old.isEmpty() || sdt.isEmpty() || gmail.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Chưa có đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    db.AddNhanVien(name, old, sexEmployee_spinner.getSelectedItem().toString(), sdt, gmail, diachi, ChucVuEmployee_spinner.getSelectedItem().toString(), date, luong);
                    Toast.makeText(getApplicationContext(), "Đã thêm vào danh sách", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(AddEmployeeActivity.this, EmployeeActivity.class);
                    startActivity(it);
                }
            }
        });
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                tvdate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + 86400000);
    }
}