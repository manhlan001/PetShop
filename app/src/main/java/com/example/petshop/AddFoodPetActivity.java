package com.example.petshop;

import android.content.Intent;
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

public class AddFoodPetActivity extends AppCompatActivity {
    EditText edname, edprice, edquantity;
    Button btn_add, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_pet);

        edname = findViewById(R.id.nameFoodPet);
        edprice = findViewById(R.id.priceFoodPet);
        edquantity = findViewById(R.id.quantityFoodPet);
        btn_add = findViewById(R.id.buttonAddFoodPet);
        btn_back = findViewById(R.id.buttonAddFoodPetBack);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddFoodPetActivity.this, FoodActivity.class);
                startActivity(it);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String priceText = edprice.getText().toString();
                String quantityText = edquantity.getText().toString();
                Database db = new Database(getApplicationContext(), "PetShop", null, 1);
                if(name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Chưa có đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Float price = Float.parseFloat(priceText);
                        int quantity = Integer.parseInt(quantityText);
                        db.AddFoodPet(name, quantity, price);
                        Toast.makeText(getApplicationContext(), "Đã thêm vào danh sách", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(AddFoodPetActivity.this, FoodActivity.class);
                        startActivity(it);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Giá tiền không hợp lệ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}