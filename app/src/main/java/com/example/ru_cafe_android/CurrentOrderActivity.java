package com.example.ru_cafe_android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ru_cafe_android.system.*;

import java.util.ArrayList;

/**
 * Activity controller for Current Order screen
 * @author Namulun and Jay, modified Dec. 4, 2025
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private TextView currOSubtotal, currOSalesTax, currOTotal;
    private ListView currOBreakdown;
    private Button remSelected, placeOrder;
    private OrderManager orderManager;
    private OrderListManager orderListManager;
    private Order order;
    private String orderBreakdown;

    /**
     * Initial setup for Current Order activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.current_order_view);

        this.currOBreakdown = (ListView) findViewById(R.id.currentOrderBreakdown);
        this.currOSubtotal = (TextView) findViewById(R.id.currentOrderSubtotal);
        this.currOSalesTax = (TextView) findViewById(R.id.currentOrderSalesTax);
        this.currOTotal = (TextView) findViewById(R.id.currentOrderTotal);

        this.remSelected = (Button) findViewById(R.id.ordersRemSelected);
        this.placeOrder = (Button) findViewById(R.id.ordersPlaceOrder);
        this.orderManager = OrderManager.getInstance();
        this.order = orderManager.getCurrentOrder();
        this.orderListManager = OrderListManager.getInstance();
    }

    public void onStart() {
        this.order = orderManager.getCurrentOrder();
        super.onStart();
        // Load in the items from the current order into the list view
        ArrayList<MenuItem> items = order.getOrderItems();
        ArrayAdapter<?> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                order.breakdown()
        );
        currOBreakdown.setAdapter(adapter);

        currOBreakdown.setOnItemClickListener((parent, view, position, id) -> {
            orderBreakdown = parent.getItemAtPosition(position).toString();

            Toast.makeText(this,
                    "Selected: " + orderBreakdown,
                    Toast.LENGTH_SHORT).show();
        });
        updateCurrPrice();
    }

    public void onResume() {super.onResume();}

    public void updateCurrPrice() {
        currOSubtotal.setText("");
        currOSalesTax.setText("");
        currOTotal.setText("");
        if (order == null) return;
        currOSubtotal.setText(String.format("$%.2f", this.order.orderPrice()));
        currOSalesTax.setText(String.format("%.2f", (this.order.orderPrice() * 0.06625)));
        currOTotal.setText(String.format("%.2f", (this.order.orderPrice() + (this.order.orderPrice() * 0.06625))));
    }

    public void confirmationPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Order placed successfully!").setTitle("Order Success");
        alert.setNegativeButton("OK", null);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void removeOrderItem(View view) {

        if (orderBreakdown == null) {
            Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Remove from order
        order.removeItemFromBreakDownString(orderBreakdown);

        Toast.makeText(this,
                "Removed: " + orderBreakdown,
                Toast.LENGTH_SHORT).show();

        // Clear selection
        orderBreakdown = null;

        // Refresh the ListView
        ArrayAdapter<?> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                order.breakdown()
        );

        currOBreakdown.setAdapter(adapter);
        updateCurrPrice();
    }

    public void placeOrder(View view) {
        if (this.order.getOrderItems().isEmpty()) {
            Toast.makeText(this, "No items present in order", Toast.LENGTH_SHORT).show();
            return;
        }
        confirmationPopup();
        Order dupe = this.order.deepCopy();
        orderListManager.getOrderList().add(dupe);
        order.getOrderItems().clear();
        ArrayAdapter<?> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                order.breakdown()
        );
        currOBreakdown.setAdapter(adapter);
        this.order = new Order();
        orderManager.setCurrentOrder(this.order);
        updateCurrPrice();
    }
}
