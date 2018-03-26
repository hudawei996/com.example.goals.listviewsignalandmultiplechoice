package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ActivityLuanchStudy extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("第一页");

        LinearLayout layout = new LinearLayout(this);
        Button btn = new Button(this);
        btn.setText("跳转到A");
        layout.addView(btn);
        setContentView(layout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLuanchStudy.this, ActivityA.class);
                startActivity(intent);
            }
        });
    }
}
