package com.example.goals.listviewsignalandmultiplechoice;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by huyongqiang on 2017/7/24.
 * email:262489227@qq.com
 */
public class MultiDexApplication extends Application {
    public MultiDexApplication() {
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
