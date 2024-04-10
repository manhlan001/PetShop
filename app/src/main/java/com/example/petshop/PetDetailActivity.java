package com.example.petshop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.google.android.material.transition.Hold;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PetDetailActivity extends AppCompatActivity {
    TextView tv;
    Button btn_back, btn_addPet;
    String pet_title;
    ArrayList list, list_cat, list_dog, list_fish;
    HashMap<String, Object> item;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        tv = findViewById(R.id.titlePetDetailTop);
        btn_back = findViewById(R.id.buttonPetBack);
        btn_addPet = findViewById(R.id.buttonAddPet);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        list_cat = new ArrayList();
        list_dog = new ArrayList();
        list_fish = new ArrayList();

        // Tạo đối tượng Database
        Database database = new Database(this, "PetShop", null, 1);

        // Lấy danh sách tất cả các động vật cưng từ cơ sở dữ liệu
        ArrayList<Pet> petList = database.getAllPets();

        // Xử lý danh sách để tạo pet_detail và pet_image
        for (Pet pet : petList) {
            String otype = pet.getOtype();
            if ("Mèo".equals(otype)) {
                item = new HashMap<String, Object>();
                item.put("id", pet.getId());
                item.put("image_pet", pet.getImagePet());
                item.put("line1", "Loại " + otype + ": " + pet.getNamePet());
                item.put("line2", "Tuổi: " + pet.getOld());
                item.put("line3", "Giới tính: " + pet.getSex());
                item.put("line4", "Màu sắc: " + pet.getColor());
                item.put("line5", "Số lượng: " + pet.getQuantity());
                item.put("line6", "Giá: " + String.valueOf(pet.getPrice()) + "đ");
                list_cat.add(item);
            } else if ("Chó".equals(otype)) {
                item = new HashMap<String, Object>();
                item.put("id", pet.getId());
                item.put("image_pet", pet.getImagePet());
                item.put("line1", "Loại " + otype + ": " + pet.getNamePet());
                item.put("line2", "Tuổi: " + pet.getOld());
                item.put("line3", "Giới tính: " + pet.getSex());
                item.put("line4", "Màu sắc: " + pet.getColor());
                item.put("line5", "Số lượng: " + pet.getQuantity());
                item.put("line6", "Giá: " + String.valueOf(pet.getPrice()) + "đ");
                list_dog.add(item);
            } else if ("Cá".equals(otype)) {
                item = new HashMap<String, Object>();
                item.put("id", pet.getId());
                item.put("image_pet", pet.getImagePet());
                item.put("line1", "Loại " + otype + ": " + pet.getNamePet());
                item.put("line2", "Tuổi: " + pet.getOld());
                item.put("line3", "Giới tính: " + pet.getSex());
                item.put("line4", "Màu sắc: " + pet.getColor());
                item.put("line5", "Số lượng: " + pet.getQuantity());
                item.put("line6", "Giá: " + String.valueOf(pet.getPrice()) + "đ");
                list_fish.add(item);
            }
        }

        list = new ArrayList();

        if (title.compareTo("Mèo") == 0) {
            list = list_cat;
            pet_title = "Mèo";
        } else if (title.compareTo("Chó") == 0) {
            list = list_dog;
            pet_title = "Chó";
        } else {
            list= list_fish;
            pet_title = "Cá";
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetDetailActivity.this, PetActivity.class));
            }
        });

        if (list.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng thêm các loại thú cưng cho cửa hàng", Toast.LENGTH_LONG).show();
        } else {
            CustomAdapter sa = new CustomAdapter(this, R.layout.multi_lines, list);

            ListView lst = findViewById(R.id.listViewPet);
            lst.setAdapter(sa);

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(PetDetailActivity.this, Delete_Custom_Activity.class);
                    it.putExtra("pet_title", pet_title);
                    HashMap<String, Object> selectedItem = (HashMap<String, Object>) parent.getItemAtPosition(position);
                    it.putExtra("id", (int) selectedItem.get("id"));
                    it.putExtra("image", (byte[]) selectedItem.get("image_pet"));
                    it.putExtra("text1", selectedItem.get("line1").toString());
                    it.putExtra("text2", selectedItem.get("line2").toString());
                    it.putExtra("text3", selectedItem.get("line3").toString());
                    it.putExtra("text4", selectedItem.get("line4").toString());
                    it.putExtra("text5", selectedItem.get("line5").toString());
                    it.putExtra("PetPrice", selectedItem.get("line6").toString());
                    startActivity(it);
                }
            });
        }

        btn_addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PetDetailActivity.this, AddPetActivity.class);
                it.putExtra("pet_title", pet_title);
                startActivity(it);
            }
        });
    }

    private Bitmap byteToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    private class CustomAdapter extends BaseAdapter{
        private Context context;
        private int layout;
        private ArrayList list = new ArrayList();

        public CustomAdapter(Context context, int layout, ArrayList list){
            this.context = context;
            this.layout = layout;
            this.list = list;
        }

        private class ViewHolder{
            ImageView image_pet;
            TextView line1, line2, line3, line4, line5, line6;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, null);
                holder.image_pet = convertView.findViewById(R.id.image_pet);
                holder.line1 = convertView.findViewById(R.id.line1);
                holder.line2 = convertView.findViewById(R.id.line2);
                holder.line3 = convertView.findViewById(R.id.line3);
                holder.line4 = convertView.findViewById(R.id.line4);
                holder.line5 = convertView.findViewById(R.id.line5);
                holder.line6 = convertView.findViewById(R.id.line6);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, Object> item = (HashMap<String, Object>) list.get(position);
            byte[] imageByte = (byte[]) item.get("image_pet");
            Bitmap bitmap = byteToBitmap(imageByte);
            holder.image_pet.setImageBitmap(bitmap);
            holder.line1.setText(item.get("line1").toString());
            holder.line2.setText(item.get("line2").toString());
            holder.line3.setText(item.get("line3").toString());
            holder.line4.setText(item.get("line4").toString());
            holder.line5.setText(item.get("line5").toString());
            holder.line6.setText(item.get("line6").toString());

            return convertView;
        }
    }
}