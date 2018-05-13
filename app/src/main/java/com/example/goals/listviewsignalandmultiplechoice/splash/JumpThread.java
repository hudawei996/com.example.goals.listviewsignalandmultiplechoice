package com.example.goals.listviewsignalandmultiplechoice.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.goals.listviewsignalandmultiplechoice.R;
import com.example.goals.listviewsignalandmultiplechoice.activity.MainActivity;

/**
 * Created by huyongqiang on 2017/7/24.
 * email:262489227@qq.com
 */

public class JumpThread implements Runnable {
    private TextView textView;
    private int numberTime;
    private Activity activity;

    public JumpThread(TextView textView, int numberTime, Activity activity) {
        this.textView = textView;
        this.numberTime = numberTime;
        this.activity = activity;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //String.valueOf(numberTime) 跳转后面空格显示，直接写numberTime空格不显示。
            textView.setText("跳转   " + String.valueOf(numberTime));
            if (numberTime == 0) {
                jumpActivity();
            }
        }
    };

    private void jumpActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        //activity切换动画
        activity.overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);

        activity.finish();
    }

    @Override
    public void run() {
        try {
            while (numberTime > 0) {
                Thread.sleep(1000);
                numberTime--;
                handler.sendMessage(new Message());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
