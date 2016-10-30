package com.example.michalik.touchdynamic.utils;

import android.content.Context;
import android.util.Log;

import com.example.michalik.touchdynamic.objects.AccelerometerMeasurement;
import com.example.michalik.touchdynamic.objects.FullMeasurementObject;
import com.example.michalik.touchdynamic.objects.TouchMeasurement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by michalik on 22.10.16
 */

public class CsvWrapper {

    public static final String TAG = CsvWrapper.class.getSimpleName();

    private final FullMeasurementObject measurementObject;
    private static final String separator = ",";
    private static final String newline = "\n";
    private final FileReadyListener listener;

    public CsvWrapper(FullMeasurementObject measurementObject, FileReadyListener listener){
        this.measurementObject=measurementObject;
        this.listener=listener;
    }
    public void createAccFile(Context context){
        String filename = "acc_"+measurementObject.getId()+".csv";

        FileOutputStream fileOutputStream;
        File file = new File(context.getFilesDir(), filename);
        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(appendHeader().getBytes());
            fileOutputStream.write(appendCreationDate().getBytes());
            fileOutputStream.write(appendDataAcc().getBytes());
            fileOutputStream.close();
        }
        catch (IOException e){
            Log.d(TAG, "createFullFile: ", e);
            listener.failedToCreate();
        }
        if(file!=null && file.exists() && file.isFile()){
            listener.fileReady(file, measurementObject.getId());
        }
    }
    public void createTouchFile(Context context){
        String filename = "touch_"+measurementObject.getId()+".csv";

        FileOutputStream fileOutputStream;
        File file = new File(context.getFilesDir(), filename);
        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(appendHeader().getBytes());
            fileOutputStream.write(appendCreationDate().getBytes());
            fileOutputStream.write(appendDataTouch().getBytes());
            fileOutputStream.close();
        }
        catch (IOException e){
            Log.d(TAG, "createFullFile: ", e);
            listener.failedToCreate();
        }
        if(file!=null && file.exists() && file.isFile()){
            listener.fileReady(file, measurementObject.getId());
        }
    }
    public void createFullFile(Context context){
        String filename = measurementObject.getUserData().getId()+"_"+measurementObject.getId()+".csv";

        FileOutputStream fileOutputStream;
        File file = new File(context.getFilesDir(), filename);
        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(appendHeader().getBytes());
            fileOutputStream.write(appendCreationDate().getBytes());
            fileOutputStream.write(appendDataTouch().getBytes());
            fileOutputStream.write(appendDataSeparator().getBytes());
            fileOutputStream.write(appendDataAcc().getBytes());
            fileOutputStream.close();
        }
        catch (IOException e){
            Log.d(TAG, "createFullFile: ", e);
            listener.failedToCreate();
        }
        if(file!=null && file.exists() && file.isFile()){
            listener.fileReady(file, measurementObject.getId());
        }
    }

    private String appendDataSeparator() {
     return "##################################\n";
    }

    private String appendDataTouch(){
        String out;
        StringBuilder sb = new StringBuilder();
        sb.append("eventTime, type, x, y\n");
        List<TouchMeasurement> touchMeasurements = measurementObject.getTouchMeasurements();
        for(TouchMeasurement t : touchMeasurements){

            sb
                    .append(t.getEventTime())
                    .append(separator)
                    .append(t.getType())
                    .append(separator)
                    .append(t.getX())
                    .append(separator)
                    .append(t.getY())
//                    .append(separator)
                    .append(newline);
        }

        return sb.toString();
    }

    private String appendDataAcc(){
        StringBuilder sb = new StringBuilder();
        List<AccelerometerMeasurement> accList = measurementObject.getAccelerometerMeasurements();

        sb.append("eventTime, x, y, z\n");
        for (AccelerometerMeasurement ac : accList){
            sb.
                    append(ac.getEventTime())
                    .append(separator)
                    .append(ac.getX())
                    .append(separator)
                    .append(ac.getY())
                    .append(separator)
                    .append(ac.getZ())
                    .append(newline);
        }

        return sb.toString();
    }
    private String appendCreationDate(){
        return "#"+Calendar.getInstance().getTime().toString()+newline;
    }
    private String appendHeader(){
        return "#"+measurementObject.getId()+newline+"#"+measurementObject.getUserData().getId();
    }
    public interface FileReadyListener{
        void fileReady(File file, String id);
        void failedToCreate();
    }
}
