package com.example.crystalfashion.Models;

import com.google.gson.annotations.SerializedName;

public class Default_Response {

    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    public Default_Response(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
