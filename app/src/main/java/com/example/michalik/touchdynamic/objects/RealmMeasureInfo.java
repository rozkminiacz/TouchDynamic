package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by michalik on 30.10.16
 */

public class RealmMeasureInfo extends RealmObject{
    @PrimaryKey
    private String measureId;

    private String userId;
    private String touchURL;
    private String accURL;

    private int age;
    private int tired;
    private String gender;
    private String position;

    //should be changed for each measurement
    private int finger;
    private String hand;
    private int desiredBPM;
    private String devicePosition;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTired() {
        return tired;
    }

    public void setTired(int tired) {
        this.tired = tired;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getFinger() {
        return finger;
    }

    public void setFinger(int finger) {
        this.finger = finger;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public int getDesiredBPM() {
        return desiredBPM;
    }

    public void setDesiredBPM(int desiredBPM) {
        this.desiredBPM = desiredBPM;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTouchURL() {
        return touchURL;
    }

    public void setTouchURL(String touchURL) {
        this.touchURL = touchURL;
    }

    public String getAccURL() {
        return accURL;
    }

    public void setAccURL(String accURL) {
        this.accURL = accURL;
    }
}
