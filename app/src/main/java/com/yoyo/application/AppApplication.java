package com.yoyo.application;

import android.app.Application;

import com.yoyo.test01.MyApplicationLifeCycleCallBacks;

/**
 * Created by liumin on 2015/4/22.
 */
public class AppApplication extends Application {
    public int mForegroundActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("appapplication oncreate...");
        registerActivityLifecycleCallbacks(new MyApplicationLifeCycleCallBacks());
    }
}
