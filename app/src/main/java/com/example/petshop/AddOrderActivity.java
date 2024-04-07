package com.example.petshop;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddOrderActivity extends AppCompatActivity {

    TextView text1, text2, text3, text4, PetCost;
    Button btn_back, btn_addtocart;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        image = findViewById(R.id.imagePet);

        btn_back = findViewById(R.id.buttonBack);
        btn_addtocart = findViewById(R.id.buttonAddtoCart);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        PetCost = findViewById(R.id.PetCost);

        Intent intent = getIntent();
        image.setImageResource(intent.getIntExtra("image", 0));
        text1.setText(intent.getStringExtra("text1"));
        text2.setText(intent.getStringExtra("text2"));
        text3.setText(intent.getStringExtra("text3"));
        text4.setText(intent.getStringExtra("text4"));
        PetCost.setText("Giá : " + intent.getStringExtra("PetCost") + "đ");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddOrderActivity.this, PetDetailActivity.class));
            }
        });

        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddOrderActivity.this, PetDetailActivity.class));
            }
        });
    }
}