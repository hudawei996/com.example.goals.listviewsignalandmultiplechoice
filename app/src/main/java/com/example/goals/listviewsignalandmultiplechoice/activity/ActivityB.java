package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityB extends BaseActivity {

    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("第B页");

        LinearLayout layout = new LinearLayout(this);
        Button btn = new Button(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(1);
            }
        });

        /*for (int i = 0; i < content.length(); i++) {

        }*/

        btn.setText("ActivityB");
        layout.addView(btn);
        setContentView(layout);
    }
}
