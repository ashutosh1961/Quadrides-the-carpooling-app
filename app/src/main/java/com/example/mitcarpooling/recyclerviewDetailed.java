package com.example.mitcarpooling;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class recyclerviewDetailed extends AppCompatActivity {

    TextView detailedDate, detailedTime, detailedSource, detailedDestination, detailedAmount, detailedVehicleNo;
    Button bookRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_detailed);

        // Initialize Views
        detailedDate = findViewById(R.id.detailedDate);
        detailedTime = findViewById(R.id.detailedTime);
        detailedSource = findViewById(R.id.detailedSource);
        detailedDestination = findViewById(R.id.detailedDestination);
        detailedAmount = findViewById(R.id.detailedAmount);
        detailedVehicleNo = findViewById(R.id.detailedVehicleNo);
        bookRideButton = findViewById(R.id.bookRideButton);

        // Get ride details from intent
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");
        String amount = getIntent().getStringExtra("amount");
        String vehicleNo = getIntent().getStringExtra("vehicleNo");

        // Set the ride details to the TextViews
        detailedDate.setText(date);
        detailedTime.setText(time);
        detailedSource.setText(source);
        detailedDestination.setText(destination);
        detailedAmount.setText(amount);
        detailedVehicleNo.setText(vehicleNo);

        // Set onClickListener for "Book Ride" button
        bookRideButton.setOnClickListener(v -> {
            // Book ride logic here
            Toast.makeText(recyclerviewDetailed.this, "Ride booked successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}