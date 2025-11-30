package com.example.ru_cafe_android.categories;

/**
 * Enum class to store each donut type and its price (all flavors are the same price)
 * @author Namulun, modified Nov. 30, 2025
 */
public enum DonutType {
    YEAST("Yeast Donut", 1.99),
    CAKE("Cake Donut", 2.19),
    HOLE("Donut Holes", 0.39),
    SEASONAL("Seasonal Donut", 2.49);

    private final String name;
    private final double price;

    /**
     * Default constructor for DonutType
     * @param name String name of donut type
     * @param price double price for donut type
     */
    DonutType(String name, double price) {
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
