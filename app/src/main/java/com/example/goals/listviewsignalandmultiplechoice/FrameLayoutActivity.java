package com.example.goals.listviewsignalandmultiplechoice;import android.support.v4.widget.SwipeRefreshLayout;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.View;import android.widget.Toast;//public class FrameLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {public class FrameLayoutActivity extends AppCompatActivity  {    public SwipeRefreshLayout refreshLayout;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_frame_layout);//        refreshLayout = (SwipeRefreshLayout) findViewById(refresh);//        refreshLayout.setOnRefreshListener(this);//        refreshLayout.setRefreshing(true);    }    public void bundlUnitCard(View view) {        Toast.makeText(this, "dianji", Toast.LENGTH_LONG).show();    }    public void gotoOldUnitCard(View view) {        Toast.makeText(this, "dianji", Toast.LENGTH_LONG).show();    }    /*@Override    public void onRefresh() {        Toast.makeText(this, "dianji", Toast.LENGTH_LONG).show();    }*/}