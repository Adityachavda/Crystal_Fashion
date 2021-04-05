package com.example.crystalfashion.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("product")
    @Expose
    public List<Product> product = null;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Product> getProduct() {
        return product;
    }

    public class Product{
        @SerializedName("Product_id")
        public int product_id;
        @SerializedName("Product_name")
        public String product_name;
        @SerializedName("Product_category")
        public String product_category;
        @SerializedName("Product_image")
        public String product_image;
        @SerializedName("Product_price")
        public int product_price;
        @SerializedName("Product_caption")
        public String product_caption;
        @SerializedName("Product_detail")
        public String product_detail;
        @SerializedName("Product_material")
        public String product_material;
        @SerializedName("Product_care")
        public String product_care;
        @SerializedName("Product_size")
        public String product_size;
        @SerializedName("Product_fit")
        public String product_fit;
        @SerializedName("Stock")
        public int stock;
        @SerializedName("Created")
        public String created;

        public String getProduct_image() {
            return product_image;
        }

        @SerializedName("Updated")
        public String updated;

        public int getProduct_id() {
            return product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getProduct_category() {
            return product_category;
        }

        public int getProduct_price() {
            return product_price;
        }

        public String getProduct_caption() {
            return product_caption;
        }

        public String getProduct_detail() {
            return product_detail;
        }

        public String getProduct_material() {
            return product_material;
        }

        public String getProduct_care() {
            return product_care;
        }

        public String getProduct_size() {
            return product_size;
        }

        public String getProduct_fit() {
            return product_fit;
        }

        public int getStock() {
            return stock;
        }

        public String getCreated() {
            return created;
        }

        public String getUpdated() {
            return updated;
        }
    }

}
