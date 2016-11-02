package com.example.michalik.touchdynamic.utils;

import com.example.michalik.touchdynamic.objects.FileUploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by michalik on 30.10.16
 */

public interface RetrofitInterface {
    @Multipart
    @PUT("/touchfiles")
    Call<List<FileUploadResponse>> uploadTouch(@Part MultipartBody.Part file);

    @Multipart
    @PUT("/accfiles")
    Call<List<FileUploadResponse>> uploadAcc(@Part MultipartBody.Part file);

}
