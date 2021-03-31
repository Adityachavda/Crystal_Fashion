package com.example.crystalfashion.Models;

public class User{
    public int uid;
    public String user_image;
    public String username;
    public String email;
    public String password;
    public String skin_tone;
    public Float height;
    public Float weight;
    public String body_type;

    public User(int uid, String user_image, String username, String email, String password, String skin_tone, Float height, Float weight, String body_type) {
        this.uid = uid;
        this.user_image = user_image;
        this.username = username;
        this.email = email;
        this.password = password;
        this.skin_tone = skin_tone;
        this.height = height;
        this.weight = weight;
        this.body_type = body_type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSkin_tone() {
        return skin_tone;
    }

    public void setSkin_tone(String skin_tone) {
        this.skin_tone = skin_tone;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }
}
