package com.example.crystalfashion.HomePage.ui.MyWishList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    String ThID="0";
    RecyclerView recyclerView;
    List<WishListResponse.Product> productList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mywishlist, container, false);
        getThirdPartyID();
        recyclerView=root.findViewById(R.id.wishList_Recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        if(!ThID.equals("0")){
            Call<WishListResponse> wishListResponseCall = API_Client.getInstance().getApi().get_wished_productsTh("selectfavproductsTh",ThID);
            wishListResponseCall.enqueue(new Callback<WishListResponse>() {
                @Override
                public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                    WishListResponse wishListResponse=response.body();
                    productList=wishListResponse.getProduct();
                    if(productList.isEmpty()){
                        Toast.makeText(getActivity(), "You Don't Have Any Favourites", Toast.LENGTH_SHORT).show();
                        Intent gotoHome=new Intent(getActivity(), HomePage.class);
                        startActivity(gotoHome);
                    }else {
                        recyclerView.setAdapter(new WishListAdatper(getActivity(), productList));
                    }
                }

                @Override
                public void onFailure(Call<WishListResponse> call, Throwable t) {

                }
            });
        }
        else {
            Call<WishListResponse> wishListResponseCall = API_Client.getInstance().getApi().get_wished_products("selectfavproducts", SharedPrefManager.getUser().uid);
            wishListResponseCall.enqueue(new Callback<WishListResponse>() {
                @Override
                public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                    WishListResponse wishListResponse = response.body();
                    productList = wishListResponse.getProduct();
                    if(productList.isEmpty()){
                        Toast.makeText(getActivity(), "You Don't Have Any Favourites", Toast.LENGTH_SHORT).show();
                        Intent gotoHome=new Intent(getActivity(), HomePage.class);
                        startActivity(gotoHome);
                    }else {
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

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (account != null) {
            String personId = account.getId();
            ThID = personId;
        } else {
        }
    }
}