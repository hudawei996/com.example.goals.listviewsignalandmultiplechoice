package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goals.listviewsignalandmultiplechoice.R;

public class MultiThreadActivity extends Activity {
    Button button;
    TextView text;
    Runnable mRunnable;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0x123){
                text.setText("Task Done!!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);//耗时任务完成时在该TextView上显示文本

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);//模拟耗时任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(0x123);
                //text.setText("Task Done!!");//在非UI线程之外去访问UI组件
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(mRunnable);
                thread.start();
            }
        });
    }
}
