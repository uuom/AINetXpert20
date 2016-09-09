package com.asiainfo.netxpert;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class App extends Application {

    public static List<Activity> activities = new ArrayList<>();
    private static Context appContext;


    @Override
    public void onCreate(){
        super.onCreate();
        StrictMode.enableDefaults();
        appContext = getApplicationContext();
    }

    public static Context getContext(){
        return appContext;
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void removeAll(){
        for (Activity activity : activities) {
            if (activity != null){
                activity.finish();
            }
        }
    }
}
