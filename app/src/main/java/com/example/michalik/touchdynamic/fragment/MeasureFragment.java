package com.example.michalik.touchdynamic.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michalik.touchdynamic.R;
import com.example.michalik.touchdynamic.objects.AccelerometerMeasurement;
import com.example.michalik.touchdynamic.objects.FullMeasurementObject;
import com.example.michalik.touchdynamic.objects.MeasureSettings;
import com.example.michalik.touchdynamic.objects.RealmMeasureInfo;
import com.example.michalik.touchdynamic.objects.TouchMeasurement;
import com.example.michalik.touchdynamic.services.AccelerometerService;
import com.example.michalik.touchdynamic.services.DataStreamConnector;
import com.example.michalik.touchdynamic.services.TouchService;
import com.example.michalik.touchdynamic.utils.CsvWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * @TODO
 * add control views
 * add measurement settings as dialog
 */
public class MeasureFragment extends Fragment{

    public static final String TAG  = MeasureFragment.class.getSimpleName();

    private AccelerometerService accelerometerService;
    private TouchService touchService;
    private List<TouchMeasurement> touchMeasurementList;

    @BindView(R.id.fragment_measure_field)
    ImageView aquisitionField;
    @BindView(R.id.fragment_measure_start)
    Button startButton;
    @BindView(R.id.fragment_measure_send)
    Button sendButton;
    @BindView(R.id.fragment_measure_reset)
    Button resetButton;

    private Realm realm;
    private RealmMeasureInfo measureInfo;

    public MeasureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
//        aquisitionField = (ImageView) container.findViewById(R.id.fragment_measure_field);
        ButterKnife.bind(this, view);
        accelerometerService = new AccelerometerService(getContext());
        touchService = new TouchService(aquisitionField);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsDialog();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endMeasurement();
                try{
                    saveData(touchService.getTouchMeasurementList(), accelerometerService.getMeasurements());
                }
                catch (Exception e){
                    Log.d(TAG, "onClick: ", e);
                    Snackbar.make(v, "Measurement not completed or connection problems", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void showSettingsDialog() {
        final DialogSettingsFragment dialog = new DialogSettingsFragment();
        dialog.setListener(new DialogSettingsFragment.SettingsAppliedListener() {
            @Override
            public void onApplied(MeasureSettings measureSettings) {
                dialog.dismiss();
                Toast.makeText(getContext(), measureSettings.getPosition(), Toast.LENGTH_SHORT).show();
                startMeasurement(measureSettings);
            }

            @Override
            public void onDismissed() {
                //do nothing
            }
        });
        dialog.show(getFragmentManager(), DialogSettingsFragment.class.getSimpleName());
//        startMeasurement(new MeasureSettings());
    }

//    @OnClick(R.id.fragment_measure_start)
//    public void onStartButtonClick(){
//        startMeasurement();
//    }
//    @OnClick(R.id.fragment_measure_send)
//    public void onSendButtonClick(){
//        endMeasurement();
//        saveData(touchService.getTouchMeasurementList(), accelerometerService.getMeasurements());
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void startMeasurement(MeasureSettings settings){
        touchMeasurementList = new ArrayList<>();
        //get time in ms
        initTouchService();
        initAccelerometerService();
    }
    private void endMeasurement(){
        //get time in ms
        endTouchService();
        endAccelerometerService();
    }

    private void initTouchService(){
        touchService.init();
        Log.d(TAG, "initTouchService: initializing...");
    }

    private void endTouchService(){

    }

    private void initAccelerometerService(){
        accelerometerService.init();
    }

    private void endAccelerometerService(){
        accelerometerService.uinit();

    }

    private void saveData(List<TouchMeasurement> touchMeasurementList, List<AccelerometerMeasurement> accelerometerMeasurementList) throws Exception{
        Log.d(TAG, "saveData: "+touchMeasurementList.size());
        FullMeasurementObject m = new FullMeasurementObject(touchMeasurementList, accelerometerMeasurementList);

        realm = Realm.getDefaultInstance();

        RealmMeasureInfo realmMeasureInfo = new RealmMeasureInfo();
        realmMeasureInfo.setMeasureId(m.getId());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmMeasureInfo);
        realm.commitTransaction();

        CsvWrapper csvWrapper = new CsvWrapper(m, new CsvWrapper.FileReadyListener() {
            @Override
            public void fileReady(final File file, String id) {
                DataStreamConnector ds = new DataStreamConnector();
                ds.setListener(new DataStreamConnector.UploadListener() {
                    @Override
                    public void onSuccess(String msg, String id) {

                        if(file.getName().contains("acc")){
                            measureInfo.setAccURL(msg);
                        }
                        if(file.getName().contains("touch")){
                            measureInfo.setTouchURL(msg);
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
//                        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                if(file.getName().contains("acc")){
                    ds.sendAccFileToServer(file, id, DataStreamConnector.ACC_ENDPOINT);
                }
                if(file.getName().contains("touch")){
                    ds.sendTouchFileToServer(file, id, DataStreamConnector.TOUCH_ENDPOINT);
                }

            }

            @Override
            public void failedToCreate() {
//                Snackbar.make(getActivity().getCurrentFocus(), "Failed do create file", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_SHORT).show();
            }
        });
        csvWrapper.createAccFile(getContext());
        csvWrapper.createTouchFile(getContext());
    }
}
