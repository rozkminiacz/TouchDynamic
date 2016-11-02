package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;

/**
 * Created by michalik on 23.10.16
 */

public class MeasureSettings extends RealmObject {

    //could be saved as default
    private int age;
    private int tired;
    private String gender;
    private String position;

    //should be changed for each measurement
    private int finger;
    private String hand;
    private int desiredBPM;
    private String devicePosition;

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
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
}
