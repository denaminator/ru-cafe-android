package com.example.ru_cafe_android;

import android.app.AlertDialog;
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

import com.example.ru_cafe_android.system.*;
import com.example.ru_cafe_android.categories.*;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Activity controller for Sandwiches screen
 * @author Namulun, modified Nov. 30, 2025
 */
public class SandwichesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner proteinSpn, breadSpn, quantitySpn;
    private TextView sandwichesBreakdown, sandwichesTotal;
    private CheckBox lettuce, tomato, onion, cheese;
    private Button sandwichesAddOrder;
    private Order order;
    private Sandwich sandwich;
    private Protein protein;
    private Bread bread;
    private int quantity;
    private double totalPrice;

    /**
     * Sets up base elements for sandwiches page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sandwiches_activity);

        this.proteinSpn = (Spinner) findViewById(R.id.sandwichesProteinSpinner);
        this.breadSpn = (Spinner) findViewById(R.id.sandwichesBreadSpinner);
        this.quantitySpn = (Spinner) findViewById(R.id.sandwichesQuantitySpinner);
        this.sandwichesBreakdown = (TextView) findViewById(R.id.sandwichesBreakdown);
        this.sandwichesTotal = (TextView) findViewById(R.id.sandwichesTotal);
        this.lettuce = (CheckBox) findViewById(R.id.lettuce);
        this.tomato = (CheckBox) findViewById(R.id.tomato);
        this.onion = (CheckBox) findViewById(R.id.onion);
        this.cheese = (CheckBox) findViewById(R.id.cheese);

        lettuceCBListener();
        tomatoCBListener();
        onionCBListener();
        cheeseCBListener();

    }


    /**
     * Initializes spinners and activates checkboxes after onCreate
     */
    protected void onStart() {
        ArrayList<Integer> quantityArr = new ArrayList<>();
        for (int i = 1; i < 11; i++) {quantityArr.add(i);}
        proteinSpn.setAdapter(new ArrayAdapter<Protein>(this,
                android.R.layout.simple_list_item_1, Protein.values()));
        proteinSpn.setOnItemSelectedListener(this);

        breadSpn.setAdapter(new ArrayAdapter<Bread>(this,
                android.R.layout.simple_list_item_1, Bread.values()));
        breadSpn.setOnItemSelectedListener(this);

        quantitySpn.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, quantityArr));
        quantitySpn.setOnItemSelectedListener(this);

        totalPrice = 0.0;
        sandwichesTotal.setText(String.format("%.2f", totalPrice));
        sandwichesBreakdown.setText("");

        resetAddOns();
        // add all checkbox listeners
        super.onStart();
    }

    /**
     * Callback method executed after onStart()
     */
    protected void onResume() {super.onResume();}

    @Override
    /**
     *
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String toastMsg = "";
        if (parent.getSelectedItem() instanceof Protein) {
            this.protein = (Protein) proteinSpn.getSelectedItem();
            this.totalPrice = protein.getPrice();
            this.sandwichesTotal.setText(String.format("%.2f", totalPrice));
            this.sandwich = new Sandwich(protein);
            toastMsg = protein.getName();
            resetAddOns();
        }
        else if (parent.getSelectedItem() instanceof Bread) {
            this.bread = (Bread) breadSpn.getSelectedItem();
            this.sandwich.setBread(bread);
            toastMsg = bread.getName();
            resetAddOns();
        }
        else if (parent.getSelectedItem() instanceof Integer) {
            if (quantity > 1) { // get price of one single item to multiply with new quantity
                double adds = totalPrice - (this.protein.getPrice() * this.quantity);
                adds /= this.quantity;
                totalPrice = this.protein.getPrice() + adds;
            }
            quantity = (int) quantitySpn.getSelectedItem();
            if (quantity > 1) totalPrice *= quantity;
            sandwichesTotal.setText(String.format("$%.2f", totalPrice));
            toastMsg = quantity + " order(s)";
        }
        updateBreakdown();
        Toast.makeText(this, toastMsg + " selected", Toast.LENGTH_SHORT).show();;
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /* Checkbox methods */

    /**
     * Resets add-on checkboxes
     */
    private void resetAddOns() {
        lettuce.setChecked(false);
        onion.setChecked(false);
        tomato.setChecked(false);
        cheese.setChecked(false);
    }

    /**
     * Lettuce CheckBox listener
     */
    public void lettuceCBListener() {
        double addOn = 0.00;
        lettuce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddOns.LETTUCE.getPrice() * quantity);
                    sandwich.addAddOn(AddOns.LETTUCE);
                    Toast.makeText(SandwichesActivity.this, AddOns.LETTUCE.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddOns.LETTUCE.getPrice() * quantity);
                    sandwich.removeAddOn(AddOns.LETTUCE);
                    Toast.makeText(SandwichesActivity.this, AddOns.LETTUCE.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                sandwichesTotal.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Onions checkbox listener
     */
    public void onionCBListener() {
        double addOn = 0.00;
        onion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddOns.ONIONS.getPrice() * quantity);
                    sandwich.addAddOn(AddOns.ONIONS);
                    Toast.makeText(SandwichesActivity.this, AddOns.ONIONS.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddOns.ONIONS.getPrice() * quantity);
                    sandwich.removeAddOn(AddOns.ONIONS);
                    Toast.makeText(SandwichesActivity.this, AddOns.ONIONS.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                sandwichesTotal.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Tomoato checkbox listener
     */
    public void tomatoCBListener() {
        double addOn = 0.00;
        tomato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddOns.TOMATOES.getPrice() * quantity);
                    sandwich.addAddOn(AddOns.TOMATOES);
                    Toast.makeText(SandwichesActivity.this, AddOns.TOMATOES.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddOns.TOMATOES.getPrice() * quantity);
                    sandwich.removeAddOn(AddOns.TOMATOES);
                    Toast.makeText(SandwichesActivity.this, AddOns.TOMATOES.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                sandwichesTotal.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

    /**
     * Cheese checkbox listener
     */
    public void cheeseCBListener() {
        double addOn = 0.00;
        cheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPrice += (AddOns.CHEESE.getPrice() * quantity);
                    sandwich.addAddOn(AddOns.CHEESE);
                    Toast.makeText(SandwichesActivity.this, AddOns.CHEESE.getName() + " added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    totalPrice -= (AddOns.CHEESE.getPrice() * quantity);
                    sandwich.removeAddOn(AddOns.CHEESE);
                    Toast.makeText(SandwichesActivity.this, AddOns.CHEESE.getName() + " removed",
                            Toast.LENGTH_SHORT).show();
                }
                sandwichesTotal.setText(String.format("$%.2f", totalPrice));
                updateBreakdown();
            }
        });
    }

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
     * Updates breakdown textview
     */
    public void updateBreakdown() {
        if (sandwich == null) return;
        sandwichesBreakdown.setText("");
        ArrayList<String> sandwichBreakdown = sandwich.breakdown();
        for (String line: sandwichBreakdown) sandwichesBreakdown.append(line + "\n");
    }

    /**
     * Adds sandwich(es) to order
     */
    public void sandwichesAddOrder(View view) {
        for (int i = 0; i < quantity; i++) {order.addOrderItem(sandwich);}
        confirmationPopup();
        resetAddOns();

        quantitySpn.setSelection(1);
        this.quantity = 1;
        proteinSpn.setSelection(1);
        this.protein = Protein.BEEF;
        breadSpn.setSelection(1);
        this.bread = Bread.BAGEL;
        totalPrice = 0.0;
        sandwichesTotal.setText(String.format("$%.2f", totalPrice));
        this.sandwichesBreakdown.setText("Order placed successfully");
    }


}
