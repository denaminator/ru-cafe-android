package com.example.ru_cafe_android.system;

import com.example.ru_cafe_android.categories.AddIns;
import com.example.ru_cafe_android.categories.CupSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Stores values for Coffee and its CupSize and AddIns on the menu
 * @author Namulun, modified Nov. 30, 2025
 */
public class Coffee extends MenuItem{
    private CupSize size;
    private ArrayList<AddIns> addIns;

    /**
     * Constructor to create a coffee object
     * @param size size of the coffee. Requires the customer to pick a size before any AddIns
     */
    public Coffee(CupSize size) {
        this.size = size;
        this.addIns = new ArrayList<>();
    }

    /**
     * Default constructor for coffee
     */
    public Coffee() {}

    /**
     * Getter method for Coffee addIns list
     * @return Array list of add ins for this order
     */
    public ArrayList<AddIns> getAddIns() { return addIns; }

    /**
     * Getter method for cupSize
     * @return cupSize
     */
    public CupSize getSize() { return size; }

    /**
     * Adds add-in to Coffee
     * @param addIn
     */
    public void addAddIn(AddIns addIn) { this.addIns.add(addIn); }

    /**
     * Removes add-in from Coffee
     * @param addIn
     */
    public void removeAddIn(AddIns addIn) { this.addIns.remove(addIn); }

    /**
     * Calculates price of all add-ins
     * @return price of all add-ins
     */
    private double addInsPrice() {
        double addInsPrice = 0;
        for (AddIns addIn : addIns) addInsPrice += addIn.getPrice();
        return addInsPrice;
    }

    /**
     * Calculates price of Coffee
     * @return total price of Coffee
     */
    @Override
    public double price() { return (size.getPrice() + addInsPrice()); }

    /**
     * String breakdown of Coffee object
     * @return String array that contains all details of the coffee
     */
    @Override
    public ArrayList<String> breakdown() {
        ArrayList<String> breakDown = new ArrayList<>();
        if (this.getSize() == null) {
            breakDown.add("Cup size: [Please pick a cup size]");
            return breakDown;
        }
        breakDown.add("Cup size: " + this.getSize().getName() + " $" + this.getSize().getPrice());
        if (this.getAddIns().size() <= 0) return breakDown;
        breakDown.add("Add ins:");
        for (AddIns addIns: this.getAddIns()) breakDown.add("   - " + addIns.getName() +
                String.format(" $%.2f", addIns.getPrice()));
        return breakDown;
    }

    /**
     * Finds whether two coffee objects are equal
     * @param obj   the reference object with which to compare.
     * @return true if the two objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coffee compareCoffee) {
            if (!(this.getSize().equals(compareCoffee.getSize()))) return false;
            ArrayList<AddIns> arr1 = this.getAddIns();
            ArrayList<AddIns> arr2 = compareCoffee.getAddIns();
            Collections.sort(arr1);
            Collections.sort(arr2);
            return arr1.equals(arr2);
        }
        return false;
    }

    /**
     * Generates custom hashcode for hash matching
     * @return integer that hashes object based on size
     */
    @Override
    public int hashCode() { return Objects.hash(this.getSize()); }

    /**
     * Returns string representation of object
     * @return String with object add-ins
     */
    @Override
    public String toString() {
        String coffee = this.getSize().getName();
        coffee = coffee + " Coffee " + this.getAddIns().toString();
        return coffee;
    }
}
