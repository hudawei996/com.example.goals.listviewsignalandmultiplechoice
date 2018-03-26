package com.example.goals.listviewsignalandmultiplechoice;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_picture)
    ImageView iv_picture;
    @BindView(R.id.iv_default)
    ImageView iv_default;
    @BindView(R.id.tv_jump)
    TextView tv_jump;

    private String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493095641000&di=a7a9b52b6d531a8a0a4f25fff85b2471&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e41b5788a3fd0000018c1bb8d896.png";
    private static SplashActivity splashActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashActivity = this;
//        hideStatusBar();
        ButterKnife.bind(this);
        iv_default.setBackgroundResource(R.mipmap.p_login_bg);
        Glide.with(SplashActivity.this).load(imgUrl)
                .centerCrop()
                .placeholder(R.mipmap.p_login_bg)
                .error(R.mipmap.p_login_bg)
                .into(iv_picture);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_default.setVisibility(View.GONE);
                tv_jump.setVisibility(View.VISIBLE);
                tv_jump.setText("跳转　3");
                Thread t = new Thread(new JumpThread(tv_jump,3,splashActivity));
                t.start();
            }
        }, 2000);
    }
}
