package com.example.petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "create table users(username text, email text, password text)";
        db.execSQL(qry1);

        String qry2 = "create table pets(imagePet blob, namePet text primary key, old text, sex text, color text, quantity int, price text, otype text)";
        db.execSQL(qry2);

        String qry3 = "create table nhanvien(name text, old text, sex text, sdt text, gmail text, diachi text, chucvu text,date text, luong text)";
        db.execSQL(qry3);

        String qry4 = "create table foodpet(name text, quantity int, price text)";
        db.execSQL(qry4);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists pets");
        db.execSQL("drop Table if exists nhanvien");
        db.execSQL("drop Table if exists foodpet");
    }

    public void Signup(String username, String email, String password){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int Login(String username, String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username = ? and password = ?", str);
        if(c.moveToFirst()){
            result = 1;
        }
        return  result;
    }

    public void AddPet(byte[] imagePet, String namePet, String old, String sex, String color, int quantity, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("imagePet", imagePet);
        cv.put("namePet", namePet);
        cv.put("old", old);
        cv.put("sex", sex);
        cv.put("color", color);
        cv.put("quantity", quantity);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("pets", null, cv);
        db.close();
    }

    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pets", null);
        if (cursor != null) {
            int columnIndexImagePet = cursor.getColumnIndex("imagePet");
            int columnIndexNamePet = cursor.getColumnIndex("namePet");
            int columnIndexOld = cursor.getColumnIndex("old");
            int columnIndexSex = cursor.getColumnIndex("sex");
            int columnIndexColor = cursor.getColumnIndex("color");
            int columnIndexQuantity = cursor.getColumnIndex("quantity");
            int columnIndexPrice = cursor.getColumnIndex("price");
            int columnIndexOType = cursor.getColumnIndex("otype");

            while (cursor.moveToNext()) {
                byte[] imagePet = cursor.getBlob(columnIndexImagePet);
                String namePet = cursor.getString(columnIndexNamePet);
                String old = cursor.getString(columnIndexOld);
                String sex = cursor.getString(columnIndexSex);
                String color = cursor.getString(columnIndexColor);
                String quantity = cursor.getString(columnIndexQuantity);
                String price = cursor.getString(columnIndexPrice);
                String otype = cursor.getString(columnIndexOType);

                // Create a new Pet object from the current column data
                Pet pet = new Pet(imagePet, namePet, old, sex, color, quantity, price, otype);

                // Add the pet to the list
                petList.add(pet);
            }
            cursor.close();
        }
        db.close();
        return petList;
    }

    public void AddNhanVien(String name, String old, String sex, String sdt, String gmail, String diachi, String chucvu,String date, String luong){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("old", old);
        cv.put("sex", sex);
        cv.put("sdt", sdt);
        cv.put("gmail", gmail);
        cv.put("diachi", diachi);
        cv.put("chucvu", chucvu);
        cv.put("date", date);
        cv.put("luong", luong);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("nhanvien", null, cv);
        db.close();
    }

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> NhanVienList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM nhanvien", null);
        if (cursor != null) {
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexOld = cursor.getColumnIndex("old");
            int columnIndexSex = cursor.getColumnIndex("sex");
            int columnIndexSDT = cursor.getColumnIndex("sdt");
            int columnIndexGmail = cursor.getColumnIndex("gmail");
            int columnIndexDiaChi = cursor.getColumnIndex("diachi");
            int columnIndexChucVu = cursor.getColumnIndex("chucvu");
            int columnIndexDate = cursor.getColumnIndex("date");
            int columnIndexLuong = cursor.getColumnIndex("luong");

            while (cursor.moveToNext()) {
                String name = cursor.getString(columnIndexName);
                String old = cursor.getString(columnIndexOld);
                String sex = cursor.getString(columnIndexSex);
                String sdt = cursor.getString(columnIndexSDT);
                String gmail = cursor.getString(columnIndexGmail);
                String diachi = cursor.getString(columnIndexDiaChi);
                String chucvu = cursor.getString(columnIndexChucVu);
                String date = cursor.getString(columnIndexDate);
                String luong = cursor.getString(columnIndexLuong);

                NhanVien NhanVien = new NhanVien(name, old, sex, sdt, gmail, diachi, chucvu, date, luong);

                NhanVienList.add(NhanVien);
            }
            cursor.close();
        }
        db.close();
        return NhanVienList;
    }

    public void AddFoodPet(String name, int quantity, float price){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("quantity", quantity);
        cv.put("price", price);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("foodpet", null, cv);
        db.close();
    }

    public ArrayList<FoodPet> getAllFoodPet() {
        ArrayList<FoodPet> FoodPetList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM foodpet", null);
        if (cursor != null) {
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexQuantity = cursor.getColumnIndex("quantity");
            int columnIndexPrice = cursor.getColumnIndex("price");

            while (cursor.moveToNext()) {
                String name = cursor.getString(columnIndexName);
                String quantity = cursor.getString(columnIndexQuantity);
                String price = cursor.getString(columnIndexPrice);

                // Create a new Pet object from the current column data
                FoodPet foodPet = new FoodPet(name, quantity, price);

                // Add the pet to the list
                FoodPetList.add(foodPet);
            }
            cursor.close();
        }
        db.close();
        return FoodPetList;
    }
}
