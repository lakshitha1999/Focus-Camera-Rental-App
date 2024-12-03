package com.example.camerarent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class CategoryActivity extends AppCompatActivity {

    private ImageView product1Image, product2Image;
    private Button product1OrderButton, product2OrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        product1Image = findViewById(R.id.product1Image);
        product2Image = findViewById(R.id.product2Image);
        product1OrderButton = findViewById(R.id.product1OrderButton);
        product2OrderButton = findViewById(R.id.product2OrderButton);

        // Get category from intent
        String category = getIntent().getStringExtra("category");

        // Set up button listeners
        product1OrderButton.setOnClickListener(view -> openOrderForm(category, "Product 1"));
        product2OrderButton.setOnClickListener(view -> openOrderForm(category, "Product 2"));
    }

    private void openOrderForm(String category, String product) {
        Intent intent = new Intent(CategoryActivity.this, OrderFormActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}