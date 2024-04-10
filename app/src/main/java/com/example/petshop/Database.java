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

        String qry2 = "create table pets(id int primary key, imagePet blob, namePet text, old text, sex text, color text, quantity int, price text, otype text)";
        db.execSQL(qry2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists pets");
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

    public void AddPet(int id, byte[] imagePet, String namePet, String old, String sex, String color, int quantity, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
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
            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexImagePet = cursor.getColumnIndex("imagePet");
            int columnIndexNamePet = cursor.getColumnIndex("namePet");
            int columnIndexOld = cursor.getColumnIndex("old");
            int columnIndexSex = cursor.getColumnIndex("sex");
            int columnIndexColor = cursor.getColumnIndex("color");
            int columnIndexQuantity = cursor.getColumnIndex("quantity");
            int columnIndexPrice = cursor.getColumnIndex("price");
            int columnIndexOType = cursor.getColumnIndex("otype");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(columnIndexId);
                byte[] imagePet = cursor.getBlob(columnIndexImagePet);
                String namePet = cursor.getString(columnIndexNamePet);
                String old = cursor.getString(columnIndexOld);
                String sex = cursor.getString(columnIndexSex);
                String color = cursor.getString(columnIndexColor);
                String quantity = cursor.getString(columnIndexQuantity);
                String price = cursor.getString(columnIndexPrice);
                String otype = cursor.getString(columnIndexOType);

                // Create a new Pet object from the current column data
                Pet pet = new Pet(id, imagePet, namePet, old, sex, color, quantity, price, otype);

                // Add the pet to the list
                petList.add(pet);
            }
            cursor.close();
        }
        db.close();
        return petList;
    }
}
