package com.example.crystalfashion.HomePage.ui.MyWishList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.HomePage.HomePage;
import com.example.crystalfashion.Models.WishListResponse;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Storage.SharedPrefManager;
import com.example.crystalfashion.Utils.WishListAdatper;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyWishlistFragment extends Fragment {
    public MyWishlistFragment() {
    }

    String ThID;
    RecyclerView recyclerView;
    List<WishListResponse.Product> productList;
    private final int interval = 2000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mywishlist, container, false);

        recyclerView=root.findViewById(R.id.wishList_Recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getThirdPartyID();

        if(SharedPrefManager.getUser().uid==-1){
            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            progressDialog.setMessage("Please Wait..");
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Call<WishListResponse> wishListResponseCall = API_Client.getInstance().getApi().get_wished_productsTh("selectfavproductsTh",ThID);
                    wishListResponseCall.enqueue(new Callback<WishListResponse>() {
                        @Override
                        public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                            WishListResponse wishListResponse=response.body();
                            productList=wishListResponse.getProduct();
                            if(productList.isEmpty()){
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "You Don't Have Any Favourites", Toast.LENGTH_SHORT).show();
                                Intent gotoHome=new Intent(getActivity(), HomePage.class);
                                startActivity(gotoHome);
                            }else {
                                progressDialog.dismiss();
                                recyclerView.setAdapter(new WishListAdatper(getActivity(), productList));
                            }
                        }

                        @Override
                        public void onFailure(Call<WishListResponse> call, Throwable t) {

                        }
                    });
                }
            };
            handler.postDelayed(runnable,interval);
        }
        else {
            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            Call<WishListResponse> wishListResponseCall = API_Client.getInstance().getApi().get_wished_products("selectfavproducts", SharedPrefManager.getUser().uid);
            wishListResponseCall.enqueue(new Callback<WishListResponse>() {
                @Override
                public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                    WishListResponse wishListResponse = response.body();
                    productList = wishListResponse.getProduct();
                    if(productList.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "You Don't Have Any Favourites", Toast.LENGTH_SHORT).show();
                        Intent gotoHome=new Intent(getActivity(), HomePage.class);
                        startActivity(gotoHome);
                    }else {
                        progressDialog.dismiss();
                        recyclerView.setAdapter(new WishListAdatper(getActivity(), productList));
                    }
                }

                @Override
                public void onFailure(Call<WishListResponse> call, Throwable t) {

                }
            });
        }
        return root;
    }

    private  void getThirdPartyID() {
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
                            Log.d("Faceook Id:",ThID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            String personId = account.getId();
            ThID = personId;
        } else {
        }
    }
}