package com.example.retradeapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductModel> wishlistItems;

    public WishlistAdapter(Context context, ArrayList<ProductModel> wishlistItems) {
        this.context = context;
        this.wishlistItems = wishlistItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = wishlistItems.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());

        int imageResId = context.getResources().getIdentifier(
                product.getImageResource(), "drawable", context.getPackageName()
        );

        // Set the product image
        if (imageResId != 0) {
            holder.productImage.setImageResource(imageResId);
        } else {
            holder.productImage.setImageResource(R.drawable.placeholder_image);
        }

        // ✅ Fix: Remove item and persist changes
        holder.removeButton.setOnClickListener(v -> {
            removeFromWishlist(position);
        });
    }

    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    // ✅ Fix: Properly remove from Wishlist and persist changes
    private void removeFromWishlist(int position) {
        wishlistItems.remove(position);  // Remove from UI
        saveWishlist();  // Save the updated list
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, wishlistItems.size());
    }

    private void saveWishlist() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("loggedInUserEmail", "guest");

        SharedPreferences wishlistPrefs = context.getSharedPreferences("Wishlist_" + userEmail, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = wishlistPrefs.edit();

        JSONArray updatedWishlistArray = new JSONArray();
        for (ProductModel item : wishlistItems) {
            JSONObject product = new JSONObject();
            try {
                product.put("id", item.getId());
                product.put("name", item.getName());
                product.put("price", item.getPrice());
                product.put("image", item.getImageResource());
                updatedWishlistArray.put(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString("wishlistItems", updatedWishlistArray.toString());
        editor.apply();
    }

}