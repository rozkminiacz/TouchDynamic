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

public class CSVwrapper {

    public static final String TAG = CSVwrapper.class.getSimpleName();

    private final FullMeasurementObject measurementObject;
    private static final String separator = ",";
    private static final String newline = "\n";
    private final FileReadyListener listener;

    public CSVwrapper(FullMeasurementObject measurementObject, FileReadyListener listener){
        this.measurementObject=measurementObject;
        this.listener=listener;
    }
    public void createFile(Context context){
        String filename = measurementObject.getUserData().getId()+"_"+measurementObject.getId();

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
            Log.d(TAG, "createFile: ", e);
            listener.failedToCreate();
        }
        if(file!=null && file.exists() && file.isFile()){
            listener.fileReady(file);
        }
    }

    private String appendDataSeparator() {
     return "##################################";
    }

    private String appendDataTouch(){
        String out;
        StringBuilder sb = new StringBuilder();
        sb.append("#, eventTime, type, x, y\n");
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
                    .append(separator)
                    .append(newline);
        }

        return sb.toString();
    }

    private String appendDataAcc(){
        StringBuilder sb = new StringBuilder();
        List<AccelerometerMeasurement> accList = measurementObject.getAccelerometerMeasurements();

        sb.append("#, eventTime, x, y, z\n");
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
        void fileReady(File file);
        void failedToCreate();
    }
}
