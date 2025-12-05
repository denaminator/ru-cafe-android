package com.example.ru_cafe_android;

import com.example.ru_cafe_android.system.Order;

/**
 * Singleton order manager to prevent multiple global order instances
 * @author Jay, modified 12/4/2025
 */
public final class OrderManager {
    private static OrderManager instance;
    private Order currentOrder;

    /**
     * Private constructor prevents instantiation outside of this file
     */
    private OrderManager() {
    }

    /**
     * Creates an instance if one was not made, returns one if it was
     * @return the only instance of this class
     */
    public static synchronized OrderManager getInstance() {
        if (instance == null) instance = new OrderManager();
        return instance;
    }

    /**
     * Sets the centralized order
     * @param order Order object to set to global
     */
    public void setCurrentOrder(Order order) { this.currentOrder = order; }

    /**
     * Retrieves the current global order
     * @return Global order object
     */
    public Order getCurrentOrder() { return this.currentOrder; }



}
