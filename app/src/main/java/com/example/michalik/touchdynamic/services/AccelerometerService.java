package com.example.michalik.touchdynamic.services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;

import com.example.michalik.touchdynamic.objects.AccelerometerMeasurement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalik on 21.10.16
 */

public class AccelerometerService implements SensorEventListener2 {

    private final Sensor s;
    private final SensorManager sensorManager;
    private List<AccelerometerMeasurement> measurements;

    public List<AccelerometerMeasurement> getMeasurements() {
        return measurements;
    }

    public AccelerometerService(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void init(){
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
        measurements = new ArrayList<>();

    }
    public void uinit(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        measurements.add(new AccelerometerMeasurement(event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
