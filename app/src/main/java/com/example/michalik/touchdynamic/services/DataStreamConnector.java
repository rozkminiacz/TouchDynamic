package com.example.michalik.touchdynamic.services;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by michalik on 23.10.16
 */

public class DataStreamConnector {
    //send via bluetooth
    //send via local conneciton
    //send to server
    //send file to endpoint
    private static final String TAG = DataStreamConnector.class.getSimpleName();
    private StorageReference firebaseReference;
    private UploadListener uploadListener;

    public DataStreamConnector(){
        this.firebaseReference = FirebaseStorage.getInstance().getReference();
    }

    public void sendFileToCloud(File csvFile){
        Uri uri = Uri.fromFile(csvFile);
        StorageReference storageReference = firebaseReference.child("data/"+csvFile.getName());
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        if(uploadListener!=null){
                            uploadListener.onSuccess(downloadUri.toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ", e);
                        if(uploadListener!=null){
                            uploadListener.onFailure(e.getMessage());
                        }
                    }
                });
    }

    public void setListener(UploadListener uploadListener){
        this.uploadListener = uploadListener;
    }

    public interface UploadListener{
        void onSuccess(String msg);
        void onFailure(String msg);
    }
}
