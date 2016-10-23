package com.example.michalik.touchdynamic.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michalik.touchdynamic.R;
import com.example.michalik.touchdynamic.objects.AccelerometerMeasurement;
import com.example.michalik.touchdynamic.objects.FullMeasurementObject;
import com.example.michalik.touchdynamic.objects.TouchMeasurement;
import com.example.michalik.touchdynamic.services.AccelerometerService;
import com.example.michalik.touchdynamic.services.DataStreamConnector;
import com.example.michalik.touchdynamic.services.RealmHandler;
import com.example.michalik.touchdynamic.services.TouchService;
import com.example.michalik.touchdynamic.utils.CSVwrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * @TODO
 * add control views
 * add measurement settings as dialog
 */
public class MeasureFragment extends Fragment{


    private AccelerometerService accelerometerService;
    private TouchService touchService;
    private ImageView aquisitionField;
    private List<TouchMeasurement> touchMeasurementList;

    @BindView(R.id.fragment_measure_start)
    Button startButton;
    @BindView(R.id.fragment_measure_send)
    Button sendButton;
    @BindView(R.id.fragment_measure_reset)
    Button resetButton;
    @BindView(R.id.fragment_measure_settings)
    Button settingsButton;


    public MeasureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        aquisitionField = (ImageView) container.findViewById(R.id.fragment_measure_field);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        accelerometerService = new AccelerometerService(getContext());
        touchService = new TouchService(aquisitionField);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void startMeasurement(){
        touchMeasurementList = new ArrayList<>();
        //get time in ms
        initTouchService();
        initAccelerometerService();
    }
    private void endMeasurement(){
        //get time in ms
        endTouchService();
        endAccelerometerService();
        saveData(touchService.getTouchMeasurementList(), accelerometerService.getMeasurements());
    }

    private void initTouchService(){
        touchService.init();
    }

    private void endTouchService(){

    }

    private void initAccelerometerService(){
        accelerometerService.init();
    }

    private void endAccelerometerService(){
        accelerometerService.uinit();

    }

    private void saveData(List<TouchMeasurement> touchMeasurementList, List<AccelerometerMeasurement> accelerometerMeasurementList){
        FullMeasurementObject m = new FullMeasurementObject(touchMeasurementList, accelerometerMeasurementList);
        CSVwrapper csVwrapper = new CSVwrapper(m, new CSVwrapper.FileReadyListener() {
            @Override
            public void fileReady(File file) {
                DataStreamConnector ds = new DataStreamConnector();
                ds.setListener(new DataStreamConnector.UploadListener() {
                    @Override
                    public void onSuccess(String msg) {
//                        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String msg) {
//                        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failedToCreate() {
//                Snackbar.make(getActivity().getCurrentFocus(), "Failed do create file", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_SHORT).show();
            }
        });
        csVwrapper.createFile(getContext());
    }
}
