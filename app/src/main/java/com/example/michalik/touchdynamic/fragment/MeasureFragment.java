package com.example.michalik.touchdynamic.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import com.example.michalik.touchdynamic.objects.MeasureDataRequest;
import com.example.michalik.touchdynamic.objects.MeasureSettings;
import com.example.michalik.touchdynamic.objects.RealmMeasureInfo;
import com.example.michalik.touchdynamic.objects.TouchMeasurement;
import com.example.michalik.touchdynamic.services.AccelerometerService;
import com.example.michalik.touchdynamic.services.DataStreamConnector;
import com.example.michalik.touchdynamic.services.TouchService;
import com.example.michalik.touchdynamic.utils.CsvWrapper;
import com.example.michalik.touchdynamic.utils.Metronome;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private MeasureDataRequest measureDataRequest;

    public MeasureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        ButterKnife.bind(this, view);

        accelerometerService = new AccelerometerService(getContext());
        touchService = new TouchService(aquisitionField);
        measureDataRequest = new MeasureDataRequest();
        measureDataRequest.setUserId(UUID.randomUUID().toString());

        return view;
    }

    @OnClick(R.id.fragment_measure_start)
    public void onStartClicked(){
        final DialogSettingsFragment dialog = new DialogSettingsFragment();
        dialog.setListener(new DialogSettingsFragment.SettingsAppliedListener() {
            @Override
            public void onApplied(MeasureSettings measureSettings) {
                dialog.dismiss();
                Log.d(TAG, "onApplied: ");
                Toast.makeText(getContext(), measureSettings.getPosition(), Toast.LENGTH_SHORT).show();
                setDeviceSettings();
                measureDataRequest.applySettings(measureSettings);
                startMeasurement();
            }

            @Override
            public void onDismissed() {
                //do nothing
            }
        });
        dialog.show(getFragmentManager(), DialogSettingsFragment.class.getSimpleName());
    }

    private void setDeviceSettings(){
        aquisitionField.getHeight();
        aquisitionField.getMeasuredWidth();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        measureDataRequest.setRealFieldSize(aquisitionField.getHeight()+" "+aquisitionField.getWidth());
        measureDataRequest.setDpi(metrics.densityDpi+"");

        Log.d(TAG, "setDeviceSettings: "+aquisitionField.getMeasuredWidth());
    }

    @OnClick(R.id.fragment_measure_send)
    public void onSendButtonClicked(){
        endMeasurement();
        try{
            saveData(touchService.getTouchMeasurementList(), accelerometerService.getMeasurements());
        }
        catch (Exception e){
            Log.d(TAG, "onClick: ", e);
            Snackbar.make(getView(), "Measurement not completed or connection problems", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fragment_measure_post)
    public void onPostClicked(){
        try{
            sendData();
        }
        catch (Exception e){
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT);
            Log.d(TAG, "onPostClicked: ", e);
        }
    }

    private void startMeasurement(){
        touchMeasurementList = new ArrayList<>();
        initMetronome();
        initTouchService();
        initAccelerometerService();
    }

    private void initMetronome() {
        if(measureDataRequest.getDesiredBPM()!=null && !measureDataRequest.getDesiredBPM().isEmpty())
        Metronome.playSound(measureDataRequest.getDesiredBPM(), getActivity());
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
        touchService.uinit();
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

        CsvWrapper csvWrapper = new CsvWrapper(m, new CsvWrapper.FileReadyListener() {
            @Override
            public void fileReady(final File file, String id) {
                DataStreamConnector ds = new DataStreamConnector();
                ds.setListener(new DataStreamConnector.UploadListener() {
                    @Override
                    public void onSuccess(String msg, String id) {

                        if(file.getName().contains("acc")){
                            measureDataRequest.setAccURL(msg);
                        }
                        if(file.getName().contains("touch")){
                            measureDataRequest.setTouchURL(msg);
                        }
                        if(measureDataRequest.getTouchURL()!=null && measureDataRequest.getAccURL()!=null){
                            try{
                                sendData();
                            }
                            catch (Exception e){
                                Log.d(TAG, "onSuccess: ", e);
                            }
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
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
                Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_SHORT).show();
            }
        });
        csvWrapper.createAccFile(getContext());
        csvWrapper.createTouchFile(getContext());
    }

    private void sendData() throws Exception{
        if(measureDataRequest.getAccURL()==null || measureDataRequest.getTouchURL()==null){
            throw new Exception("Files not ready");
        }
        DataStreamConnector ds = new DataStreamConnector();
        ds.setListener(new DataStreamConnector.UploadListener() {
            @Override
            public void onSuccess(String msg, String measureId) {
                Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
            }
        });
        ds.postMeasureData(measureDataRequest);
    }
}
