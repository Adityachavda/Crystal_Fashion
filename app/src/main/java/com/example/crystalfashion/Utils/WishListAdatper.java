package com.example.crystalfashion.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.crystalfashion.API_Interface.API;
import com.example.crystalfashion.HomePage.HomePage;
import com.example.crystalfashion.Models.Default_Response;
import com.example.crystalfashion.Models.ProductResponse;
import com.example.crystalfashion.Models.WishListResponse;
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

public class WishListAdatper extends RecyclerView.Adapter<WishListAdatper.WishListViewHolder> {

    private Context mCtx;
    private List<WishListResponse.Product> productList;
    private String URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";
    String ThID = "0";

    public WishListAdatper(Context mCtx, List<WishListResponse.Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public WishListAdatper.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.wishlist_recycler_view_layout, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdatper.WishListViewHolder holder, int position) {
        getThirdPartyID();
        WishListResponse.Product product_response = productList.get(position);
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
        holder.whishList_cheCheckBox.setChecked(true);
        holder.whishList_cheCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.whishList_cheCheckBox.isChecked()) {
                    if (!ThID.equals("0") && SharedPrefManager.getUser().uid==-1) {
                        Call<Default_Response> default_responseCall=API_Client.getInstance().getApi().delete_wished_productsTh("delFavProductTh",product_response.getProduct_id(),ThID);
                        default_responseCall.enqueue(new retrofit2.Callback<Default_Response>() {
                            @Override
                            public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
                                Toast.makeText(mCtx, product_response.getProduct_caption() + " Removed", Toast.LENGTH_SHORT).show();
                                productList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                if(productList.isEmpty()){
                                    Intent GotoHome=new Intent(mCtx, HomePage.class);
                                    mCtx.startActivity(GotoHome);
                                }
                            }

                            @Override
                            public void onFailure(Call<Default_Response> call, Throwable t) {

                            }
                        });
                    } else {

                        Call<Default_Response> default_responseCall = API_Client.getInstance().getApi().delete_wished_products("delFavProduct", SharedPrefManager.getUser().uid, product_response.getProduct_id());
                        default_responseCall.enqueue(new retrofit2.Callback<Default_Response>() {
                            @Override
                            public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
                                Toast.makeText(mCtx, product_response.getProduct_caption() + " Removed", Toast.LENGTH_SHORT).show();
                                productList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                if(productList.isEmpty()){
                                    Intent GotoHome=new Intent(mCtx, HomePage.class);
                                    mCtx.startActivity(GotoHome);
                                }
                            }

                            @Override
                            public void onFailure(Call<Default_Response> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_caption, product_price;
        ImageView product_image;
        CheckBox whishList_cheCheckBox;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.wishlist_product_ImageView);
            product_name = itemView.findViewById(R.id.wishlist_product_name_textView);
            product_caption = itemView.findViewById(R.id.wishlist_Product_caption_textView);
            product_price = itemView.findViewById(R.id.wishlist_Product_Price_textView);
            whishList_cheCheckBox = itemView.findViewById(R.id.enabled_Product_WishList);

        }
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
}
