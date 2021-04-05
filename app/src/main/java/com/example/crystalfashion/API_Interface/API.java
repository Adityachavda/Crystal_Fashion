package com.example.crystalfashion.API_Interface;

import com.example.crystalfashion.Models.Default_Response;
import com.example.crystalfashion.Models.LoginResponse;
import com.example.crystalfashion.Models.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("user_register.php")
    Call<Default_Response> createUser(
            @Field("user_image") String user_image,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("skin_tone") String skin_tone,
            @Field("height") Float height,
            @Field("weight") Float weight,
            @Field("body_type") String body_type
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<LoginResponse> loginUser(
            @Field("opr") String opr,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<Default_Response> forgot_password(
            @Field("opr") String opr,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("select_product.php")
    Call<ProductResponse> get_products(
            @Field("opr") String opr
    );

}
