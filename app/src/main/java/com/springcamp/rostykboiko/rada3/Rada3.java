package com.springcamp.rostykboiko.rada3;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Rada3 extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
}
}
