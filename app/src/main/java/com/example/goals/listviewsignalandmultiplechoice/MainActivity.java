package com.example.goals.listviewsignalandmultiplechoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void signalChoice(View view) {
        startActivity(new Intent(this, SignalChoiceActivity.class));
    }

    public void multipleChoice(View view) {
        startActivity(new Intent(this, MultipleChoiceActivity.class));

    }
}
