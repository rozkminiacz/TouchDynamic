package com.example.michalik.touchdynamic.services;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.michalik.touchdynamic.objects.FileuploadResponse;
import com.example.michalik.touchdynamic.utils.RetrofitInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public String accReference;
    public String touchReference;
    public static final String TOUCH_ENDPOINT = "";
    public static final String ACC_ENDPOINT = "";

    public DataStreamConnector(){
        this.firebaseReference = FirebaseStorage.getInstance().getReference();
    }

    public void sendFileToCloud(final File csvFile, final String id){
        Uri uri = Uri.fromFile(csvFile);
        StorageReference storageReference = firebaseReference.child("data/"+csvFile.getName());
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        if(uploadListener!=null){
                            if(csvFile.getName().contains("acc")){
                                accReference = taskSnapshot.getDownloadUrl().toString();
                            }
                            if(csvFile.getName().contains("touch")){
                                touchReference = taskSnapshot.getDownloadUrl().toString();
                            }
                            uploadListener.onSuccess(downloadUri.toString(), id);
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

    public void sendFileToServer(final File csvFile){

        Retrofit retrofit = build(TOUCH_ENDPOINT);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), csvFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", csvFile.getName(), file);

        retrofitInterface.uploadTouch(body).enqueue(new Callback<FileuploadResponse>() {
            @Override
            public void onResponse(Call<FileuploadResponse> call, Response<FileuploadResponse> response) {
                Log.d(TAG, "onResponse: ");       
            }

            @Override
            public void onFailure(Call<FileuploadResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public void setListener(UploadListener uploadListener){
        this.uploadListener = uploadListener;
    }

    private static Retrofit build(String endpoint){
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface UploadListener{
        void onSuccess(String msg, String measureId);
        void onFailure(String msg);
    }
}
