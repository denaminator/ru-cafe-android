package com.example.ru_cafe_android;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity controller for Sandwiches screen
 * @author Namulun, modified Nov. 30, 2025
 */
public class SandwichesActivity extends AppCompatActivity {

    /**
     * Sets up base elements for sandwiches page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sandwiches_activity);
    }
}
