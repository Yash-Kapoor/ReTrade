package com.example.retradeapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishlistPage extends AppCompatActivity {

    private WishlistAdapter wishlistAdapter;
    private ArrayList<ProductModel> wishlistItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wishlist_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(WishlistPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnHome = findViewById(R.id.home_button);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(WishlistPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(WishlistPage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(WishlistPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });

        RecyclerView wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistItems = new ArrayList<>();
        wishlistAdapter = new WishlistAdapter(this, wishlistItems);
        wishlistRecyclerView.setAdapter(wishlistAdapter);

        loadWishlistItems();
    }

    private void loadWishlistItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("loggedInUserEmail", "guest"); // Default: "guest"

        SharedPreferences wishlistPrefs = getSharedPreferences("Wishlist_" + userEmail, MODE_PRIVATE);
        String wishlistString = wishlistPrefs.getString("wishlistItems", "[]");

        wishlistItems.clear();

        try {
            JSONArray wishlistArray = new JSONArray(wishlistString);
            for (int i = 0; i < wishlistArray.length(); i++) {
                JSONObject product = wishlistArray.getJSONObject(i);
                ProductModel item = new ProductModel(
                        product.getString("id"),
                        product.getString("name"),
                        product.getString("price"),
                        product.getString("image")
                );
                wishlistItems.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        wishlistAdapter.notifyDataSetChanged();
    }


    private void showEmptyWishlistMessage() {
        TextView emptyMessage = findViewById(R.id.emptyWishlistMessage);
        emptyMessage.setVisibility(View.VISIBLE);
    }
}