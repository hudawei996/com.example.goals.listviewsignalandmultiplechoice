package com.example.goals.listviewsignalandmultiplechoice.crash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.goals.listviewsignalandmultiplechoice.R;
import com.example.goals.listviewsignalandmultiplechoice.activity.MainActivity;

/**
 * Date: 2018/5/2.
 * Description:
 *
 * @author huyongqiang
 */

public class CrashTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_test);
        findViewById(R.id.causeNPE).setOnClickListener(this);
        findViewById(R.id.startSickService).setOnClickListener(this);
    }



    private void causeNPE() {
        String s = null;
        s.length();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.causeNPE) {
            causeNPE();
        } else if (viewId == R.id.startSickService) {
            startService(new Intent(CrashTestActivity.this, DroidService.class));
        }
    }
}
