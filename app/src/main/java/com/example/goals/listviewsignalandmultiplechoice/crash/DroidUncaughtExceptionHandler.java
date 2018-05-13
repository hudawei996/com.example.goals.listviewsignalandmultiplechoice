package com.example.goals.listviewsignalandmultiplechoice.crash;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * Date: 2018/5/2.
 * Description:
 *
 * @author huyongqiang
 */

public class DroidUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String LOGTAG = "DroidUncaughtExceptionHandler";
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    private Context mAppContext;

    public DroidUncaughtExceptionHandler(Context context) {
        mAppContext = context.getApplicationContext();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static String getProcessName(Context appContext) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String processName = getProcessName(mAppContext);
        if (mAppContext.getPackageName().equals(processName)) {
            Log.i(LOGTAG, "uncaughtException main process");
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        } else {
            Log.i(LOGTAG, "uncaughtException process name=" + processName);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
