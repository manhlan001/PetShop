package com.example.petshop;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class Delete_Custom_Activity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    Database database;
    TextView text1, text2, text3, text4, text5, PetCost;
    Button btn_back, btn_custom, btn_delete, btn_sale;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_custom);

        database = new Database(this, "PetShop", null, 1);

        image = findViewById(R.id.imagePet);

        btn_back = findViewById(R.id.buttonBack);
        btn_delete = findViewById(R.id.buttonDelete);
        btn_custom = findViewById(R.id.buttonCustom);
        btn_sale = findViewById(R.id.buttonSale);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        PetCost = findViewById(R.id.PetCost);

        Intent intent = getIntent();
        String title = intent.getStringExtra("pet_title");
        String id = intent.getStringExtra("Id_pet");
        byte[] image_pet = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_pet, 0, image_pet.length);
        image.setImageBitmap(bitmap);
        text1.setText(intent.getStringExtra("text1"));
        text2.setText(intent.getStringExtra("text2"));
        text3.setText(intent.getStringExtra("text3"));
        text4.setText(intent.getStringExtra("text4"));
        text5.setText(intent.getStringExtra("text5"));
        PetCost.setText(intent.getStringExtra("PetPrice"));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Delete_Custom_Activity.this, PetDetailActivity.class);
                it.putExtra("title", title);
                startActivity(it);
            }
        });

        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Delete_Custom_Activity.this, CustomPetActivity.class);
                it.putExtra("title", title);
                it.putExtra("Id_pet", id);
                it.putExtra("image", intent.getByteArrayExtra("image"));
                it.putExtra("text1", intent.getStringExtra("text1"));
                it.putExtra("text2", intent.getStringExtra("text2"));
                it.putExtra("text3", intent.getStringExtra("text3"));
                it.putExtra("text4", intent.getStringExtra("text4"));
                it.putExtra("text5", intent.getStringExtra("text5"));
                it.putExtra("PetPrice", intent.getStringExtra("PetPrice"));
                startActivity(it);
            }
        });

        btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityText = intent.getStringExtra("quantity");
                int quantity = Integer.parseInt(quantityText);
                final int[] quantity_after_sale = {0};
                String[] optionsArray = new String[quantity]; // Tạo một mảng chuỗi với kích thước bằng với số lượng
                for (int i = 0; i < quantity; i++) {
                    optionsArray[i] = String.valueOf(i + 1); // Điền mảng với các chuỗi số từ 1 đến quantity
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Delete_Custom_Activity.this);
                builder.setTitle("Chọn số lượng đã bán")
                        .setItems(optionsArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedOption = optionsArray[which];
                                quantity_after_sale[0] = Integer.parseInt(selectedOption);
                                ContentValues cv = new ContentValues();
                                cv.put("quantity", quantity - quantity_after_sale[0]);
                                String whereClause = "namePet = ?";
                                String[] whereArgs = {id};
                                sqLiteDatabase = database.getWritableDatabase();
                                long redelete = sqLiteDatabase.update("pets", cv, whereClause, whereArgs);
                                if(redelete != -1){
                                    Intent it = new Intent(Delete_Custom_Activity.this, PetDetailActivity.class);
                                    it.putExtra("title", title);
                                    startActivity(it);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Không thể cập nhật", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = database.getWritableDatabase();
                String whereClause = "namePet = ?";
                String[] whereArgs = {id};
                long recustom = sqLiteDatabase.delete("pets", whereClause, whereArgs);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(Delete_Custom_Activity.this, PetDetailActivity.class);
                    it.putExtra("title", title);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}