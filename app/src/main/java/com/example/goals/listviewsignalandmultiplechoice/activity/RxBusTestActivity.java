package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.goals.listviewsignalandmultiplechoice.R;
import com.example.goals.listviewsignalandmultiplechoice.rxBus.EventRxBus;
import com.example.goals.listviewsignalandmultiplechoice.rxBus.RxBus;

public class RxBusTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_test);


    }

    public void rxBusMsg(View view) {
        RxBus.getDefault().send(new EventRxBus(0,"RxBusMsg"));
    }
}
