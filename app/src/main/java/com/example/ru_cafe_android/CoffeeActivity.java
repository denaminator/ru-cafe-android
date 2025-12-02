package com.example.ru_cafe_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ru_cafe_android.categories.*;
import com.example.ru_cafe_android.system.*;

import java.util.ArrayList;

/**
 * Activity controller for Coffee screen
 * @author Namulun, modified Nov. 30, 2025
 */
public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner cupSizeSpn, quantitySpn;
    private TextView orderBreakdown, totalPriceBox;
    private CheckBox twoMilk, mocha, vanilla, caramel, whippedCream;
    private Button addOrder;
    private CupSize size;
    private Order order;
    private Coffee coffee;
    private int quantity;
    private double totalPrice;

    /*Base functions*/
    /**
     * Sets up interactive elements of Coffee view and links to GUI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.coffee_activity);

        this.cupSizeSpn = (Spinner) findViewById(R.id.coffeeSizeSpinner);
        this.quantitySpn = (Spinner) findViewById(R.id.coffeeQuantitySpinner);
        this.orderBreakdown = (TextView) findViewById(R.id.coffeeBreakdown);
        this.totalPriceBox = (TextView) findViewById(R.id.coffeeTotal);
        this.addOrder = (Button) findViewById(R.id.coffeeAddOrder);

        this.twoMilk = (CheckBox) findViewById(R.id.twoMilk);
        this.mocha = (CheckBox) findViewById(R.id.mocha);
        this.vanilla = (CheckBox) findViewById(R.id.vanilla);
        this.caramel = (CheckBox) findViewById(R.id.caramel);
        this.whippedCream = (CheckBox) findViewById(R.id.whippedCream);
    }

    /**
     * Initializes data field values, called after onCreate()
     */
    protected void onStart() {
        ArrayList<Integer> quantityArr = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++) {quantityArr.add(i);}
        cupSizeSpn.setAdapter(new ArrayAdapter<CupSize>(this,
                android.R.layout.simple_list_item_1, CupSize.values()));
        cupSizeSpn.setOnItemSelectedListener(this);

        quantitySpn.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, quantityArr));
        quantitySpn.setOnItemSelectedListener(this);

        totalPrice = 0.00;
        totalPriceBox.setText(String.format("$%.2f", totalPrice));
        orderBreakdown.setText("");

        resetAddIns();
        twoMilkCBListener();
        mochaCBListener();
        vanillaCBListener();
        caramelCBListener();
        whippedCreamCBListener();
        super.onStart();
    }

    /**
     * Callback method executed after onStart()
     */
    protected void onResume() {super.onResume();}

    /* Spinner event handlers */
    @Override
    /**
     * Spinner event handler (cup size and quantity)
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String toastMsg = "";
        // CupSize spinner
        if (parent.getSelectedItem() instanceof CupSize) {
            this.size = (CupSize) cupSizeSpn.getSelectedItem();
            this.totalPrice = size.getPrice();
            this.totalPriceBox.setText(String.format("$%.2f", totalPrice));
            this.coffee = new Coffee(size);

            toastMsg = size.getName();
            resetAddIns();
        }
        // Quantity spinner
        else if (parent.getSelectedItem() instanceof Integer) {
            // Adjust price with add-ins included
            if (quantity > 1) {
                double adds = totalPrice - (this.size.getPrice() * this.quantity);
                adds /= this.quantity;
                totalPrice = this.size.getPrice() + adds;
            }
            quantity = (int) quantitySpn.getSelectedItem();
            if (quantity > 1) totalPrice *= quantity;
            totalPriceBox.setText(String.format("$%.2f", totalPrice));

            toastMsg = quantity + " order(s)";
        }
        updateBreakdown();
        Toast.makeText(this, toastMsg + " selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /* CheckBox event handler (via private class) */
    /**
     * Resets add-in Checkboxes
     */
    private void resetAddIns() {
        twoMilk.setChecked(false);
        mocha.setChecked(false);
        vanilla.setChecked(false);
        caramel.setChecked(false);
        whippedCream.setChecked(false);
    }

    /**
     * 2% Milk CheckBox listener
     */
    public void twoMilkCBListener() {
        double addIn = 0.00;
        twoMilk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddIns.TWO.getPrice() * quantity);
                    coffee.addAddIn(AddIns.TWO);
                    Toast.makeText(CoffeeActivity.this, AddIns.TWO.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddIns.TWO.getPrice() * quantity);
                    coffee.removeAddIn(AddIns.TWO);
                    Toast.makeText(CoffeeActivity.this, AddIns.TWO.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                totalPriceBox.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Mocha CheckBox listener
     */
    public void mochaCBListener() {
        mocha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddIns.MOCHA.getPrice() * quantity);
                    coffee.addAddIn(AddIns.MOCHA);
                    Toast.makeText(CoffeeActivity.this, AddIns.MOCHA.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddIns.MOCHA.getPrice() * quantity);
                    coffee.removeAddIn(AddIns.MOCHA);
                    Toast.makeText(CoffeeActivity.this, AddIns.MOCHA.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                totalPriceBox.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Vanilla CheckBox listener
     */
    public void vanillaCBListener() {
        vanilla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddIns.VANILLA.getPrice() * quantity);
                    coffee.addAddIn(AddIns.VANILLA);
                    Toast.makeText(CoffeeActivity.this, AddIns.VANILLA.getName() + " added", Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddIns.VANILLA.getPrice() * quantity);
                    coffee.removeAddIn(AddIns.VANILLA);
                    Toast.makeText(CoffeeActivity.this, AddIns.VANILLA.getName() + " removed", Toast.LENGTH_SHORT).show();
                }
                totalPriceBox.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Caramel CheckBox listener
     */
    public void caramelCBListener() {
        caramel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddIns.CARAMEL.getPrice() * quantity);
                    coffee.addAddIn(AddIns.CARAMEL);
                    Toast.makeText(CoffeeActivity.this, AddIns.CARAMEL.getName() + " added", Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddIns.CARAMEL.getPrice() * quantity);
                    coffee.removeAddIn(AddIns.CARAMEL);
                    Toast.makeText(CoffeeActivity.this, AddIns.CARAMEL.getName() + " removed", Toast.LENGTH_SHORT).show();
                }
                totalPriceBox.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Whipped cream CheckBox listener
     */
    public void whippedCreamCBListener() {
        whippedCream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddIns.VANILLA.getPrice() * quantity);
                    coffee.addAddIn(AddIns.VANILLA);
                    Toast.makeText(CoffeeActivity.this, AddIns.VANILLA.getName() + " added", Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddIns.VANILLA.getPrice() * quantity);
                    coffee.removeAddIn(AddIns.VANILLA);
                    Toast.makeText(CoffeeActivity.this, AddIns.VANILLA.getName() + " removed", Toast.LENGTH_SHORT).show();
                }
                totalPriceBox.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /* Order addition event handlers */
    /**
     * Order confirmation popup
     */
    public void confirmationPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Order added successfully!").setTitle("Order Success");
        alert.setNegativeButton("OK", null);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Updates order breakdown in orderBreakdown text view
     */
    public void updateBreakdown() {
        if (this.coffee == null) return;
        orderBreakdown.setText("");
        ArrayList<String> coffeeBreakdown = coffee.breakdown();
        for (String line: coffeeBreakdown) orderBreakdown.append(line + "\n");
    }

    public void coffeeAddOrder(View view) {
        for (int i = 0; i < quantity; i++) {order.addOrderItem(coffee);}
        confirmationPopup();

        resetAddIns();
        cupSizeSpn.setSelection(1);
        this.size = CupSize.SHORT;
        quantitySpn.setSelection(1);
        this.quantity = 1;
        totalPrice = 0.00;
        totalPriceBox.setText(String.format("$%.2f", totalPrice));

        orderBreakdown.setText("Order added successfully");
    }
}
