package com.example.retradeapplication;

public class ProductModel {
    String id;
    String name;
    String price;
    String imageResource;

    public ProductModel(String id, String name, String price, String imageResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageResource() { return imageResource; }
}
