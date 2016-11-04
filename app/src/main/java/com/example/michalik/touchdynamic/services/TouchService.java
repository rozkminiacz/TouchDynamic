package com.example.michalik.touchdynamic.services;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.michalik.touchdynamic.objects.TouchMeasurement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalik on 22.10.16
 */

public class TouchService implements View.OnTouchListener{
    public static final String TAG = TouchService.class.getSimpleName();
    ImageView aquisitionArea;
    private List<TouchMeasurement> touchMeasurementList;

    public List<TouchMeasurement> getTouchMeasurementList() {
        return touchMeasurementList;
    }

    public TouchService(ImageView aquisitionArea) {
        this.aquisitionArea = aquisitionArea;
    }
    public void init(){
        touchMeasurementList = new ArrayList<>();
        aquisitionArea.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch: ");

        if(event.getAction() == MotionEvent.ACTION_UP){
            touchMeasurementList.add(new TouchMeasurement(event.getRawX(), event.getRawY(), TouchMeasurement.ACTION_UP));
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchMeasurementList.add(new TouchMeasurement(event.getRawX(), event.getRawY(), TouchMeasurement.ACTION_DOWN));
        }
        return true;
    }

    public void uinit() {
        aquisitionArea.setOnTouchListener(null);
    }
}
