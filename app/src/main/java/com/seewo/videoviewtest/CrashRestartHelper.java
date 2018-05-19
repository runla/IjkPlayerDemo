package com.seewo.videoviewtest;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by admin on 2018/5/8.
 * 描述：异常捕获
 */

public class CrashRestartHelper implements UncaughtExceptionHandler {
    public static final String TAG = CrashRestartHelper.class.getSimpleName();
    private static CrashRestartHelper INSTANCE = new CrashRestartHelper();
    private Context mAppContext;
    private UncaughtExceptionHandler mDefaultHandler;

    private CrashRestartHelper() {
    }

    public static CrashRestartHelper getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mAppContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "App Crash");
        if(!this.handleException(ex) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Intent intent = this.mAppContext.getPackageManager().getLaunchIntentForPackage(this.mAppContext.getPackageName());
            intent.addFlags(268468224);
            this.mAppContext.startActivity(intent);
            Process.killProcess(Process.myPid());
            System.exit(0);
            System.gc();
        }

    }

    private boolean handleException(Throwable ex) {
        return ex != null;
    }
}

