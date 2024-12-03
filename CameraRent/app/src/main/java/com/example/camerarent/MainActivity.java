package com.example.camerarent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ImageView cameraCategory, droneCategory, accessoriesCategory, actionCameraCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize UI elements
        cameraCategory = findViewById(R.id.cameraCategory);
        droneCategory = findViewById(R.id.droneCategory);
        accessoriesCategory = findViewById(R.id.accessoriesCategory);
        actionCameraCategory = findViewById(R.id.actionCameraCategory);

        // Set category click listeners
        cameraCategory.setOnClickListener(view -> openCategoryPage("Camera"));
        droneCategory.setOnClickListener(view -> openCategoryPage("Drone"));
        accessoriesCategory.setOnClickListener(view -> openCategoryPage("Accessories"));
        actionCameraCategory.setOnClickListener(view -> openCategoryPage("Action Camera"));
    }

    private void openCategoryPage(String category) {
        // Navigate to CategoryActivity and pass the category as a parameter
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

}