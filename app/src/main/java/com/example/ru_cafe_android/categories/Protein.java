package com.example.ru_cafe_android.categories;

/**
 * Enum object to store protein values
 * @author Namulun, modified Nov. 30, 2025
 */
public enum Protein {
    BEEF("Beef", 12.99),
    CHICKEN("Chicken", 10.99),
    SALMON("Salmon", 14.99);

    private final String name;
    private final double price;

    /**
     * Default constructor of Protein object
     * @param name Name of protein
     * @param price double price of protein
     */
    Protein(String name, double price) {
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
}

