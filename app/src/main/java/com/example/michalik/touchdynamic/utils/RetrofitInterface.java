package com.example.michalik.touchdynamic.utils;

import com.example.michalik.touchdynamic.objects.FileuploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by michalik on 30.10.16
 */

public interface RetrofitInterface {
    @Multipart
    @POST("touch")
    Call<FileuploadResponse> uploadTouch(@Part MultipartBody.Part file);
}
