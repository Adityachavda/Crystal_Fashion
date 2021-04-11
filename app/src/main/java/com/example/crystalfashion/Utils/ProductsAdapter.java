package com.example.crystalfashion.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.Models.Default_Response;
import com.example.crystalfashion.Models.ProductResponse;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Storage.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context mCtx;
    private List<ProductResponse.Product> productList;
    private OnProductClickListener moOnProductClickListener;
    private String URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";
    int UserId;
    String ThID = "0";

    public ProductsAdapter(Context mCtx, List<ProductResponse.Product> productList, OnProductClickListener moOnProductClickListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.moOnProductClickListener = moOnProductClickListener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.homepage_recycler_view_layout, parent, false);
        return new ProductsViewHolder(view, moOnProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        getThirdPartyID();
        ProductResponse.Product product_response = productList.get(position);
        Picasso.with(mCtx).load(URL + product_response.getProduct_image()).fit().into(holder.product_image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
        holder.product_name.setText(product_response.getProduct_name());
        holder.product_caption.setText(product_response.getProduct_caption());
        holder.product_price.setText("₹" + String.valueOf(product_response.getProduct_price()));
        holder.whishList_cheCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.whishList_cheCheckBox.isChecked()) {
                    Call<Default_Response> default_responseCall = API_Client.getInstance().getApi().add_wish("AddToFav", product_response.getProduct_id(), SharedPrefManager.getUser().uid, ThID);
                    default_responseCall.enqueue(new retrofit2.Callback<Default_Response>() {
                        @Override
                        public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
//                            Toast.makeText(mCtx, "Added To Favourites", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Default_Response> call, Throwable t) {
//                            Toast.makeText(mCtx, "Unable to add to Favourite", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (!holder.whishList_cheCheckBox.isChecked()) {
                    Call<Default_Response> default_responseCall = API_Client.getInstance().getApi().add_wish("DelFav", product_response.getProduct_id(), getUserId(), ThID);
                    default_responseCall.enqueue(new retrofit2.Callback<Default_Response>() {
                        @Override
                        public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
//                            Toast.makeText(mCtx, "Removed From Favourites", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Default_Response> call, Throwable t) {
//                            Toast.makeText(mCtx, "Unable to Remove From Favourites", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private int getUserId() {
        UserId = SharedPrefManager.getUser().uid;
        if (UserId == -1) {
            UserId = 0;
        }
        return UserId;
    }

    private void getThirdPartyID() {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            ThID = object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mCtx);
        if (account != null) {
            String personId = account.getId();
            ThID = personId;
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product_name, product_caption, product_price;
        ImageView product_image;
        CheckBox whishList_cheCheckBox;
        OnProductClickListener onProductClickListener;

        public ProductsViewHolder(@NonNull View itemView, OnProductClickListener moOnProductClickListener1) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_ImageView);
            product_name = itemView.findViewById(R.id.Product_name_textView);
            product_caption = itemView.findViewById(R.id.Product_caption_textView);
            product_price = itemView.findViewById(R.id.Product_Price_textView);
            whishList_cheCheckBox = itemView.findViewById(R.id.Product_WishList);

            this.onProductClickListener = moOnProductClickListener1;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            ProductResponse.Product product_response = productList.get(getAdapterPosition());
            int P_id = product_response.getProduct_id();
            String Prd_image = product_response.getProduct_image();
            String Prd_name = product_response.getProduct_name();
            int Prd_price = product_response.getProduct_price();
            String Prd_Details = product_response.getProduct_detail();
            String Prd_caption = product_response.getProduct_caption();
            String Prd_category = product_response.getProduct_category();
            String Prd_material = product_response.getProduct_material();
            String Prd_size = product_response.getProduct_size();
            String Prd_care = product_response.getProduct_care();
            String Prd_fit = product_response.getProduct_fit();
            onProductClickListener.onProductClick(getAdapterPosition(), P_id, Prd_image, Prd_name, Prd_price, Prd_Details, Prd_category, Prd_caption, Prd_material, Prd_size, Prd_care, Prd_fit);
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Integer position, int p_id, String prd_image, String prd_name, int prd_price, String prd_details, String prd_category, String prd_caption, String prd_material, String prd_size, String prd_care, String prd_fit);
    }

}
