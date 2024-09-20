package com.example.mitcarpooling;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class publishRide extends AppCompatActivity {

    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String SOURCE_KEY = "source";
    public static final String DESTINATION_KEY = "destination";
    public static final String AMOUNT_KEY = "amount";
    public static final String VEHICLENO_KEY = "vehicleNo";
    public static final String TAG = "Rides";



    EditText uploadDate, uploadTime, uploadSource, uploadDestination, uploadAmount, uploadVehicleNo;
    Button publish;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_publish_ride);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        publish = findViewById(R.id.publishbtn);

        uploadDate = findViewById(R.id.et_date);
        uploadTime = findViewById(R.id.et_time);
        uploadSource = findViewById(R.id.et_source);
        uploadDestination = findViewById(R.id.et_destination);
        uploadAmount = findViewById(R.id.et_amount);
        uploadVehicleNo = findViewById(R.id.et_vehicleno);



        uploadTime.setOnClickListener(view -> opentimeDialog());

        uploadDate.setOnClickListener(view -> openDialog());

    }

    private void opentimeDialog(){
        TimePickerDialog dialog = new TimePickerDialog(this, (timePicker, hours, minutes) -> {

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hours);
            datetime.set(Calendar.MINUTE, minutes);

            String am_pm = (datetime.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";

            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0 ) ? "12" : datetime.get(Calendar.HOUR)+"";

            String timeString = strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm;

            uploadTime.setText(timeString);



        }, 15, 0, true);

        dialog.show();

    }

    private void openDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                uploadDate.setText(day +"/"+ (month + 1) +"/"+ year);

            }
        }, 2024, 8, 29);


        dialog.show();
    }


    public void save(View view){
        String dataDate = uploadDate.getText().toString();
        String dataTime = uploadTime.getText().toString();
        String dataSource = uploadSource.getText().toString();
        String dataDestination = uploadDestination.getText().toString();
        String dataAmount = uploadAmount.getText().toString();
        String dataVehicleNo = uploadVehicleNo.getText().toString();


        if (TextUtils.isEmpty(dataDate)) {
            Toast.makeText(publishRide.this, "Please enter the date when you are going", Toast.LENGTH_LONG).show();
            uploadDate.setError("Date is required");
            uploadDate.requestFocus();
        } else if (TextUtils.isEmpty(dataTime)) {
            Toast.makeText(publishRide.this, "Please enter the correct time", Toast.LENGTH_LONG).show();
            uploadTime.setError("Time is required");
            uploadTime.requestFocus();
        } else if (TextUtils.isEmpty(dataSource)) {
            Toast.makeText(publishRide.this, "Please enter your source location", Toast.LENGTH_LONG).show();
            uploadSource.setError("Source location is required");
            uploadSource.requestFocus();
        } else if (TextUtils.isEmpty(dataDestination)) {
            Toast.makeText(publishRide.this, "Please enter your destination location", Toast.LENGTH_LONG).show();
            uploadDestination.setError("Destination location is required");
            uploadDestination.requestFocus();
        } else if (TextUtils.isEmpty(dataAmount)) {
            Toast.makeText(publishRide.this, "Please enter cost-effective amount", Toast.LENGTH_LONG).show();
            uploadAmount.setError("Amount field location is required");
            uploadAmount.requestFocus();
        } else if (TextUtils.isEmpty(dataVehicleNo)) {
            Toast.makeText(publishRide.this, "Please enter valid vehicle number", Toast.LENGTH_LONG).show();
            uploadVehicleNo.setError("Vehicle no. is required");
            uploadVehicleNo.requestFocus();
        }



        if (dataDate.isEmpty() || dataTime.isEmpty() || dataSource.isEmpty() || dataDestination.isEmpty() || dataAmount.isEmpty() || dataVehicleNo.isEmpty()) { return; }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(DATE_KEY, dataDate);
        dataToSave.put(TIME_KEY, dataTime);
        dataToSave.put(SOURCE_KEY, dataSource);
        dataToSave.put(DESTINATION_KEY, dataDestination);
        dataToSave.put(AMOUNT_KEY, dataAmount);
        dataToSave.put(VEHICLENO_KEY, dataVehicleNo);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(publishRide.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog1 = builder.create();
        dialog1.show();



        FirebaseFirestore mdocRef = FirebaseFirestore.getInstance();


        mdocRef.collection("Rides")
                        .add(dataToSave).addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId()))
                        .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));



        FirebaseFirestore.getInstance().collection("Rides").add(dataToSave).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                dialog1.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(publishRide.this, "Ride created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(publishRide.this, findRide.class));
                    finish();
                    progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(publishRide.this, "Ride creation failed", Toast.LENGTH_SHORT).show();

                }
                progressBar.setVisibility(View.GONE);

            }

        });


/*
        mdocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"Data saved successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Data save failed");
            }
        });

*/
    }



}
