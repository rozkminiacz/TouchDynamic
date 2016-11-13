package com.example.michalik.touchdynamic.objects;

/**
 * Created by michalik on 02.11.16
 */

public class MeasureDataRequest {

    String userId;
    String touchURL;
    String accURL;
    String age;
    String tired;
    String gender;
    String position;
    String hand;
    String finger;
    String desiredBPM;
    String devicePosition;
    String realFieldSize;
    String dpi;

    public MeasureDataRequest(){

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTired() {
        return tired;
    }

    public void setTired(String tired) {
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

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getDesiredBPM() {
        return desiredBPM;
    }

    public void setDesiredBPM(String desiredBPM) {
        this.desiredBPM = desiredBPM;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public String getRealFieldSize() {
        return realFieldSize;
    }

    public void setRealFieldSize(String realFieldSize) {
        this.realFieldSize = realFieldSize;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public void applySettings(MeasureSettings measureSettings) {
        this.age=measureSettings.getAge();
        this.desiredBPM=measureSettings.getDesiredBPM();
        this.hand=measureSettings.getHand();
        this.tired=measureSettings.getTired();
        this.position=measureSettings.getPosition();
        this.finger=measureSettings.getFinger();
        this.gender=measureSettings.getGender();
        //@TODO - uzupełnić
    }
}
