package com.seewo.videoviewtest;

import android.app.Application;

/**
 * Created by admin on 2018/5/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashRestartHelper.getInstance().init(this);
    }

}
