package com.example.mallcom.Models;

import java.util.List;

public class SearchHallModel {
    String search;
    String sort;
    int price_from;
    int price_to;
    int rate;
    String color;


    public int getPrice_from() {
        return price_from;
    }

    public void setPrice_from(int price_from) {
        this.price_from = price_from;
    }

    public int getPrice_to() {
        return price_to;
    }

    public void setPrice_to(int price_to) {
        this.price_to = price_to;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
