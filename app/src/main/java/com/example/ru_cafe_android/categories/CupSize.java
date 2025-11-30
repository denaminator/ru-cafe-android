package com.example.ru_cafe_android.categories;

/**
 * Enum class to store the cupsize values and prices for coffee
 * @author Namulun, modified Nov. 30, 2025
 */

public enum CupSize {
    SHORT("Short", 2.39),
    TALL("Tall", 2.99),
    GRANDE("Grande", 3.59),
    VENTI("Venti", 4.19);

    private final double price;
    private final String name;

    /**
     * Constructor for CupSize object
     * @param name Name of size
     * @param price price
     */
    CupSize(String name, double price) {
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
