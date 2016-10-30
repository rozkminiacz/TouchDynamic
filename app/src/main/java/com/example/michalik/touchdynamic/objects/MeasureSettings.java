package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;

/**
 * Created by michalik on 23.10.16
 */

public class MeasureSettings extends RealmObject {

    private int age;
    private int tired;
    private String gender;
    private String position;

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
