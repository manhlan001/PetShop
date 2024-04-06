package com.example.petshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        CardView home = findViewById(R.id.cardHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetActivity.this, HomeActivity.class));
            }
        });

        CardView cat = findViewById(R.id.cardCat);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PetActivity.this,PetDetailActivity.class);
                it.putExtra("title", "Mèo");
                startActivity(it);
            }
        });

        CardView dog = findViewById(R.id.cardDog);
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PetActivity.this,PetDetailActivity.class);
                it.putExtra("title", "Chó");
                startActivity(it);
            }
        });

        CardView fish = findViewById(R.id.cardFish);
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PetActivity.this,PetDetailActivity.class);
                it.putExtra("title", "Cá");
                startActivity(it);
            }
        });
    }
}