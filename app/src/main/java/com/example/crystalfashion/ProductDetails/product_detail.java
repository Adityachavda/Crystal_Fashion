package com.example.crystalfashion.ProductDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.crystalfashion.R;
import com.squareup.picasso.Picasso;

public class product_detail extends Fragment {
    public product_detail() {
        // Required empty public constructor
    }

    ImageView imageView_prd;
    String image_string;
    private String URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        imageView_prd=view.findViewById(R.id.prd_image_main);
        image_string=getArguments().getString("prd_image");
        Picasso.with(getActivity()).load(URL+image_string).into(imageView_prd);
        return view;
    }
}