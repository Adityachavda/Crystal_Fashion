package com.example.crystalfashion.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crystalfashion.Models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("uid", user.getUid());
        editor.putString("user_image",user.getUser_image());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("skin_tone", user.getSkin_tone());
        editor.putFloat("height",user.getHeight());
        editor.putFloat("weight",user.getWeight());
        editor.putString("body_type", user.getBody_type());
        editor.apply();
    }

    public static User getUser() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("uid",-1),
                sharedPreferences.getString("user_image",null),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("password",null),
                sharedPreferences.getString("skin_tone",null),
                sharedPreferences.getFloat("height",-1),
                sharedPreferences.getFloat("weight",-1),
                sharedPreferences.getString("body_type",null)
        );
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("uid", -1) != -1;
    }

    public void clearUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
