package com.example.michalik.touchdynamic;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by michalik on 22.10.16
 */

public class App extends Application {
    public static StorageReference storageReference;
    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        storageReference = FirebaseStorage.getInstance().getReference();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("config.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
    public static App getInstance(){
        return app;
    }
}
