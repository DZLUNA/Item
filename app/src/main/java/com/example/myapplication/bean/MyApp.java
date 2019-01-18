package com.example.myapplication.bean;

import android.app.Application;

/**
 * Created by 叟 on 2018/12/25.
 */

public class MyApp extends Application {

    public static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;

    }
}
