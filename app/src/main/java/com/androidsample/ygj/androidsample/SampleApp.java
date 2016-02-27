package com.androidsample.ygj.androidsample;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by YGJ on 2015/11/4 0004.
 */
public class SampleApp extends Application {
   private static SampleApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());

        CrashReport.initCrashReport(getApplicationContext(),"900012846",false);

        Log.i("start","应用程序启动");
    }

    public static SampleApp getInstance(){
        if(instance==null)
            instance = new SampleApp();
        return instance;
    }
}
