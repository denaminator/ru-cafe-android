package com.example.ru_cafe_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity controller for Current Order screen
 * @author Namulun and Jay, modified Dec. 4, 2025
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private TextView currOBreakdown, currOSubtotal, currOSalesTax, currOTotal;
    private Button remSelected, placeOrder;

    /**
     * Initial setup for Current Order activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.current_order_view);

        this.currOBreakdown = (TextView) findViewById(R.id.currentOrderBreakdown);
        this.currOSubtotal = (TextView) findViewById(R.id.currentOrderSubtotal);
        this.currOSalesTax = (TextView) findViewById(R.id.currentOrderSalesTax);
        this.currOTotal = (TextView) findViewById(R.id.currentOrderTotal);

        this.remSelected = (Button) findViewById(R.id.ordersRemSelected);
        this.placeOrder = (Button) findViewById(R.id.ordersPlaceOrder);
    }

    public void onStart() {super.onStart();}

    public void onResume() {super.onResume();}
}
