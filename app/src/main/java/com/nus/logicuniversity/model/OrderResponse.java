package com.nus.logicuniversity.model;

import com.nus.logicuniversity.model.Order;

import java.util.List;

public class OrderResponse {

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
