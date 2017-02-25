package com.example.goals.listviewsignalandmultiplechoice;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 特别注意universalImageLoader需要在MainApplication中初始化
         */
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}
