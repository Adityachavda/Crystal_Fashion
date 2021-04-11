package com.example.crystalfashion.HomePage.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.Models.ProductResponse;
import com.example.crystalfashion.ProductDetails.productActivity;
import com.example.crystalfashion.ProductDetails.product_detail;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Utils.ProductsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    RecyclerView recyclerView;
    List<ProductResponse.Product> productList;
    product_detail product_detail;
    FragmentTransaction fragmentTransaction;
    FragmentManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        recyclerView = root.findViewById(R.id.recyclerView_homePage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        Call<ProductResponse> productsCall = API_Client.getInstance().getApi().get_products("selectproducts");

        productsCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();
                productList = productResponse.getProduct();
                recyclerView.setAdapter(new ProductsAdapter(getActivity(), productList,onProductClickListener));

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    ProductsAdapter.OnProductClickListener onProductClickListener = new ProductsAdapter.OnProductClickListener() {
        @Override
        public void onProductClick(Integer position, int p_id, String prd_image, String prd_name, int prd_price, String prd_details, String prd_category, String prd_caption, String prd_material, String prd_size, String prd_care, String prd_fit) {
            Bundle bundle=new Bundle();
            bundle.putString("prd_image",prd_image);
            Intent intent_prd = new Intent(getActivity(), productActivity.class);
            intent_prd.putExtra("prd_id",p_id);
            intent_prd.putExtra("prd_image",prd_image);
            intent_prd.putExtra("prd_name",prd_name);
            intent_prd.putExtra("prd_price",prd_price);
            intent_prd.putExtra("prd_details",prd_details);
            intent_prd.putExtra("prd_caption",prd_caption);
            intent_prd.putExtra("prd_category",prd_category);
            intent_prd.putExtra("prd_material",prd_material);
            intent_prd.putExtra("prd_size",prd_size);
            intent_prd.putExtra("prd_care",prd_care);
            intent_prd.putExtra("prd_fit",prd_fit);
            startActivity(intent_prd);
        }
    };
}