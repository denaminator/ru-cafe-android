package com.example.ru_cafe_android.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Order class to keep track of menu items in an order
 * @author Namulun, modified Nov. 30, 2025
 */
public class Order {
    private static final AtomicLong sequence = new AtomicLong(System.currentTimeMillis() / 1000);
    private final int orderNumber;
    protected ArrayList<MenuItem> orderItems;

    /**
     * Order constructor
     */
    public Order() {
        this.orderNumber = createOrderNumber();
        this.orderItems = new ArrayList<>();
    }

    /**
     * Parametrized constructor for deep copy creation
     * @param orderNumber orderNumber of current Order instance
     */
    private Order(int orderNumber) {this.orderNumber = orderNumber;}

    /**
     * Makes a deep copy of the order
     * @return Order with same information, different memory addressing locations
     */
    public Order deepCopy() {
        Order dupe = new Order(this.orderNumber);
        dupe.orderItems = new ArrayList<>();
        for (MenuItem item: this.orderItems) {dupe.orderItems.add(item);}
        return dupe;
    }

    /**
     * Gets generated order number
     * @return int order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets list of items in order
     * @return array-list of menu items
     */
    public ArrayList<MenuItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Adds a menu item to the order
     * @param item menu item to add
     */
    public void addOrderItem(MenuItem item) {
        this.orderItems.add(item);
    }

    /**
     * Generate an order number
     * @return integer order number
     */
    private int createOrderNumber() { return (int) sequence.incrementAndGet(); }

    /**
     * Calculates sum of all menu items in the order
     * @return double value containing the order price
     */
    public double orderPrice() {
        double orderPrice = 0;
        for (MenuItem item: orderItems) orderPrice += item.price();
        return orderPrice;
    }

    /**
     * Breaks down each item in the order
     * @return list of strings of the breakdown
     */
    public ArrayList<String> breakdown() {
        ArrayList<String> breakDown = new ArrayList<>();
        Map<MenuItem, Integer> orderMap = new LinkedHashMap<>();

        for (MenuItem item : orderItems) {
            orderMap.put(item, orderMap.getOrDefault(item, 0) + 1);
        }

        for (Map.Entry<MenuItem, Integer> entry : orderMap.entrySet()) {
            String line = "[" + entry.getValue() + "]" + "-" + entry.getKey().toString();
            breakDown.add(line);
        }

        return breakDown;
    }

    /**
     * Removes an item from the list given a string from the order breakdown(used in ui)
     * @param breakDownString Breakdown string of the item that will be removed
     */
    public void removeItemFromBreakDownString(String breakDownString) {
        String[] keyValuePair = breakDownString.strip().split("-", 2);
        if (keyValuePair.length != 2) return; // safety check
        String key = keyValuePair[1].strip();
        keyValuePair[0] = keyValuePair[0].replace("[", "").replace("]", "");
        int value = Integer.parseInt(keyValuePair[0]);
        int removed = 0;
        Iterator<MenuItem> iter = orderItems.iterator();
        while (iter.hasNext() && removed < value) {
            MenuItem item = iter.next();
            if (item.toString().equals(key)) {
                iter.remove();
                removed++;
            }
        }
    }

    /**
     * Overrides default toString method for file output writing
     * @return String containing the main details of the order
     */
    @Override
    public String toString() {
        String s = new String("\nOrder no: " + this.getOrderNumber() + "\nprice: " + String.format("%.2f",
                ((this.orderPrice()*0.06625) + this.orderPrice())
        ));
        return s;
    }
}
