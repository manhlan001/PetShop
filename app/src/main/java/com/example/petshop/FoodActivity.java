package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class FoodActivity extends AppCompatActivity {
    Button btn_back, btn_addFoodPet;
    HashMap<String, Object> item;
    ArrayList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        btn_back = findViewById(R.id.buttonFoodPetBack);
        btn_addFoodPet = findViewById(R.id.buttonAddFoodPet);
        list = new ArrayList();

        Database database = new Database(this, "PetShop", null, 1);

        ArrayList<FoodPet> foodPetList = database.getAllFoodPet();

        for (FoodPet foodPet : foodPetList) {
            item = new HashMap<String, Object>();
            item.put("name", foodPet.getName());
            item.put("price", "Giá: " + foodPet.getPrice() + "đ");
            item.put("quantity", "Số lượng: " + foodPet.getQuantity());
            list.add(item);
        }

        if (list.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng thêm thức ăn hoặc phụ kiện thứ cưng cho cửa hàng", Toast.LENGTH_LONG).show();
        } else {
            SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.food_multi_line,
                    new String[]{"name", "quantity", "price"},
                    new int[]{R.id.nameFoodPetMulti, R.id.QuantityEmployeeMulti, R.id.PriceFoodPetMulti}
            );

            ListView lst = findViewById(R.id.listViewFood);
            lst.setAdapter(sa);

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(FoodActivity.this, FoodPetDetailActivity.class);
                    HashMap<String, Object> selectedItem = (HashMap<String, Object>) parent.getItemAtPosition(position);
                    it.putExtra("name", selectedItem.get("name").toString());
                    it.putExtra("quantity", selectedItem.get("quantity").toString());
                    it.putExtra("price", selectedItem.get("price").toString());
                    startActivity(it);
                }
            });
        }

        btn_addFoodPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this, AddFoodPetActivity.class));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this, HomeActivity.class));
            }
        });
    }
}