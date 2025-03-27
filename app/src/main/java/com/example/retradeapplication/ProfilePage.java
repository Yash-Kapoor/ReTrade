package com.example.retradeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout btnHome = findViewById(R.id.home_button);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnSell = findViewById(R.id.sell_button);

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, SellingPage.class);
            startActivity(intent);
            finish();
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(ProfilePage.this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        TextView userNameTextView = findViewById(R.id.user_name);
        if (user != null) {
            String userName = user.getDisplayName();
            if (userName != null) {
                userNameTextView.setText(userName);
            }
        }

        MaterialButton logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(v -> logoutUser());

        MaterialButton termsButton = findViewById(R.id.termsAndConditions_button);
        termsButton.setOnClickListener(v -> {
            TermsConditionsBottomSheet bottomSheet = new TermsConditionsBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), "TermsConditionsBottomSheet");
        });

        MaterialButton myPurchasesBtn = findViewById(R.id.myPurchases_button);
        myPurchasesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, MyPurchasesPage.class);
            startActivity(intent);
        });

        MaterialButton editInformationBtn = findViewById(R.id.editInformation_button);
        editInformationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, EditInformationPage.class);
            startActivity(intent);
        });

        MaterialButton wishlistBtn = findViewById(R.id.wishlist_button);
        wishlistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, WishlistPage.class);
            startActivity(intent);
        });

    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ProfilePage.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}