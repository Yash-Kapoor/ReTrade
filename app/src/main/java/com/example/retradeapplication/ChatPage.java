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

public class ChatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        // Bottom Navigation
        LinearLayout btnHome = findViewById(R.id.home_button);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ChatPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);
        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(ChatPage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ChatPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });
    }
}