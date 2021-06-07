package com.example.mallcom.Models;

import com.google.gson.annotations.SerializedName;

public class CreateOrderRequest {
    @SerializedName("data")
    private String data;

    public CreateOrderRequest(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
