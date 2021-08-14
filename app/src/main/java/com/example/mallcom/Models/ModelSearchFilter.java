package com.example.mallcom.Models;

import java.util.List;

public class ModelSearchFilter {
    String color;
    int weight;
    int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    List<PriceModel> price;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<PriceModel> getPrice() {
        return price;
    }

    public void setPrice(List<PriceModel> price) {
        this.price = price;
    }
}

