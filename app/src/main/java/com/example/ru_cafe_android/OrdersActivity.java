package com.example.ru_cafe_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity controller for Orders screen
 * @author Namulun, modified Dec. 4, 2025
 */
public class OrdersActivity extends AppCompatActivity {
    private Button currentOrder, pastOrder;
//    private TabLayout orderTab;
//    private LinearLayout orderView;
//    private TextView currOBreakdown, pastOBreakdown, currOSubtotal, currOSalesTax, currOTotal, pastOTotal;
//    private Spinner orderNumber;
//    private Button remSelected, placeOrder, cancelOrder;

    /* Base functions */
    /**
     * Initial setup for Orders activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orders_activity);
    }

    public void onStart() {super.onStart();}

    public void onResume() {super.onResume();}

    /* Button event handlers */
    /**
     * Event handler for Current Order button click
     * @param view Android View that triggered the event
     */
    public void displayCurrentOrderActivity(View view) {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Event handler for Past Orders button click
     * @param view Android View that triggered the event
     */
    public void displayPastOrdersActivity (View view) {
        Intent intent = new Intent(this, PastOrderActivity.class);
        startActivity(intent);
    }
}
