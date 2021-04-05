package com.example.crystalfashion.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crystalfashion.Models.ProductResponse;
import com.example.crystalfashion.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context mCtx;
    private List<ProductResponse.Product> productList;
    private String URL="https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";

    public ProductsAdapter(Context mCtx, List<ProductResponse.Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.homepage_recycler_view_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        ProductResponse.Product product_response = productList.get(position);
        Picasso.with(mCtx).load(URL+product_response.getProduct_image()).fit().into(holder.product_image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
        holder.product_name.setText(product_response.getProduct_name());
        holder.product_caption.setText(product_response.getProduct_caption());
        holder.product_price.setText("₹"+String.valueOf(product_response.getProduct_price()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_caption, product_price;
        ImageView product_image;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image=itemView.findViewById(R.id.product_ImageView);
            product_name = itemView.findViewById(R.id.Product_name_textView);
            product_caption = itemView.findViewById(R.id.Product_caption_textView);
            product_price = itemView.findViewById(R.id.Product_Price_textView);

        }
    }
}
