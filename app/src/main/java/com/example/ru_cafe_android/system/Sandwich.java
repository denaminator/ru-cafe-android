package com.example.ru_cafe_android.system;

import com.example.ru_cafe_android.categories.AddOns;
import com.example.ru_cafe_android.categories.Bread;
import com.example.ru_cafe_android.categories.Protein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Stores values for Sandwich and its Protein, Bread, and AddOns on the menu
 * @author Namulun, modified Nov. 30, 2025
 */
public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructor for sandwich object
     * @param protein object required to create a sandwich. As the base price depends on the protein
     */
    public Sandwich(Protein protein) {
        this.protein = protein;
        this.addOns = new ArrayList<>();
    }

    /**
     * Return bread value of sandwich
     * @return Bread
     */
    public Bread getBread() { return bread; }

    /**
     * Set bread value for sandwich
     * @param bread value to set the sandwich
     */
    public void setBread(Bread bread) { this.bread = bread; }

    /**
     * Return protein value of sandwich
     * @return Protein enum
     */
    public Protein getProtein() { return protein; }

    /**
     * Gets current add-ons on the sandwich
     * @return ArrayList of add-ons on the sandwich
     */
    public ArrayList<AddOns> getAddOns() { return this.addOns; }

    /**
     * Add add-on to the sandwich
     * @param addOn add-on to add to the sandwich
     */
    public void addAddOn(AddOns addOn) { this.addOns.add(addOn); }

    /**
     * Remove add-on from sandwich
     * @param addOn add-on to remove from the sandwich
     */
    public void removeAddOn(AddOns addOn) { this.addOns.remove(addOn); }

    private double addOnsPrice() {
        double addOnsPrice = 0;
        for (AddOns addOn : addOns) addOnsPrice += addOn.getPrice();
        return addOnsPrice;
    }

    /**
     * Gets total price value for the sandwich
     * @return double price
     */
    @Override
    public double price() {
        return (addOnsPrice() + protein.getPrice());
    }

    /**
     * Get a complete breakdown of the sandwich
     * @return ArrayList of Strings containing all the information of the sandwich
     */
    @Override
    public ArrayList<String> breakdown() {
        ArrayList<String> breakDown = new ArrayList<>();
        if (this.getProtein() == null) {
            breakDown.add("Protein: [Please pick a protein]");
            return breakDown;
        }
        breakDown.add("Protein: " + this.getProtein().getName() + " $" + this.getProtein().getPrice());
        if (this.getBread() != null) breakDown.add("Bread: " + this.getBread().getName());
        if (this.getAddOns().size() <= 0) return breakDown;
        breakDown.add("Add ons: ");
        for (AddOns addOns: this.getAddOns()) {
            breakDown.add("   - " + addOns.getName() +
                    String.format(" $%.2f", addOns.getPrice()));
        }
        return breakDown;
    }

    /**
     * Checks whether two sandwiches are equal
     * @param obj   the reference object with which to compare.
     * @return true if they are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sandwich compareSandwich) {
            if (!(this.getProtein().getName().equals(compareSandwich.getProtein().getName()))) return false;
            if (!(this.getBread().getName().equals(compareSandwich.getBread().getName()))) return false;
            ArrayList<AddOns> arr1 = this.getAddOns();
            ArrayList<AddOns> arr2 = compareSandwich.getAddOns();
            Collections.sort(arr1);
            Collections.sort(arr2);
            return arr1.equals(arr2);
        }
        return false;
    }

    /**
     * Create custom hashcode based on protein value
     * @return int containing custom hashcode
     */
    @Override
    public int hashCode() { return Objects.hash(this.getProtein()); }

    /**
     * Returns string representation of object
     * @return String object
     */
    @Override
    public String toString() {
        String sandwich = this.getProtein().getName();
        sandwich += " Sandwich " + "[";
        if (this.bread != null) sandwich += this.getBread().getName() + "] ";
        else sandwich += "] ";
        if (this.addOns != null) sandwich += this.getAddOns().toString();
        return sandwich;
    }
}
