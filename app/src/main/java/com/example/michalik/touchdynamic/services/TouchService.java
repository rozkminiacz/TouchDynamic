package com.example.michalik.touchdynamic.services;

import android.view.MotionEvent;
import android.view.View;

import com.example.michalik.touchdynamic.objects.TouchMeasurement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalik on 22.10.16
 */

public class TouchService implements View.OnTouchListener{
    View aquisitionArea;
    private List<TouchMeasurement> touchMeasurementList;

    public List<TouchMeasurement> getTouchMeasurementList() {
        return touchMeasurementList;
    }

    public TouchService(View aquisitionArea) {
        this.aquisitionArea = aquisitionArea;
    }
    public void init(){
        touchMeasurementList = new ArrayList<>();
        aquisitionArea.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                touchMeasurementList.add(new TouchMeasurement(event.getRawX(), event.getRawY(), TouchMeasurement.ACTION_UP));
                break;
            case MotionEvent.ACTION_DOWN:
                touchMeasurementList.add(new TouchMeasurement(event.getRawX(), event.getRawY(), TouchMeasurement.ACTION_DOWN));
                break;
        }
        return false;
    }
}
