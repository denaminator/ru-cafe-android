package com.example.ru_cafe_android.system;

import com.example.ru_cafe_android.categories.DonutType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores values for Donut and its DonutType on the menu
 * @author Namulun, modified Nov. 30, 2025
 */
public class Donut extends MenuItem{
    private DonutType donutType;
    private String flavor;

    /**
     * Constructor to create a donut object
     * @param type Type of the donut
     * @param flavor Name of flavor of the selected donut type
     */
    public Donut(DonutType type, String flavor) {
        this.donutType = type;
        this.flavor = flavor;
    }

    /**
     * Gets donut type
     * @return DonutType
     */
    public DonutType getType() { return this.donutType; }

    /**
     * Gets donut flavor
     * @return String flavor
     */
    public String getFlavor() { return this.flavor; }

    /**
     * Default constructor for donut
     */
    public Donut() {}

    /**
     * Calculates price of Donut
     * @return total price of Donut
     */
    @Override
    public double price() {
        return donutType.getPrice();
    }

    /**
     * String breakdown of Donut object
     * @return String array that contains all details of the donut
     */
    @Override
    public ArrayList<String> breakdown() {
        ArrayList<String> breakDown = new ArrayList<>();
        if (this.getType() == null) {
            breakDown.add("Donut type: [Please pick a donut type]");
            return breakDown;
        }
        breakDown.add("Type: " + this.getType().getName() + " $" + this.getType().getPrice());
        breakDown.add("Flavor: " + this.getFlavor());
        return breakDown;
    }

    /**
     * Finds whether two donut objects are equal
     * @param obj   the reference object with which to compare.
     * @return true if the two objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Donut compareDonut) {
            if (!(compareDonut.getType().equals(this.getType()))) return false;
            if (!(compareDonut.getFlavor().equals(this.getFlavor()))) return false;
        }
        return true;
    }

    /**
     * Generates custom hashcode for hash matching
     * @return integer that hashes object based on size
     */
    @Override
    public int hashCode() { return Objects.hash(this.getType()); }

    /**
     * Returns string representation of object
     * @return String with object add-ins
     */
    @Override
    public String toString() {
        return this.getType().getName() + " [" + this.getFlavor() + "]";
    }
}
