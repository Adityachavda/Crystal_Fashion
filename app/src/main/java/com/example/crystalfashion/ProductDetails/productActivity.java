package com.example.crystalfashion.ProductDetails;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalfashion.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class productActivity extends AppCompatActivity {

    ImageView imageView_prd;
    String image_string;
    String prd_name;
    String prd_caption;
    String prd_category;
    String prd_details;
    int prd_price;
    String prd_fit;
    String prd_size;
    String details;
    String prd_material;
    String prd_care;
    TextView prd_name_txt_view,prd_caption_txt_view,prd_category_txt_view,prd_price_txt_view,prd_fit_txt_view,prd_size_txt_view,prd_details_txt_view,prd_material_txt_view,prd_care_txt_view;
    Button button_WishList,button_Add_to_bag;
    private String URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        button_WishList=findViewById(R.id.add_WishList_prd_button_main);
        button_Add_to_bag=findViewById(R.id.add_WishList_Add_to_bag_button_main);
        prd_name_txt_view=findViewById(R.id.prd_detail_name_text_view);
        prd_caption_txt_view=findViewById(R.id.prd_detail_caption_text_view);
        prd_category_txt_view=findViewById(R.id.prd_detail_category_text_view);
        prd_price_txt_view=findViewById(R.id.prd_detail_price_text_view);
        prd_fit_txt_view=findViewById(R.id.prd_detail_Fit_text_view);
        prd_size_txt_view=findViewById(R.id.prd_detail_Size1_text_view);
        prd_details_txt_view=findViewById(R.id.prd_detail_det_text_view);
        prd_material_txt_view=findViewById(R.id.prd_detail_mat_text_view);
        prd_care_txt_view=findViewById(R.id.prd_detail_care_text_view);

        imageView_prd=findViewById(R.id.prd_image_main_qq);
        image_string=getIntent().getStringExtra("prd_image");

        getSupportActionBar().setTitle(getIntent().getStringExtra("prd_name"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_bar)));

        try{
            Picasso.with(productActivity.this).load(URL+image_string).noFade().into(imageView_prd, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Toast.makeText(productActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Log.e("Image_Error", String.valueOf(e));
        }
        prd_name=getIntent().getStringExtra("prd_name");
        prd_caption=getIntent().getStringExtra("prd_caption");
        prd_category=getIntent().getStringExtra("prd_category");
        prd_price=getIntent().getIntExtra("prd_price",0);
        prd_details=getIntent().getStringExtra("prd_details");
        prd_size=getIntent().getStringExtra("prd_size");
        prd_care=getIntent().getStringExtra("prd_care");
        prd_fit=getIntent().getStringExtra("prd_fit");
        prd_material=getIntent().getStringExtra("prd_material");

        prd_name_txt_view.setText(prd_name);
        prd_caption_txt_view.setText(prd_caption);
        prd_category_txt_view.setText(prd_category);
        prd_price_txt_view.setText("₹"+String.valueOf(prd_price));
        prd_fit_txt_view.setText(prd_fit);
        prd_size_txt_view.setText(prd_size);
        prd_details_txt_view.setText(prd_details);
        prd_material_txt_view.setText(prd_material);
        prd_care_txt_view.setText(prd_care);
    }
}