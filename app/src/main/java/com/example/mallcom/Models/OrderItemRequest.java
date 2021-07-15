package com.example.mallcom.Models;

public class OrderItemRequest {

    private int state_id;
    private int product_id;
    private int amount;
    private ModelOrder_props order_props;


    public ModelOrder_props getOrder_props() {
        return order_props;
    }

    public void setOrder_props(ModelOrder_props order_props) {
        this.order_props = order_props;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
