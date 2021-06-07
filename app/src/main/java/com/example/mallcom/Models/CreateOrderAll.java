package com.example.mallcom.Models;

import java.util.List;

public class CreateOrderAll {
//    private int account_id;
    private String  payment_method;
    private List<OrderItemRequest> orders;


//    public int getAccount_id() {
//        return account_id;
//    }

//    public void setAccount_id(int account_id) {
//        this.account_id = account_id;
//    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public List<OrderItemRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemRequest> orders) {
        this.orders = orders;
    }
}
