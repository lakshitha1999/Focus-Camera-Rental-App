package com.example.camerarent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

public class OrderSummaryActivity extends AppCompatActivity {
    private TextView orderDetails;
    private Button confirmButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        // Initialize UI elements
        orderDetails = findViewById(R.id.orderDetails);
        confirmButton = findViewById(R.id.confirmButton);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("orders");

        // Get order ID from intent
        String orderId = getIntent().getStringExtra("orderId");

        // Retrieve order details from Firebase
        if (orderId != null) {
            fetchOrderDetails(orderId);
        }

        // Set confirm button listener
        confirmButton.setOnClickListener(view -> confirmOrder(orderId));
    }

    // Fetch order details
    private void fetchOrderDetails(String orderId) {
        databaseReference.child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order != null) {
                        String orderSummary = "Name: " + order.getName() +
                                "\nAddress: " + order.getAddress() +
                                "\nProduct: " + order.getProduct() +
                                "\nCategory: " + order.getCategory() +
                                "\nQuantity: " + order.getQuantity();
                        orderDetails.setText(orderSummary);
                    } else {
                        orderDetails.setText("Failed to parse order details.");
                    }
                } else {
                    orderDetails.setText("Order not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                orderDetails.setText("Failed to load order details.");
                Toast.makeText(OrderSummaryActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Confirm order
    private void confirmOrder(String orderId) {
        Intent intent = new Intent(OrderSummaryActivity.this,MainActivity.class);
        startActivity(intent);
        if (!TextUtils.isEmpty(orderId)) {
            databaseReference.child(orderId).child("status").setValue("Confirmed")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(OrderSummaryActivity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                            confirmButton.setEnabled(false); // Disable button after confirmation
                        } else {
                            Toast.makeText(OrderSummaryActivity.this, "Failed to confirm order", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Invalid Order ID for confirmation.", Toast.LENGTH_SHORT).show();
        }
    }
}