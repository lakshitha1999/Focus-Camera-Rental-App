package com.example.camerarent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderFormActivity extends AppCompatActivity {

    private EditText customerName, address, quantity;
    private Button submitOrderButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("orders");

        // Initialize UI elements
        customerName = findViewById(R.id.customerName);
        address = findViewById(R.id.address);
        quantity = findViewById(R.id.quantity);
        submitOrderButton = findViewById(R.id.submitOrderButton);

        // Get category and product from intent
        String category = getIntent().getStringExtra("category");
        String product = getIntent().getStringExtra("product");

        // Submit order to Firebase
        submitOrderButton.setOnClickListener(view -> submitOrder(category, product));
    }

    private void submitOrder(String category, String product) {
        String name = customerName.getText().toString();
        String userAddress = address.getText().toString();
        String userQuantity = quantity.getText().toString();

        // Validate input fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userAddress) || TextUtils.isEmpty(userQuantity)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantityValue;
        try {
            quantityValue = Integer.parseInt(userQuantity);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique order ID
        String orderId = databaseReference.push().getKey();
        if (orderId != null) {
            Order order = new Order(orderId, name, userAddress, category, product, quantityValue);

            Intent intent = new Intent(OrderFormActivity.this, OrderSummaryActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
            // Save the order to Firebase
            databaseReference.child(orderId)
                    .setValue(order)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Order Submitted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to Submit Order", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Failed to generate order ID", Toast.LENGTH_SHORT).show();
        }
    }
}
