package com.example.ru_cafe_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PastOrderActivity extends AppCompatActivity {
    private TextView pastOBreakdown, pastOTotal;
    private Spinner orderNumber;
    private Button cancelOrder;

    /**
     * Initial setup for Past Orders activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.past_order_view);

        this.pastOBreakdown = (TextView) findViewById(R.id.pastOrderBreakdown);
        this.pastOTotal = (TextView) findViewById(R.id.pastOrderTotal);
        this.orderNumber = (Spinner) findViewById(R.id.orderNumberSpinner);
        this.cancelOrder = (Button) findViewById(R.id.ordersCancel);
    }

    public void onStart() {super.onStart();}

    public void onResume() {super.onResume();}
}
