package com.example.goals.listviewsignalandmultiplechoice;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ViewPagerEffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_effect);
        setTitle("viewpager特效");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb);
        toolbar.setTitle("viewpager特效");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
    }
}
