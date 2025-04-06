package com.example.retradeapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context context;
    private final List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;

        Log.d("ProductAdapter", "Total Products: " + productList.size());
        for (Product p : productList) {
            Log.d("ProductAdapter", "Product in Adapter: " + p.getName() + " - " + p.getCategory());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("₹" + product.getPrice());

        // 🔥 Check if the image is a URL (Firebase Storage) or a local drawable name
        String imageUrl = product.getImage();
        if (imageUrl != null && imageUrl.startsWith("http")) {
            // Load image from Firebase URL
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.productImage);
        } else {
            // Load from drawable resources
            int imageResourceId = getImageResourceId(imageUrl, context);
            if (imageResourceId != 0) {
                Glide.with(context)
                        .load(imageResourceId)
                        .placeholder(R.drawable.placeholder_image)
                        .into(holder.productImage);
            } else {
                holder.productImage.setImageResource(R.drawable.placeholder_image);
            }
        }

        // 🔥 Contact Seller Button - Opens Dialer with a random number (if missing)
        holder.contactSellerButton.setOnClickListener(v -> {
            String phoneNumber;
            phoneNumber = "+91 9876543210";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // 🔥 Helper method to get drawable resource ID
    private int getImageResourceId(String imageName, Context context) {
        if (imageName == null || imageName.isEmpty()) return R.drawable.placeholder_image;

        // 🔥 Try finding the resource without adding an extension
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (resId == 0) {
            System.out.println("Image not found: " + imageName);
            return R.drawable.placeholder_image; // Default image if not found
        }

        return resId;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        Button contactSellerButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            contactSellerButton = itemView.findViewById(R.id.contact_seller_button);
        }
    }
}
