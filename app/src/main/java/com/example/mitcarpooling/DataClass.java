package com.example.mitcarpooling;

public class DataClass {


    private String dataDate, dataTime, dataSource, dataDestination, dataAmount, dataVehicleNo;

    public String getDataDate() {
        return dataDate;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getDataDestination() {
        return dataDestination;
    }

    public String getDataAmount() {
        return dataAmount;
    }

    public String getDataVehicleNo() {
        return dataVehicleNo;
    }

    public DataClass(String dataDate, String dataTime, String dataSource, String dataDestination, String dataAmount, String dataVehicleNo) {
        this.dataDate = dataDate;
        this.dataTime = dataTime;
        this.dataSource = dataSource;
        this.dataDestination = dataDestination;
        this.dataAmount = dataAmount;
        this.dataVehicleNo = dataVehicleNo;
    }

    public DataClass() {

    }


}
