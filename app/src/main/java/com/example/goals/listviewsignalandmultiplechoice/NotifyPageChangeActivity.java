package com.example.goals.listviewsignalandmultiplechoice;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * android进行异步更新UI的四种方式
 * https://segmentfault.com/a/1190000003702775
 * 1. 使用Handler消息传递机制
 * 2. 使用AsyncTask异步任务
 * 3. 使用runOnUiThread(action)方法
 * 4. 使用Handler的post(Runnabel r)方法
 * <p>
 * 总之：就是在非UI线程中，通知UI线程更新视图
 */
public class NotifyPageChangeActivity extends AppCompatActivity {

    private TextView tv;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                tv.setText("更新后的TextView");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_page_change);

        tv = (TextView) findViewById(R.id.tv);

        setTitle("4中方法通知页面更新");
    }

    /**
     * 1.使用Handler消息传递机制
     *
     * @param view
     */
    public void handler(View view) {
        new MyThread().start();
    }


    /**
     * 2. 使用AsyncTask异步任务
     *
     * @param view
     */
    public void asynNotify(View view) {
        new Yibu().execute();
    }

    /**
     * 3. 使用runOnUiThread(action)方法
     *
     * @param view
     */
    public void runOnUiThread(View view) {
        new MyThread2().start();
    }

    /**
     * 4. 使用Handler的post(Runnabel r)方法
     *
     * @param view
     */
    public void handlerPost(View view) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //延迟两秒更新
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv.setText("更新后的TextView");
            }
        });
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            //延迟两秒更新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x123);
        }
    }


    class Yibu extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            tv.setText("更新后的TextView");
        }

    }

    class MyThread2 extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        //延迟两秒更新
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tv.setText("更新后的TextView");
                }
            });
        }
    }

    /**
     * 初始化测试文字内容
     *
     * @param view
     */
    public void initText(View view) {
        tv.setText("测试更新内容");
    }
}
