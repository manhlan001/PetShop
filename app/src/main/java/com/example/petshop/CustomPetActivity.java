package com.example.petshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class CustomPetActivity extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    SQLiteDatabase sqLiteDatabase;
    Database database;
    EditText text1, text2, text3, text4, text5, PetCost;
    Button btn_back, btn_custom;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pet);

        image = findViewById(R.id.imageCustomPet);
        btn_custom = findViewById(R.id.buttonCustomPet);
        btn_back = findViewById(R.id.buttonBackCustomPet);

        text1 = findViewById(R.id.CustomPettext1);
        text2 = findViewById(R.id.CustomPettext2);
        text3 = findViewById(R.id.CustomPettext3);
        text4 = findViewById(R.id.CustomPettext4);
        text5 = findViewById(R.id.CustomPettext5);
        PetCost = findViewById(R.id.CustomPetCost);

        database = new Database(this, "PetShop", null, 1);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int id = intent.getIntExtra("id", 0);
        byte[] image_pet = intent.getByteArrayExtra("image");
        assert image_pet != null;
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
                Intent it = new Intent(CustomPetActivity.this, Delete_Custom_Activity.class);
                it.putExtra("title", title);
                startActivity(it);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallary = new Intent(Intent.ACTION_PICK);
                iGallary.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallary, GALLERY_REQ_CODE);
            }
        });

        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] image_customPet = ImageViewToByte(image);
                ContentValues cv = new ContentValues();
                cv.put("imagePet", image_customPet);
                cv.put("namePet", text1.getText().toString());
                cv.put("old", text2.getText().toString());
                cv.put("sex", text3.getText().toString());
                cv.put("color", text4.getText().toString());
                cv.put("quantity", text5.getText().toString());
                cv.put("price", PetCost.getText().toString());

                sqLiteDatabase = database.getWritableDatabase();
                long recustom = sqLiteDatabase.update("pets", cv, "id" + id, null);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(CustomPetActivity.this, PetDetailActivity.class);
                    it.putExtra("title", title);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == GALLERY_REQ_CODE) {
                // Lấy đường dẫn của hình ảnh được chọn từ Intent
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        image.setImageURI(data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private byte[] ImageViewToByte(ImageView imagePet){
        Bitmap bitmap = ((BitmapDrawable)imagePet.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null) {
            if (bitmap.hasAlpha()) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
        } else {
            return null;
        }
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}

