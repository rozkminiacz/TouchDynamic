package com.example.michalik.touchdynamic.services;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.michalik.touchdynamic.objects.FileUploadResponse;
import com.example.michalik.touchdynamic.objects.MeasureDataRequest;
import com.example.michalik.touchdynamic.utils.RetrofitInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

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
    public static final String TOUCH_ENDPOINT = "touchfiles";
    public static final String ACC_ENDPOINT = "accfiles";
    private static final String BASE_URL = "http://139.59.213.12:81/";
    public static final String TOUCH = "TOUCH";
    public static final String ACC = "ACC";
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

    public void sendTouchFileToServer(final File csvFile, final String type, final String ENDPOINT){

        switch (ENDPOINT){
            case TOUCH_ENDPOINT:
                break;
            case ACC_ENDPOINT:
                break;
        }

        Retrofit retrofit = retrofitCreator();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), csvFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", csvFile.getName(), file);

//        String descriptionString = type;
//        RequestBody description =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), type);
//
        retrofitInterface.uploadTouch(body).enqueue(new Callback<List<FileUploadResponse>>() {
            @Override
            public void onResponse(Call<List<FileUploadResponse>> call, Response<List<FileUploadResponse>> response) {
                Log.d(TAG, "onResponse: "+response.body().size());
                uploadListener.onSuccess(response.body().get(0).getFilename(), response.body().get(0).getId());
            }

            @Override
            public void onFailure(Call<List<FileUploadResponse>> call, Throwable t) {

            }
        });
//        retrofitInterface.uploadTouch(body).enqueue(new Callback<FileUploadResponse>() {
//            @Override
//            public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
//                Log.d(TAG, "onResponse: ");
//                if(uploadListener!=null){
//
//                    uploadListener.onSuccess(response.body().getFilename(), response.body().getId());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure: ", t);
//                uploadListener.onFailure(t.getMessage());
//            }
//        });
    }

    public void sendAccFileToServer(final File csvFile, final String type, final String ENDPOINT){

        switch (ENDPOINT){
            case TOUCH_ENDPOINT:
                break;
            case ACC_ENDPOINT:
                break;
        }

        Retrofit retrofit = retrofitCreator();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), csvFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", csvFile.getName(), file);


        retrofitInterface.uploadAcc(body).enqueue(new Callback<List<FileUploadResponse>>() {
            @Override
            public void onResponse(Call<List<FileUploadResponse>> call, Response<List<FileUploadResponse>> response) {
                Log.d(TAG, "onResponse: "+response.body().size());
                if(uploadListener!=null){
                    uploadListener.onSuccess(response.body().get(0).getFilename(), response.body().get(0).getId());
                }
            }

            @Override
            public void onFailure(Call<List<FileUploadResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: ", t);
                uploadListener.onFailure(t.getMessage());
            }
        });
    }
    public void postMeasureData(MeasureDataRequest measureData){
        Retrofit retrofit = retrofitCreator();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        retrofitInterface.postMeasureInfo(measureData).enqueue(new Callback<MeasureDataRequest>() {
            @Override
            public void onResponse(Call<MeasureDataRequest> call, Response<MeasureDataRequest> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if(uploadListener!=null){
                    uploadListener.onSuccess(response.message(), "");
                }
            }

            @Override
            public void onFailure(Call<MeasureDataRequest> call, Throwable t) {
                Log.d(TAG, "onFailure: ", t);
                uploadListener.onFailure(t.getMessage());
            }
        });
    }


    public void setListener(UploadListener uploadListener){
        this.uploadListener = uploadListener;
    }

    private static Retrofit retrofitCreator(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface UploadListener{
        void onSuccess(String msg, String measureId);
        void onFailure(String msg);
    }
}
