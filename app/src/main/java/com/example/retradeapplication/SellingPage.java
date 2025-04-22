package com.example.retradeapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.*;

public class SellingPage extends AppCompatActivity {

    private static final int SELECT_IMAGE_REQUEST_CODE = 101;
    private ImageView productImageView;
    private Uri selectedImageUri = null;
    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selling_page);

        productImageView = findViewById(R.id.click_image);
        Button selectImageBtn = findViewById(R.id.select_image_button);
        Button submitBtn = findViewById(R.id.submit_product_button);
        Spinner categorySpinner = findViewById(R.id.productCategorySpinner);

        String[] categories = {"Clothing", "Electronics", "Furniture", "Cars", "Two Wheelers", "Books", "Mobiles", "Properties", "Instruments", "Sports", "Collectables", "Home Decor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        selectImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
        });

        submitBtn.setOnClickListener(v -> {
            EditText productTitle = findViewById(R.id.productTitle);
            EditText productDescription = findViewById(R.id.productDescription);
            EditText productPrice = findViewById(R.id.productPrice);

            String title = productTitle.getText().toString().trim();
            String description = productDescription.getText().toString().trim();
            String priceStr = productPrice.getText().toString().trim();
            String selectedCategory = categorySpinner.getSelectedItem().toString();

            if (title.isEmpty() || description.isEmpty() || priceStr.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Please fill in all fields and select an image.", Toast.LENGTH_SHORT).show();
                return;
            }

            Double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price entered", Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            String sellerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            uploadImageToImgur(selectedImageUri, imageUrl -> {
                if (imageUrl == null) {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product product = new Product(title, description, price, selectedCategory, imageUrl, uid, sellerEmail);

                FirebaseFirestore.getInstance().collection("products")
                        .add(product)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show();

                            productTitle.setText("");
                            productDescription.setText("");
                            productPrice.setText("");
                            categorySpinner.setSelection(0);
                            productImageView.setImageResource(R.drawable.placeholder);
                            selectedImageUri = null;
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error adding product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            });
        });

        findViewById(R.id.backButton).setOnClickListener(v -> startActivity(new Intent(this, HomePageActivity.class)));
        findViewById(R.id.home_button).setOnClickListener(v -> startActivity(new Intent(this, HomePageActivity.class)));
        findViewById(R.id.chat_button).setOnClickListener(v -> startActivity(new Intent(this, ChatPage.class)));
        findViewById(R.id.profile_button).setOnClickListener(v -> startActivity(new Intent(this, ProfilePage.class)));
    }

    private void uploadImageToImgur(Uri imageUri, ImgurUploadCallback callback) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);
            String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            RequestBody requestBody = new FormBody.Builder()
                    .add("image", base64Image)
                    .add("type", "base64")
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .post(requestBody)
                    .addHeader("Authorization", "Client-ID 2a935282f2f67b8")
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> callback.onResult(null));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        String imageUrl = json.getJSONObject("data").getString("link");
                        runOnUiThread(() -> callback.onResult(imageUrl));
                    } catch (Exception e) {
                        runOnUiThread(() -> callback.onResult(null));
                    }
                }
            });
        } catch (Exception e) {
            callback.onResult(null);
        }
    }

    interface ImgurUploadCallback {
        void onResult(String imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            productImageView.setImageURI(selectedImageUri);
        }
    }
}
