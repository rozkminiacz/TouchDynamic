package com.example.michalik.touchdynamic.services;

import com.example.michalik.touchdynamic.objects.FullMeasurementObject;
import com.example.michalik.touchdynamic.objects.UserData;

import io.realm.Realm;

/**
 * Created by michalik on 22.10.16
 */

public class RealmHandler {
    public static void saveUserData(UserData data){

    }
    public static UserData getUserData(){
        return new UserData();
    }
//    public static void saveMeasurements(FullMeasurementObject measurementObject){
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        realm.copyToRealm(measurementObject);
//
//        realm.commitTransaction();
//        realm.close();
//    }
}