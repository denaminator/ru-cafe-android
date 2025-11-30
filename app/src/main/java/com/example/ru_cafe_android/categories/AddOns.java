package com.example.ru_cafe_android.categories;

/**
 * enum to hold add-ons data
 * @author Namulun, modified Nov. 30, 2025
 */
public enum AddOns {
    CHEESE("Cheese", 1.00),
    LETTUCE("Lettuce", 0.30),
    TOMATOES("Tomatoes", 0.30),
    ONIONS("Onions", 0.30);

    private final String name;
    private final double price;

    /**
     * Constructor for AddOn
     * @param name String representation of object
     * @param price Double price value of object
     */
    AddOns(String name, double price) {
        this.name = name;
        this.price = price;
    }
    /**
     * Gets name of object
     * @return String representation of name
     */
    public String getName() { return this.name; }

    /**
     * Price of object
     * @return Double price
     */
    public double getPrice() { return this.price; }

    /**
     * Overrides string representation of object
     * @return String name
     */
    @Override
    public String toString() { return getName(); }
}
