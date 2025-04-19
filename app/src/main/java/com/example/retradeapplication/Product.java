package com.example.retradeapplication;

public class Product {

    private String name;
    private String description;
    private Double price;
    private String category;
    private String image;
    private String uid;
    private String sellerID;

    public Product() {
        // Required empty constructor for Firestore
    }

    public Product(String name, String description, Double price, String category, String image, String uid, String sellerID) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.uid = uid;
        this.sellerID = sellerID;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getSellerID() { return sellerID; }
    public void setSellerID(String sellerID) { this.sellerID = sellerID; }
}
