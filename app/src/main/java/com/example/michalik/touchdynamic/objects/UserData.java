package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;

/**
 * Created by michalik on 21.10.16
 */

public class UserData extends RealmObject{
    private String id;
    private String name;

    public UserData(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
