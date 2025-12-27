package com.example.ru_cafe_android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ru_cafe_android.system.Order;

import java.util.ArrayList;

public class PastOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView pastOBreakdown, pastOTotal;
    private Spinner orderNumSpn;
    private Button cancelOrder;
    private Order selectedOrder;
    private OrderListManager orderListManager;
    private ArrayList<Integer> orderNums;

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
        this.orderNumSpn = (Spinner) findViewById(R.id.orderNumberSpinner);
        this.cancelOrder = (Button) findViewById(R.id.ordersCancel);

        this.orderListManager = OrderListManager.getInstance();
        this.selectedOrder = orderListManager.getOrderList().get(0);
    }

    public void onStart() {
        this.orderNums = new ArrayList<>();
        for (Order order: orderListManager.getOrderList()) {orderNums.add(order.getOrderNumber());}
        orderNumSpn.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, orderNums));
        orderNumSpn.setOnItemSelectedListener(this);

        pastOTotal.setText("$0.00");
        pastOBreakdown.setText("");
        updateBreakdown();
        super.onStart();
    }

    public void onResume() {super.onResume();}

    /* Spinner item selection event handlers */
    @Override
    /**
     * Spinner event handler
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String toastMsg = "Order ";
        this.selectedOrder = this.orderListManager.getOrderList().get(position);
        this.pastOTotal.setText(String.format("$%.2f", selectedOrder.orderPrice() * 1.06625));
        toastMsg += selectedOrder.getOrderNumber();

        updateBreakdown();
        Toast.makeText(this, toastMsg + " selected", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * Updates order breakdown in pastOBreakdown text view
     */
    public void updateBreakdown() {
        pastOBreakdown.setText("");
        if (this.selectedOrder == null) return;
        ArrayList<String> pastOStrBreakdown = selectedOrder.breakdown();
        for (String line: pastOStrBreakdown) pastOBreakdown.append(line + "\n");
    }

    /**
     * Order confirmation popup
     */
    public void confirmationPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Past order canceled successfully!").setTitle("Cancel Success");
        alert.setNegativeButton("OK", null);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Cancels selected past order
     * @param view current view
     */
    public void cancelPastOrder(View view) {
        if (this.orderNumSpn.getAdapter() == null) {
            Toast.makeText(this, "No valid past orders", Toast.LENGTH_SHORT).show();
            return;
        }
        int position = 0;
        confirmationPopup();
        this.orderListManager.removeOrderFromList(selectedOrder);
        while (this.orderNums.get(position) != selectedOrder.getOrderNumber()) {position++;}
        this.orderNums.remove(position);

        if (!this.orderListManager.getOrderList().isEmpty()) {
            orderNumSpn.setSelection(0);
            this.selectedOrder = this.orderListManager.getOrderList().get(0);
            this.pastOTotal.setText(String.format("$%.2f", selectedOrder.orderPrice()));
        }
        else {
            orderNumSpn.setAdapter(null);
            this.selectedOrder = null;
            this.pastOTotal.setText("$0.00");
        }
        updateBreakdown();
    }
}
