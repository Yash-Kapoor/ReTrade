package com.example.retradeapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ElectronicsCategoryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_electronics_category_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.electronics_category_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ElectronicsCategoryPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnHome = findViewById(R.id.home_button);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ElectronicsCategoryPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(ElectronicsCategoryPage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ElectronicsCategoryPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });

        Button contactSellerButton1 = findViewById(R.id.contactSellerButton1);
        Button contactSellerButton2 = findViewById(R.id.contactSellerButton2);
        Button contactSellerButton3 = findViewById(R.id.contactSellerButton3);

        contactSellerButton1.setOnClickListener(v -> showContactSellerDialog("+91 9123456789"));
        contactSellerButton2.setOnClickListener(v -> showContactSellerDialog("+91 9234567890"));
        contactSellerButton3.setOnClickListener(v -> showContactSellerDialog("+91 9345678901"));

        Button wishlistButton1 = findViewById(R.id.addToWishlistButton1);
        Button wishlistButton2 = findViewById(R.id.addToWishlistButton2);
        Button wishlistButton3 = findViewById(R.id.addToWishlistButton3);

        JSONObject product1 = new JSONObject();
        try {
            product1.put("id", "1");
            product1.put("name", "Samsung Washing Machine");
            product1.put("price", "₹5500");
            product1.put("image", "@drawable/electronics_washing_machine");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        wishlistButton1.setOnClickListener(v -> addToWishlist(product1));

        JSONObject product2 = new JSONObject();
        try {
            product2.put("id", "2");
            product2.put("name", "LG Single Door Fridge");
            product2.put("price", "₹9500");
            product2.put("image", "@drawable/electronics_fridge");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        wishlistButton2.setOnClickListener(v -> addToWishlist(product2));

        JSONObject product3 = new JSONObject();
        try {
            product3.put("id", "3");
            product3.put("name", "Whirlpool Air Conditioner");
            product3.put("price", "₹15000");
            product3.put("image", "@drawable/electronics_air_conditioner");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        wishlistButton3.setOnClickListener(v -> addToWishlist(product3));
    }

    private void addToWishlist(JSONObject product) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("loggedInUserEmail", "guest");

        SharedPreferences wishlistPrefs = getSharedPreferences("Wishlist_" + userEmail, MODE_PRIVATE);
        SharedPreferences.Editor editor = wishlistPrefs.edit();

        String wishlistString = wishlistPrefs.getString("wishlistItems", "[]");

        try {
            JSONArray wishlistArray = new JSONArray(wishlistString);

            // Ensure image is stored correctly (only the filename)
            String imageName = product.getString("image").replace("@drawable/", "");
            product.put("image", imageName);

            wishlistArray.put(product);
            editor.putString("wishlistItems", wishlistArray.toString());
            editor.apply();

            Toast.makeText(this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showContactSellerDialog(String contactNumber) {
        // Create a Bottom Sheet Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.contact_seller_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Set the contact number dynamically
        TextView contactTextView = bottomSheetView.findViewById(R.id.seller_contact_number);
        contactTextView.setText(contactNumber);

        // Call Now button functionality
        Button callNowButton = bottomSheetView.findViewById(R.id.callNowButton);
        callNowButton.setOnClickListener(v -> {
            // Create an Intent to open the phone dialer with the number prefilled
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contactNumber));
            startActivity(callIntent);
        });

        // Close button functionality
        Button closeButton = bottomSheetView.findViewById(R.id.closeBottomSheet);
        closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        // Show the Bottom Sheet Dialog
        bottomSheetDialog.show();
    }

}