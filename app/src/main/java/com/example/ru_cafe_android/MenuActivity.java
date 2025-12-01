package com.example.ru_cafe_android;

import com.example.ru_cafe_android.system.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

/**
 * Activity controller for Menu screen
 * @author Namulun, modified Nov. 30, 2025
 */
public class MenuActivity extends AppCompatActivity {
    private Order order;
    private ArrayList<Order> orderList;

    /*Base functions*/
    /**
     * Initial setup for RU Donuts app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_activity);
    }

    /**
     * Callback method executed right after onCreate().
     */
    protected void onStart() {super.onStart();}

    /**
     * Callback method executed right after onStart().
     */
    protected void onResume() {super.onResume();}

    /*Order update (from OrdersActivity) methods*/
    /**
     * Sets the global order to a value
     * @param setOrder the order which is pushed to be the global one
     */
    public void setOrder(Order setOrder) {
        this.order = setOrder;
    }

    /**
     * Sets the list of placed orders
     * @param orders list that will be set from the order controller
     */
    public void setOrderList(ArrayList<Order> orders) {this.orderList = orders;}

    /* New activity event handlers*/
    /**
     * Event handler for Coffee button click
     * @param view Android View that triggered the event
     */
    public void displayCoffeeActivity(View view) {
        Intent intent = new Intent(this, CoffeeActivity.class);
        startActivity(intent);
    }

    /**
     * Event handler for Donuts button click
     * @param view Android View that triggered the event
     */
    public void displayDonutsActivity(View view) {
        Intent intent = new Intent(this, DonutsActivity.class);
        startActivity(intent);
    }

    /**
     * Event handler for Sandwiches button click
     * @param view Android View that triggered the event
     */
    public void displaySandwichesActivity(View view) {
        Intent intent = new Intent(this, SandwichesActivity.class);
        startActivity(intent);
    }

    /**
     * Event handler for Orders button click
     * @param view Android View that triggered the event
     */
    public void displayOrdersActivity(View view) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }
}