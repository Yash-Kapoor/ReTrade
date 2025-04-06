package com.example.retradeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;

public class ClothingCategoryPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_category_page);

        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewClothing);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        loadProductsFromFirestore();

        // Back button functionality
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClothingCategoryPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        // Bottom Navigation
        LinearLayout btnHome = findViewById(R.id.home_button);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ClothingCategoryPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);
        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(ClothingCategoryPage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ClothingCategoryPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadProductsFromFirestore() {
        CollectionReference productsRef = db.collection("products");
        productsRef.whereEqualTo("category", "Clothing")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(ClothingCategoryPage.this, "Error loading products", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    productList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        Product product = doc.toObject(Product.class);
                        productList.add(product);
                        // 🔥 Debugging: Print product details
                        System.out.println("Product loaded: " + product.getName() + ", Image: " + product.getImage());
                    }

                    // 🔥 Check if no products were found
                    if (productList.isEmpty()) {
                        Toast.makeText(ClothingCategoryPage.this, "No clothing products available!", Toast.LENGTH_SHORT).show();
                    }

                    productAdapter.notifyDataSetChanged();
                });
    }
}