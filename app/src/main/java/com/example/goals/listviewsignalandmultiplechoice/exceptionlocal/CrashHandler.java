package com.example.goals.listviewsignalandmultiplechoice.exceptionlocal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.goals.listviewsignalandmultiplechoice.MyApp;

/**
 * Created by huyongqiang on 2017/4/7.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private static CrashHandler mInstance = null;
    // 系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private CrashHandler(Context c) {
        // 获取系统默认的异常处理器
        mDefaultCrashHandler = Thread
                .getDefaultUncaughtExceptionHandler();
        // 将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 获取Context，方便内部使用
        mContext = c.getApplicationContext();
    }

    public synchronized static CrashHandler create(Context cxt) {
        if (mInstance == null) {
            mInstance = new CrashHandler(cxt);
        }
        return mInstance;
    }

    /**
     * 当UncaughtException发生时会回调该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // TODO Auto-generated method stub
        StringBuffer logInfo = new StringBuffer();
        logInfo.append("Exception: ");
        logInfo.append(ex.getMessage());
        logInfo.append("\r\n");
        logInfo.append(getStackMsg(ex));
        logInfo.append("Phone informations:");
        logInfo.append("\r\n");
        try {
            logInfo.append(getPhoneInfo());
            // 导出异常信息到存储器中
            CrashLog.writeLog(logInfo.toString());
        } catch (Exception e) {
            //忽略异常
        } finally {
            // 如果系统提供了默认的异常处理器，则交给系统去结束程序，
            if (mDefaultCrashHandler != null) {
                mDefaultCrashHandler.uncaughtException(thread, ex);
            } else {
                MyApp.getInstance().exit();
            }
        }
    }

    private static String getStackMsg(Throwable e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\r\n");
        }
        return sb.toString();
    }

    private String getPhoneInfo() throws PackageManager.NameNotFoundException {
        StringBuffer info = new StringBuffer();

        // 应用的版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        info.append("App Version Name: ");
        info.append(pi.versionName);
        info.append("\r\n");
        // android版本号
        info.append("OS Version: ");
        info.append(Build.VERSION.RELEASE);
        info.append("_");
        info.append(Build.VERSION.SDK_INT);
        info.append("\r\n");
        // 手机制造商
        info.append("Vendor: ");
        info.append(Build.MANUFACTURER);
        info.append("\r\n");
        // 手机型号
        info.append("Model: ");
        info.append(Build.MODEL);
        info.append("\r\n");
        // cpu架构
        info.append("CPU ABI: ");
        info.append(Build.CPU_ABI);
        info.append("\r\n");

        return info.toString();
    }
}
