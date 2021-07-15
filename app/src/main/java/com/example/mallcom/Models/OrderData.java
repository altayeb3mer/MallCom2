package com.example.mallcom.Models;

public class OrderData {
   private String state_id;
   private String product_id;
   private String amount;
   private ModelOrder_props order_props;


    public ModelOrder_props getOrder_props() {
        return order_props;
    }

    public void setOrder_props(ModelOrder_props order_props) {
        this.order_props = order_props;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
