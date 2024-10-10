package com.example.mitcarpooling;

import com.google.firebase.database.PropertyName;

public class DataClass {

    // Map fields exactly as they are in your Firestore collection
    @PropertyName("amount")
    private String amount;

    @PropertyName("date")
    private String date;

    @PropertyName("destination")
    private String destination;

    @PropertyName("source")
    private String source;

    @PropertyName("time")
    private String time;

    @PropertyName("vehicleNo")
    private String vehicleNo;

    // No-argument constructor required for Firestore deserialization
    public DataClass() {
    }

    // Constructor to initialize all fields
    public DataClass(String amount, String date, String destination, String source, String time, String vehicleNo) {
        this.amount = amount;
        this.date = date;
        this.destination = destination;
        this.source = source;
        this.time = time;
        this.vehicleNo = vehicleNo;
    }

    // Getter and Setter methods for all fields

    @PropertyName("amount")
    public String getAmount() {
        return amount;
    }

    @PropertyName("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("destination")
    public String getDestination() {
        return destination;
    }

    @PropertyName("destination")
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @PropertyName("source")
    public String getSource() {
        return source;
    }

    @PropertyName("source")
    public void setSource(String source) {
        this.source = source;
    }

    @PropertyName("time")
    public String getTime() {
        return time;
    }

    @PropertyName("time")
    public void setTime(String time) {
        this.time = time;
    }

    @PropertyName("vehicleNo")
    public String getVehicleNo() {
        return vehicleNo;
    }

    @PropertyName("vehicleNo")
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}