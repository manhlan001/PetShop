package com.example.petshop;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class CustomEmployeeActivity extends AppCompatActivity {
    private String Id_nhanvien;
    private DatePickerDialog datePickerDialog;
    private final String[] sexEmployee = {"Nam", "Nữ"};
    private final String[] ChucVuEmployee = {"Quản lý", "Nhân viên", "Thu ngân"};
    Spinner sexEmployee_spinner;
    Spinner ChucVuEmployee_spinner;
    EditText edold, edsdt, edgmail, eddiachi, edluong;
    TextView tvname, tvdate;
    Button btn_custom, btn_back;
    Database database;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_employee);

        database = new Database(this, "PetShop", null, 1);

        sexEmployee_spinner = findViewById(R.id.sexCustomEmployeeSpinner);
        ChucVuEmployee_spinner = findViewById(R.id.ChucVuCustomSpinner);
        tvname = findViewById(R.id.nameCustomEmployee);
        edold = findViewById(R.id.oldCustomEmployee);
        edsdt = findViewById(R.id.SDTCustomEmployee);
        edgmail = findViewById(R.id.GmailCustomEmployee);
        eddiachi = findViewById(R.id.DiaChiCustomEmployee);
        edluong = findViewById(R.id.LuongCustomEmployee);
        tvdate = findViewById(R.id.DateCustomEmployee);
        btn_custom = findViewById(R.id.buttonCustomEmployee);
        btn_back = findViewById(R.id.buttonCustomEmployeeBack);

        ArrayAdapter<String> sexEmployee_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexEmployee);
        ArrayAdapter<String> ChucVuEmployee_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ChucVuEmployee);

        sexEmployee_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChucVuEmployee_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexEmployee_spinner.setAdapter(sexEmployee_Adapter);
        ChucVuEmployee_spinner.setAdapter(ChucVuEmployee_Adapter);

        Intent intent = getIntent();
        Id_nhanvien = intent.getStringExtra("id_nhanvien");

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = getDataById(db, "nhanvien", Id_nhanvien);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String old = cursor.getString(cursor.getColumnIndex("old"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String sdt = cursor.getString(cursor.getColumnIndex("sdt"));
            String gmail = cursor.getString(cursor.getColumnIndex("gmail"));
            String diachi = cursor.getString(cursor.getColumnIndex("diachi"));
            String chucvu = cursor.getString(cursor.getColumnIndex("chucvu"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String luong = cursor.getString(cursor.getColumnIndex("luong"));

            int position_sex = findPosition(sexEmployee, sex);
            sexEmployee_spinner.setSelection(position_sex);
            int position_chucvu = findPosition(ChucVuEmployee, chucvu);
            ChucVuEmployee_spinner.setSelection(position_chucvu);

            tvname.setText(name);
            edold.setHint(old);
            edsdt.setHint(sdt);
            edgmail.setHint(gmail);
            eddiachi.setHint(diachi);
            edluong.setHint(luong);

            initDatePicker(date);
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
        }


        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CustomEmployeeActivity.this,EmployeeActivity.class);
                startActivity(it);
            }
        });

        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_custom = edold.getText().toString();
                String sex_custom = sexEmployee_spinner.getSelectedItem().toString();
                String sdt_custom = edsdt.getText().toString();
                String gmail_custom = edgmail.getText().toString();
                String diachi_custom = eddiachi.getText().toString();
                String chucvu_custom = ChucVuEmployee_spinner.getSelectedItem().toString();
                String date_custom = tvdate.getText().toString();
                String luong_custom = edluong.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("sex", sex_custom);
                cv.put("chucvu", chucvu_custom);
                cv.put("date", date_custom);
                if(!old_custom.isEmpty()){
                    cv.put("old", old_custom);
                }
                if(!sdt_custom.isEmpty()){
                    cv.put("sdt", sdt_custom);
                }
                if(!gmail_custom.isEmpty()){
                    cv.put("gmail", gmail_custom);
                }
                if(!diachi_custom.isEmpty()){
                    cv.put("diachi", diachi_custom);
                }
                if(!luong_custom.isEmpty()){
                    cv.put("luong", luong_custom);
                }
                sqLiteDatabase = database.getWritableDatabase();
                String whereClause = "name = ?";
                String[] whereArgs = {Id_nhanvien};
                long recustom = sqLiteDatabase.update("nhanvien", cv, whereClause, whereArgs);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(CustomEmployeeActivity.this, EmployeeActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void initDatePicker(String date){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                tvdate.setText(date);
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
    public Cursor getDataById(SQLiteDatabase database, String tableName, String id) {
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        Cursor cursor = database.rawQuery(query, new String[]{id});
        return cursor;
    }

    public int findPosition(String[] array, String text) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                return i;
            }
        }
        return -1;
    }
}
