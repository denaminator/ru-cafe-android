package com.example.ru_cafe_android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ru_cafe_android.system.*;
import com.example.ru_cafe_android.categories.*;

import java.util.ArrayList;

/**
 * Activity controller for Donuts screen
 * @author Jay, modified Nov. 30, 2025
 */
public class DonutsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView donutsRecycler;
    private Spinner quantitySpn;
    private TextView donutsTotal;
    private ImageView donutsImage;
    private Button donutsAddOrder;
    private int quantity;
    private double totalPrice;
    private Donut donut;
    private ArrayList<Donut> allDonuts;
    private RecyclerView.Adapter donutsAdapter;
    private int selectedPos = RecyclerView.NO_POSITION;
    private OrderManager orderManager;

    /**
     * Sets up base elements for donuts page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.donuts_activity);

        this.donutsRecycler = (RecyclerView) findViewById(R.id.donutsRecycler);
        this.quantitySpn = (Spinner) findViewById(R.id.donutsQuantitySpinner);
        this.donutsTotal = (TextView) findViewById(R.id.donutsTotal);
        this.donutsImage = (ImageView) findViewById(R.id.donutsImage);
        orderManager = OrderManager.getInstance();

    }

    /**
     * Initializes spinners and activates checkboxes after onCreate
     */
    protected void onStart() {
        ArrayList<Integer> quantityArr = new ArrayList<>();
        for (int i = 1; i < 11; i++) {quantityArr.add(i);}
        quantitySpn.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, quantityArr));
        quantitySpn.setOnItemSelectedListener(this);

        allDonuts = getAllDonuts();
        // Load this into recycler an
        totalPrice = 0.0;
        donutsTotal.setText(String.format("%.2f", totalPrice));

        donutsRecycler.setLayoutManager(new LinearLayoutManager(this));
        donutsAdapter = getDonutsAdapter();
        donutsRecycler.setAdapter(donutsAdapter);

        super.onStart();
    }

    private RecyclerView.Adapter getDonutsAdapter() {
        return new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public int getItemCount() {
                return allDonuts.size();
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView tv = holder.itemView.findViewById(android.R.id.text1);

                Donut d = allDonuts.get(position);
                tv.setText(d.getType().getName() + " - " + d.getFlavor());

                // highlight selected
                holder.itemView.setBackgroundColor(
                        position == selectedPos ? 0xFFE0E0E0 : 0x00000000
                );

                holder.itemView.setOnClickListener(v -> {
                    int old = selectedPos;
                    selectedPos = position;
                    String imageName = "donut_" + selectedPos;
                    int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    notifyItemChanged(old);
                    notifyItemChanged(selectedPos);
                    donutsImage.setImageResource(resId);
                    Toast.makeText(holder.itemView.getContext(),
                            allDonuts.get(selectedPos).toString() + " selected",
                            Toast.LENGTH_SHORT).show();

                    donut = allDonuts.get(selectedPos);  // update the selected donut
                    totalPrice = donut.price() * quantity;
                    donutsTotal.setText(String.format("$%.2f", totalPrice));
                });
            }
        };
    }

    /**
     * Callback method executed after onStart()
     */
    protected void onResume() {super.onResume();}

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String toastMsg = "";
        if (parent.getSelectedItem() instanceof Integer) {
            if (donut == null) {
                resetDonuts();
                return;
            }
            if (quantity > 1) { // get price of one single item to multiply with new quantity
                double adds = totalPrice - (this.donut.price() * this.quantity);
                adds /= this.quantity;
                totalPrice = donut.price() + adds;
            }
            quantity = (int) quantitySpn.getSelectedItem();
            if (quantity > 1) totalPrice *= quantity;
            donutsTotal.setText(String.format("$%.2f", totalPrice));
            toastMsg = quantity + " order(s)";
        }
        Toast.makeText(this, toastMsg + " selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * Popup message (confirmation or null error)
     * @param message message for body of popup
     * @param title title of popup
     */
    public void popupMsg(String message, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message).setTitle(title);
        alert.setNegativeButton("OK", null);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void donutsAddOrder(View view) {
        if (donut == null) {
            popupMsg("No donut selected!", "WARNING");
            return;
        }
        for (int i = 0; i < quantity; i++) { orderManager.getCurrentOrder().addOrderItem(donut); }
        popupMsg("Order added successfully!", "Order Success");
        resetDonuts();
    }

    private void resetDonuts() {
        quantitySpn.setSelection(0);
        this.quantity = 1;

        this.totalPrice = 0.0;
        donutsTotal.setText(String.format("$%.2f", totalPrice));

        this.donut = null;
        selectedPos = RecyclerView.NO_POSITION;
        if (donutsAdapter != null) donutsAdapter.notifyDataSetChanged();
        donutsImage.setImageResource(R.drawable.donut_header_logo);
    }


    /**
     * Creates every possible donut combination to add to the recycler view
     * @return ArrayList of all Donuts
     */
    private ArrayList<Donut> getAllDonuts() {
        ArrayList<Donut> allDonuts = new ArrayList<>();
        allDonuts.add(new Donut(DonutType.YEAST, "Chocolate Frosted"));
        allDonuts.add(new Donut(DonutType.YEAST, "Strawberry Frosted"));
        allDonuts.add(new Donut(DonutType.YEAST, "Glazed"));
        allDonuts.add(new Donut(DonutType.YEAST, "Jelly"));
        allDonuts.add(new Donut(DonutType.YEAST, "Boston Creme"));
        allDonuts.add(new Donut(DonutType.YEAST, "Powdered"));

        allDonuts.add(new Donut(DonutType.CAKE, "Red Velvet"));
        allDonuts.add(new Donut(DonutType.CAKE, "Blueberry"));
        allDonuts.add(new Donut(DonutType.CAKE, "Vanilla Coconut"));

        allDonuts.add(new Donut(DonutType.HOLE, "Jelly"));
        allDonuts.add(new Donut(DonutType.HOLE, "Powdered"));
        allDonuts.add(new Donut(DonutType.HOLE, "Chocolate"));

        allDonuts.add(new Donut(DonutType.SEASONAL, "Spooky"));
        allDonuts.add(new Donut(DonutType.SEASONAL, "Pumpkin Spice"));

        return allDonuts;
    }
}
