package com.example.retradeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout wishlistBtn = findViewById(R.id.wishlist_button);
        wishlistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, WishlistPage.class);
            startActivity(intent);
        });

        LinearLayout clothingCategory = findViewById(R.id.clothing_category);
        clothingCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, ClothingCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout electronicsCategory = findViewById(R.id.electronics_category);
        electronicsCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, ElectronicsCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout furnitureCategory = findViewById(R.id.furniture_category);
        furnitureCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, FurnitureCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout carsCategory = findViewById(R.id.cars_category);
        carsCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CarsCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout twoWheelersCategory = findViewById(R.id.two_wheelers_category);
        twoWheelersCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, TwoWheelersCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout booksCategory = findViewById(R.id.books_category);
        booksCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, BooksCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout mobilesCategory = findViewById(R.id.mobiles_category);
        mobilesCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, MobilesCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout propertiesCategory = findViewById(R.id.properties_category);
        propertiesCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, PropertiesCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout instrumentsCategory = findViewById(R.id.instruments_category);
        instrumentsCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, InstrumentsCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout sportsEquipmentCategory = findViewById(R.id.sports_equipment_category);
        sportsEquipmentCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, SportsEquipmentCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout collectablesCategory = findViewById(R.id.collectables_category);
        collectablesCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CollectablesCategoryPage.class);
            startActivity(intent);
        });

        LinearLayout homeDecorCategory = findViewById(R.id.home_decor_category);
        homeDecorCategory.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, HomeDecorCategoryPage.class);
            startActivity(intent);
        });
    }
}