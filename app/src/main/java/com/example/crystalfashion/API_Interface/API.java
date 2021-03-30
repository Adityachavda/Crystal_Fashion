package com.example.crystalfashion.API_Interface;

import com.example.crystalfashion.Models.Default_Response;

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
            @Field("height") Double height,
            @Field("weight") Double weight,
            @Field("body_type") String body_type
    );

}
