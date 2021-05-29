package com.example.mallcom.Models;

import java.util.ArrayList;

public class ModelStagger {

    private String id;
    private String name;
    private String product1;
    private String product2;
    private String product3;
    private ArrayList<ModelSlider> modelSliderArrayList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct1() {
        return product1;
    }

    public void setProduct1(String product1) {
        this.product1 = product1;
    }

    public String getProduct2() {
        return product2;
    }

    public void setProduct2(String product2) {
        this.product2 = product2;
    }

    public String getProduct3() {
        return product3;
    }

    public void setProduct3(String product3) {
        this.product3 = product3;
    }

    public ArrayList<ModelSlider> getModelSliderArrayList() {
        return modelSliderArrayList;
    }

    public void setModelSliderArrayList(ArrayList<ModelSlider> modelSliderArrayList) {
        this.modelSliderArrayList = modelSliderArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
