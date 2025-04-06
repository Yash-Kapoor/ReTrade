package com.example.retradeapplication;

public class Product {
    private String category;
    private String description;
    private String image;
    private String id;
    private String name;
    private double price;
    private String sellerID;

    public Product() {}

    public Product(String category, String description, String image, String id, String name, double price, String sellerID) {
        this.category = category;
        this.description = description;
        this.image = image;
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellerID = sellerID;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSellerID() {
        return sellerID;
    }
}