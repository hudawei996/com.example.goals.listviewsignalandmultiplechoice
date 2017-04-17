package com.example.goals.listviewsignalandmultiplechoice.util;

import android.util.Log;

import com.example.goals.listviewsignalandmultiplechoice.MyApp;

/**
 *
 */
public class LogUtil {
    public static final boolean DEBUG = MyApp.getInstance().isTest();
    public final static int maxLogSize = 400000;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                Log.d(tag, msg.substring(start, end));
            }
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                Log.v(tag, msg.substring(start, end));
            }
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                Log.i(tag, msg.substring(start, end));
            }
        }
    }


    public static void e(String tag, String msg) {
        if (DEBUG) {
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                Log.e(tag, msg.substring(start, end));
            }
        }
    }

    public static void e(String tag, Exception e) {
        if (DEBUG) {
            Log.e(tag, e.getMessage(), e);
        }
    }

    public static void e(Exception e) {
        e.getStackTrace();
    }
}