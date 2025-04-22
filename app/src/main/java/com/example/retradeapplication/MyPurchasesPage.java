package com.example.retradeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyPurchasesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_purchases_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_purchases_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyPurchasesPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnHome = findViewById(R.id.home_button);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MyPurchasesPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(MyPurchasesPage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnChat = findViewById(R.id.chat_button);

        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(MyPurchasesPage.this, ChatPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MyPurchasesPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });
    }
}