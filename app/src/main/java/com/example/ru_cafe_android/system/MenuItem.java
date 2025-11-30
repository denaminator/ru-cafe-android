package com.example.ru_cafe_android.system;

import java.util.ArrayList;

/**
 * Abstract class that is implemented by every menu item
 * @author Namulun, modified Nov. 30, 2025
 */
public abstract class MenuItem {
    protected int quantity;

    /**
     * Calculates the price of the menu item
     * @return double value of price
     */
    public abstract double price();

    /**
     * Gets complete breakdown of object details
     * @return ArrayList of strings containing all the details of the object
     */
    public abstract ArrayList<String> breakdown();

    /**
     * Checks whether two MenuItems are equal
     * @param obj   the reference object with which to compare.
     * @return true if they are equal, false if not
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Custom hashcode generation
     * @return Int containing custom hash
     */
    @Override
    public abstract int hashCode();

    /**
     * toString override for formatting
     * @return String value of object
     */
    @Override
    public abstract String toString();
}

