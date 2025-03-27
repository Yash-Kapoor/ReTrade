package com.example.retradeapplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class SellingPage extends AppCompatActivity {

    private static final int pic_id = 123;
    Button camera_open_id;
    ImageView click_image_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selling_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.selling_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        camera_open_id = findViewById(R.id.camera_button);
        click_image_id = findViewById(R.id.click_image);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        camera_open_id.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            }
        });

        Button submitBtn = findViewById(R.id.submit_product_button);
        submitBtn.setOnClickListener(v -> {
            EditText productTitle, productDescription, productPrice, productCategory;
            ImageView imageArea = findViewById(R.id.click_image);
            productCategory = findViewById(R.id.productCategory);
            productTitle = findViewById(R.id.productTitle);
            productDescription = findViewById(R.id.productDescription);
            productPrice = findViewById(R.id.productPrice);
            productCategory.setText("");
            productTitle.setText("");
            productDescription.setText("");
            productPrice.setText("");
            imageArea.setImageResource(R.drawable.placeholder);
            imageArea.setAdjustViewBounds(true);
            imageArea.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageArea.setBackgroundColor(0);
            Toast.makeText(this, "Product submitted successfully.", Toast.LENGTH_SHORT).show();
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SellingPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnHome = findViewById(R.id.home_button);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(SellingPage.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        LinearLayout btnProfile = findViewById(R.id.profile_button);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(SellingPage.this, ProfilePage.class);
            startActivity(intent);
            finish();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            click_image_id.setImageBitmap(photo);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /* Permission granted */
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show();
            }
        }
    }
}