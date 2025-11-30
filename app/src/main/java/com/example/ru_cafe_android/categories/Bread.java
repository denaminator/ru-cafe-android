package com.example.ru_cafe_android.categories;

/**
 * Enum to hold bread values
 * @author Namulun, modified Nov. 30, 2025
 */
public enum Bread {
    BAGEL("Bagel"),
    WHEAT("Wheat"),
    SOURDOUGH("Sourdough");

    private final String name;

    /**
     * Constructor for bread enum object
     * @param name Name of bread
     */
    Bread(String name) {
        this.name = name;
    }

    /**
     * Gets string representation of bread name
     * @return String name
     */
    public String getName() { return this.name; }
}
