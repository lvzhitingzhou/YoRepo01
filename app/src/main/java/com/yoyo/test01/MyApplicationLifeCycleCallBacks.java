package com.yoyo.test01;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by liumin on 2015/4/22.
 */
public class MyApplicationLifeCycleCallBacks implements Application.ActivityLifecycleCallbacks{
    private int foregroundActivities;
    private boolean hasSeenFirstActivity;
    private boolean isChangingConfiguration;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        foregroundActivities++;

        if(hasSeenFirstActivity && (foregroundActivities==1) && !isChangingConfiguration){
            applicationDidEnterForeground(activity);
        }
        hasSeenFirstActivity = true;
        isChangingConfiguration = false;
    }

    private void applicationDidEnterForeground(Activity activity) {
        System.out.println("====================> activity enters foreground.");
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        foregroundActivities--;
        if(foregroundActivities == 0){
            applicationDidEnterBackground(activity);
        }
        isChangingConfiguration = activity.isChangingConfigurations();
    }

    private void applicationDidEnterBackground(Activity activity) {
        System.out.println("====================> activity enters background.");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
