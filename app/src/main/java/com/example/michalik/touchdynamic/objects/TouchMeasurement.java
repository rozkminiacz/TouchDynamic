package com.example.michalik.touchdynamic.objects;

import java.io.Serializable;
import java.util.Calendar;

import io.realm.RealmObject;

/**
 * Created by michalik on 21.10.16
 */

public class TouchMeasurement extends RealmObject implements Serializable{
    private long eventTime;
    private float x, y;
    private String type;
    public static final String ACTION_UP = "ACTION_UP";
    public static final String ACTION_DOWN = "ACTION_DOWN";

    public TouchMeasurement(){

    }

    public TouchMeasurement(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type=type;
        this.eventTime= Calendar.getInstance().getTimeInMillis();
    }

    public long getEventTime() {
        return eventTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
