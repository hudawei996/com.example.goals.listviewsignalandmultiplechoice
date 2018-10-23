package com.example.goals.imageviewshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mImageView1 = findViewById(R.id.image1);

        mImageView1.setImageDrawable(getDrawable(R.drawable.wide_bg));
    }
}
