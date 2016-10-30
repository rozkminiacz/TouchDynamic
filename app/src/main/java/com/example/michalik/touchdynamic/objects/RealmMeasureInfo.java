package com.example.michalik.touchdynamic.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by michalik on 30.10.16
 */

public class RealmMeasureInfo extends RealmObject{
    @PrimaryKey
    String measureId;

    String userId;
    String touchURL;
    String accURL;

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
