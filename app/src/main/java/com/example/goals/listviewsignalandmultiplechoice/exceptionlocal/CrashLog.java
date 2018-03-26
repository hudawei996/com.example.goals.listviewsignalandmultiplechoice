package com.example.goals.listviewsignalandmultiplechoice.exceptionlocal;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

/**
 * Created by huyongqiang on 2017/4/7.
 * <p>
 * 用户在本地保存crash日志
 */

public class CrashLog {
    private static Context mContext;
    public static final String DIR_FOR_APP = "myApp";
    public static final String FILE_NAME_FOR_CRASH__LOG = "crash.txt";

    public static void setContext(Context aContext) {
        mContext = aContext.getApplicationContext();
    }

    public static void writeLog(String log) throws Exception {
        File dir = createFileDir(DIR_FOR_APP);
        File file = new File(dir.getPath(), FILE_NAME_FOR_CRASH__LOG);
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuffer orgStr = new StringBuffer();
        FileReader fr = new FileReader(file);
        int ch = 0;
        while ((ch = fr.read()) != -1) {
            orgStr.append((char) ch);
        }
        fr.close();

        Calendar c = Calendar.getInstance();
        String time = String.format("%2d:", c.get(Calendar.HOUR)) +
                String.format("%2d:", c.get(Calendar.MINUTE)) +
                String.format("%2d", c.get(Calendar.SECOND)) + " ";
        String str = orgStr.toString() + "\r\n" + time + log + "\r\n";
        FileWriter fw = new FileWriter(file);
        fw.write(str);
        fw.close();
    }

    public static void clearLog() {
        String dirPath = getDirPath(DIR_FOR_APP);
        if (dirPath != null) {
            File file = new File(FILE_NAME_FOR_CRASH__LOG);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static String getDirPath(String dirName) {
        String filePath = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 如果有存储器，则返回存储器的路径
            filePath = Environment.getExternalStorageDirectory()
                    + File.separator + dirName;
        } else {// 否则返回data目录的路径
            filePath = mContext.getCacheDir().getPath() + File.separator
                    + dirName;
        }
        return filePath;
    }

    public static File createFileDir(String dirName) {
        String filePath = getDirPath(dirName);
        if (filePath == null) {
            return null;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir;
    }
}
