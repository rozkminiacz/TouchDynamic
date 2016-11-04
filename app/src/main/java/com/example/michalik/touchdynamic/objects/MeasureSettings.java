package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;

/**
 * Created by michalik on 23.10.16
 */

public class MeasureSettings extends RealmObject {

    //could be saved as default
    private String age;
    private String tired;
    private String gender;
    private String position;

    //should be changed for each measurement
    private String finger;
    private String hand;
    private String desiredBPM;
    private String devicePosition;

    public String getDevicePosition() {
        return devicePosition;
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

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public String getDesiredBPM() {
        return desiredBPM;
    }

    public void setDesiredBPM(String desiredBPM) {
        this.desiredBPM = desiredBPM;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }
}
