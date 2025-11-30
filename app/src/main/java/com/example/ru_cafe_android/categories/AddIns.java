package com.example.ru_cafe_android.categories;

/**
 * Enum to hold add-ins
 * @author Namulun, modified Nov. 30, 2025
 */
public enum AddIns {
    CREAM("Whipped Cream", 0.25),
    VANILLA("Vanilla", 0.25),
    TWO("2% Milk", 0.25),
    CARAMEL("Caramel", 0.25),
    MOCHA("Mocha", 0.25);

    private final String name;
    private final double price;

    /**
     * Constructor for AddIn
     * @param name String representation of object
     * @param price Double price value of object
     */
    AddIns(String name, double price) {
        this.name = name;
        this.price = price; }

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
