package com.example.petshop;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class PetDetailActivity extends AppCompatActivity {
    private String[][] cat_detail = {
            {"Loại mèo: Mèo Ragdoll", "Tuổi: 6 tháng", "Giới tính: Cái", "Màu lông: Trắng", "1.000.000"},
            {"Loại mèo: Mèo Mướp", "Tuổi: 3 tháng", "Giới tính: Đực", "Màu lông: Xám", "1.500.000"},
            {"Loại mèo: Mèo Xiêm", "Tuổi: 12 tháng", "Giới tính: Cái", "Màu lông: Trắng", "3.000.000"},
            {"Loại mèo: Mèo tai cụp", "Tuổi: 6 tháng", "Giới tính: Đực", "Màu lông: Trắng và xám", "2.000.000"},
            {"Loại mèo: Mèo Maine Coon", "Tuổi: 18 tháng", "Giới tính: Cái", "Màu lông: Xám đen", "1.200.000"},
            {"Loại mèo: Mèo Bengal", "Tuổi: 9 tháng", "Giới tính: Đực", "Màu lông: Cam đốm", "2.300.000"},
            {"Loại mèo: Mèo Sphynx", "Tuổi: 8 tháng", "Giới tính: Cái", "Không lông", "2.600.000"},
    };

    private int cat_image[] = {
            R.drawable.cat_ragdoll,
            R.drawable.cat_muop,
            R.drawable.cat_xiem,
            R.drawable.cat_taicup,
            R.drawable.cat_mainecoon,
            R.drawable.cat_bengal,
            R.drawable.cat_sphynx
    };

    private String[][] dog_detail = {
            {"Loại chó: Chó Husky", "Tuổi: 2 năm", "Giới tính: Đực", "Màu lông: Xám và Trắng", "1.000.000"},
            {"Loại chó: Chó Poodle", "Tuổi: 1 năm", "Giới tính: Cái", "Màu lông: Trắng", "1.500.000"},
            {"Loại chó: Chó Corgi", "Tuổi: 3 năm", "Giới tính: Đực", "Màu lông: Vàng và Trắng", "3.000.000"},
            {"Loại chó: Chó Golden Retriever", "Tuổi: 4 năm", "Giới tính: Cái", "Màu lông: Vàng", "2.000.000"},
            {"Loại chó: Chó Labrador Retriever", "Tuổi: 5 năm", "Giới tính: Cái", "Màu lông: Đen", "1.200.000"},
            {"Loại chó: Chó Bulldog", "Tuổi: 2 năm", "Giới tính: Đực", "Màu lông: Nâu", "2.300.000"},
            {"Loại chó: Chó Beagle", "Tuổi: 3 năm", "Giới tính: Đực", "Màu lông: Trắng và Nâu", "3.500.000"},
    };

    private int dog_image[] = {
            R.drawable.dog_husky,
            R.drawable.dog_poodle,
            R.drawable.dog_corgi,
            R.drawable.dog_golden,
            R.drawable.dog_labrador,
            R.drawable.dog_bull,
            R.drawable.dog_beagle
    };

    private String[][] fish_detail = {
            {"Loại cá: Cá vàng", "Tuổi: 1 năm", "Giới tính: Đa dạng", "Màu sắc: Vàng", "500.000"},
            {"Loại cá: Cá mè", "Tuổi: 6 tháng", "Giới tính: Đa dạng", "Màu sắc: Đa dạng", "1.000.000"},
            {"Loại cá: Cá Betta", "Tuổi: 1 năm", "Giới tính: Đa dạng", "Màu sắc: Đa dạng", "2.000.000"},
            {"Loại cá: Cá Guppy", "Tuổi: 2 năm", "Giới tính: Đa dạng", "Màu sắc: Đa dạng", "3.000.000"},
            {"Loại cá: Cá Koi", "Tuổi: 3 năm", "Giới tính: Đa dạng", "Màu sắc: Đa dạng", "3.500.000"},
            {"Loại cá: Cá Sặc gấm", "Tuổi: 4 năm", "Giới tính: Đa dạng", "Màu sắc: Hồng", "2.200.000"},
            {"Loại cá: Cá Ngựa vằn", "Tuổi: 2 năm", "Giới tính: Đa dạng", "Màu sắc: Xanh", "800.000"}
    };

    private int fish_image[] = {
            R.drawable.fish_vang,
            R.drawable.fish_me,
            R.drawable.fish_betta,
            R.drawable.fish_guppy,
            R.drawable.fish_koi,
            R.drawable.fish_sacgam,
            R.drawable.fish_nguavan
    };

    TextView tv;
    Button btn;
    String[][] pet_detail = {};
    int[] pet_image = {};
    ArrayList list;
    HashMap<String, Object> item;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        tv = findViewById(R.id.textViewPetDetail);
        btn = findViewById(R.id.buttonPetBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Mèo") == 0){
            pet_detail = cat_detail;
            pet_image = cat_image;
        }
        else if(title.compareTo("Chó") == 0){
            pet_detail = dog_detail;
            pet_image = dog_image;
        }
        else{
            pet_detail = fish_detail;
            pet_image = fish_image;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetDetailActivity.this, PetActivity.class));
            }
        });

        list = new ArrayList();
        for(int i = 0; i < pet_detail.length; i++){
            item = new HashMap<String, Object>();
            item.put("image_pet", pet_image[i]);
            item.put("line1", pet_detail[i][0]);
            item.put("line2", pet_detail[i][1]);
            item.put("line3", pet_detail[i][2]);
            item.put("line4", pet_detail[i][3]);
            item.put("line5", "Giá: " + pet_detail[i][4] + "đ");
            list.add(item);
        }

        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"image_pet", "line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.image_pet, R.id.line1, R.id.line2, R.id.line3, R.id.line4, R.id.line5}
                );

        ListView lst = findViewById(R.id.listViewPet);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(PetDetailActivity.this,AddOrderActivity.class);
                it.putExtra("image", pet_image[position]);
                it.putExtra("text1",  pet_detail[position][0]);
                it.putExtra("text2",  pet_detail[position][1]);
                it.putExtra("text3",  pet_detail[position][2]);
                it.putExtra("text4",  pet_detail[position][3]);
                it.putExtra("PetCost",  pet_detail[position][4]);
                startActivity(it);
            }
        });
    }
}