package com.example.petshop;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Spliterator;

public class AddPetActivity extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    private final String[] otypePet = {"Mèo", "Chó", "Cá"};
    private final String[] sexPet = {"Đực","Cái"};
    Spinner otypePet_spinner;
    Spinner sexPet_spinner;
    EditText edname, edold, edcolor, edprice, edquantity;
    Button btn_addImage, btn_add, btn_back;
    ImageView imagePet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        otypePet_spinner = findViewById(R.id.otypePetSpinner);
        sexPet_spinner = findViewById(R.id.sexPetSpinner);
        edname = findViewById(R.id.namePet);
        edold = findViewById(R.id.oldPet);
        edcolor = findViewById(R.id.colorPet);
        edprice = findViewById(R.id.pricePet);
        edquantity = findViewById(R.id.quantityPet);
        btn_add = findViewById(R.id.buttonAddPet);
        btn_back = findViewById(R.id.buttonAddPetBack);
        btn_addImage = findViewById(R.id.buttonAddImage);
        imagePet = findViewById(R.id.imageAddPet);

        ArrayAdapter<String> otypePet_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, otypePet);
        ArrayAdapter<String> sexPet_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexPet);

        otypePet_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexPet_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        otypePet_spinner.setAdapter(otypePet_Adapter);
        sexPet_spinner.setAdapter(sexPet_Adapter);

        Intent intent = getIntent();
        String title = intent.getStringExtra("pet_title");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddPetActivity.this,PetDetailActivity.class);
                it.putExtra("title", title);
                startActivity(it);
            }
        });

        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallary = new Intent(Intent.ACTION_PICK);
                iGallary.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallary, GALLERY_REQ_CODE);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String old = edold.getText().toString();
                String color = edcolor.getText().toString();
                String priceText = edprice.getText().toString();
                String quantityText = edquantity.getText().toString();
                byte[] image = ImageViewToByte(imagePet);
                Database db = new Database(getApplicationContext(), "PetShop", null, 1);
                if(name.isEmpty() || old.isEmpty() || color.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || image == null) {
                    Toast.makeText(getApplicationContext(), "Chưa có đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Float price = Float.parseFloat(priceText);
                        int quantity = Integer.parseInt(quantityText);
                        db.AddPet(image, name, old, sexPet_spinner.getSelectedItem().toString(), color, quantity, price, otypePet_spinner.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Đã thêm vào danh sách", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(AddPetActivity.this,PetDetailActivity.class);
                        it.putExtra("title", title);
                        startActivity(it);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Giá tiền không hợp lệ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == GALLERY_REQ_CODE) {
                // Lấy đường dẫn của hình ảnh được chọn từ Intent
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        imagePet.setImageURI(data.getData());
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