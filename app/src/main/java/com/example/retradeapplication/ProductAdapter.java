package com.example.retradeapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        String imagePath = product.getImage();

        if (imagePath != null && !imagePath.isEmpty()) {
            // Load the image using Glide, if the imagePath is a valid URI or URL
            Glide.with(context)
                    .load(imagePath)  // Directly use the URL string
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.productImage);

        } else {
            holder.productImage.setImageResource(R.drawable.placeholder_image);  // Default placeholder if imagePath is empty or null
        }

        holder.contactSellerButton.setOnClickListener(v -> {
            String phoneNumber = "+91 9876543210";  // Example phone number, replace with actual number from product
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        });

        holder.chatWithSellerButton.setOnClickListener(v -> {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show(); // This will show the toast
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        Button contactSellerButton, chatWithSellerButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            contactSellerButton = itemView.findViewById(R.id.contact_seller_button);
            chatWithSellerButton = itemView.findViewById(R.id.chat_with_seller_button);
        }
    }
}
