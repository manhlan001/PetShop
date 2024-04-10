package com.example.petshop;

public class Pet {
    private int id;
    private byte[] imagePet;
    private String namePet;
    private String old;
    private String sex;
    private String color;
    private String quantity;
    private String price;
    private String otype;

    public Pet(int id, byte[] imagePet, String namePet, String old, String sex, String color, String quantity, String price, String otype) {
        this.id = id;
        this.imagePet = imagePet;
        this.namePet = namePet;
        this.old = old;
        this.sex = sex;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.otype = otype;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public byte[] getImagePet() {
        return imagePet;
    }

    public void setImagePet(byte[] imagePet) {
        this.imagePet = imagePet;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOtype() {
        return otype;
    }
    public void setOtype(String otype) {
        this.otype = otype;
    }
}
