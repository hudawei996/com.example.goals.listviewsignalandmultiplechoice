package com.example.goals.listviewsignalandmultiplechoice.crash;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Date: 2018/5/2.
 * Description:
 *
 * @author huyongqiang
 */

public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String LOGTAG = "SimpleUncaughtExceptionHandler";

    @SuppressLint("LongLogTag")
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        ex.printStackTrace(printWriter);
        String errorReport = result.toString();
        Log.i(LOGTAG, "uncaughtException errorReport=" + errorReport);
    }

}