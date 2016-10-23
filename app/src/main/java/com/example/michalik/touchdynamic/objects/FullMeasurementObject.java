package com.example.michalik.touchdynamic.objects;

import com.example.michalik.touchdynamic.services.RealmHandler;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import io.realm.annotations.PrimaryKey;

/**
 * Created by michalik on 22.10.16
 */

public class FullMeasurementObject implements Serializable{
    @PrimaryKey
    private String id;

    private long time;

    private UserData userData;

    private List<TouchMeasurement> touchMeasurements;
    private List<AccelerometerMeasurement> accelerometerMeasurements;

    public FullMeasurementObject(){

    }

    public FullMeasurementObject(UserData userData,
                                 List<TouchMeasurement> touchMeasurements,
                                 List<AccelerometerMeasurement> accelerometerMeasurements) {
        this.userData = userData;
        this.touchMeasurements = touchMeasurements;
        this.accelerometerMeasurements = accelerometerMeasurements;
        this.time = Calendar.getInstance().getTimeInMillis();
        this.id= UUID.randomUUID().toString();
    }
    public FullMeasurementObject(List<TouchMeasurement> touchMeasurements,
                                 List<AccelerometerMeasurement> accelerometerMeasurements) {
        this.userData = RealmHandler.getUserData();
        this.touchMeasurements = touchMeasurements;
        this.accelerometerMeasurements = accelerometerMeasurements;
        this.time = Calendar.getInstance().getTimeInMillis();
        this.id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public UserData getUserData() {
        return userData;
    }

    public List<TouchMeasurement> getTouchMeasurements() {
        return touchMeasurements;
    }

    public List<AccelerometerMeasurement> getAccelerometerMeasurements() {
        return accelerometerMeasurements;
    }
}
