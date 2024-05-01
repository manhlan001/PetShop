package com.example.petshop;

import android.content.ContentValues;
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

public class FoodPetDetailActivity extends AppCompatActivity {
    private String id;
    TextView tvname;
    EditText edprice, edquantity;
    Button btn_custom, btn_delete, btn_back;
    SQLiteDatabase sqLiteDatabase;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_pet_detail);

        tvname = findViewById(R.id.nameFoodPetDetail);
        edprice = findViewById(R.id.priceFoodPetDetail);
        edquantity = findViewById(R.id.quantityFoodPetDetail);
        btn_custom = findViewById(R.id.buttonCustomFoodPet);
        btn_delete = findViewById(R.id.buttonDeleteFoodPetBack);
        btn_back = findViewById(R.id.buttonFoodPetDetailBack);

        database = new Database(this, "PetShop", null, 1);

        Intent intent = getIntent();
        tvname.setText(intent.getStringExtra("name"));
        edquantity.setHint(intent.getStringExtra("quantity"));
        edprice.setHint(intent.getStringExtra("price"));
        id = intent.getStringExtra("name");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FoodPetDetailActivity.this, FoodActivity.class);
                startActivity(it);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = database.getWritableDatabase();
                String whereClause = "name = ?";
                String[] whereArgs = {id};
                long recustom = sqLiteDatabase.delete("foodpet", whereClause, whereArgs);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(FoodPetDetailActivity.this, FoodActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                String priceText = edprice.getText().toString();
                String quantityText = edquantity.getText().toString();
                if (!quantityText.isEmpty()) {
                    int quantity = Integer.parseInt(quantityText);
                    cv.put("quantity", quantity);
                }
                if (!priceText.isEmpty()) {
                    Float price = Float.parseFloat(priceText);
                    cv.put("price", price);
                }
                sqLiteDatabase = database.getWritableDatabase();
                String whereClause = "name = ?";
                String[] whereArgs = {id};
                long recustom = sqLiteDatabase.update("foodpet", cv, whereClause, whereArgs);
                if(recustom != -1){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(FoodPetDetailActivity.this, FoodActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Không cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}