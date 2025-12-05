package com.example.ru_cafe_android;

import com.example.ru_cafe_android.system.Order;

import java.util.ArrayList;

public class OrderListManager {
    private static OrderListManager instance;
    private ArrayList<Order> orderList;

    /**
     * Private constructor prevents instantiation outside of this file
     */
    private OrderListManager() {
        this.orderList = new ArrayList<Order>();
    }

    /**
     * Creates an instance if one was not made, returns one if it was
     * @return the only instance of this class
     */
    public static synchronized OrderListManager getInstance() {
        if (instance == null) instance = new OrderListManager();
        return instance;
    }

    public void addOrderToList(Order order) {
        this.orderList.add(order);
    }

    public void removeOrderFromList(Order order) {
        this.orderList.remove(order);
    }

    /**
     * Retrieves the current global order
     * @return Global order object
     */
    public ArrayList<Order> getOrderList() { return this.orderList; }
}
