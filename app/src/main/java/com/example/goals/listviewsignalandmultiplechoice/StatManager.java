package com.example.goals.listviewsignalandmultiplechoice;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by huyongqiang on 2017/7/24.
 * email:262489227@qq.com
 */

public class StatManager {

    private StatManager() {
    }


    public static void init(Context context) {
        if (MyApp.getInstance().isTest()) {
            MobclickAgent.setDebugMode(true);// 打开调试模式
        } else {
            MobclickAgent.setDebugMode(false);// 关闭调试模式
        }
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }


    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    public static void onPageStart(String pageName) {
        MobclickAgent.onPageStart(pageName);
    }

    public static void onPageEnd(String pageName) {
        MobclickAgent.onPageEnd(pageName);
    }


}
