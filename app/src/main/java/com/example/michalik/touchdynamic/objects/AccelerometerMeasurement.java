package com.example.michalik.touchdynamic.objects;

import java.util.Calendar;

import io.realm.RealmObject;

/**
 * Created by michalik on 22.10.16
 */

public class AccelerometerMeasurement extends RealmObject{
    private float x, y, z;
    private long eventTime;

    public AccelerometerMeasurement(float x, float y, float z){
        this.x=x;
        this.y=y;
        this.z=z;
        this.eventTime = Calendar.getInstance().getTimeInMillis();
    }

    public AccelerometerMeasurement(){

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public long getEventTime() {
        return eventTime;
    }
}