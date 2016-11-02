package com.example.michalik.touchdynamic.objects;

/**
 * Created by michalik on 02.11.16
 */

public class MeasureDataRequest {

    private String userId;
    private String touchURL;
    private String accURL;
    private int age;
    private int tired;
    private String gender;
    private String position;
    private String hand;
    private int finger;
    private int desiredBPM;
    private String devicePosition;

    public MeasureDataRequest(String userId,
                              String touchURL,
                              String accURL,
                              int age,
                              int tired,
                              String gender,
                              String position,
                              String hand,
                              int finger,
                              int desiredBPM,
                              String devicePosition) {
        this.userId = userId;
        this.touchURL = touchURL;
        this.accURL = accURL;
        this.age = age;
        this.tired = tired;
        this.gender = gender;
        this.position = position;
        this.hand = hand;
        this.finger = finger;
        this.desiredBPM = desiredBPM;
        this.devicePosition = devicePosition;
    }

    public static MeasureDataRequest fromRealm(RealmMeasureInfo measureInfo){
        return new MeasureDataRequest(
                measureInfo.getUserId(),
                measureInfo.getTouchURL(),
                measureInfo.getAccURL(),
                measureInfo.getAge(),
                measureInfo.getTired(),
                measureInfo.getGender(),
                measureInfo.getPosition(),
                measureInfo.getHand(),
                measureInfo.getFinger(),
                measureInfo.getDesiredBPM(),
                measureInfo.getDevicePosition());
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

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public int getFinger() {
        return finger;
    }

    public void setFinger(int finger) {
        this.finger = finger;
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
}
