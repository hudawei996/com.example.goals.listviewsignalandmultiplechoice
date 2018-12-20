package com.goals.viewinjecthu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@ContentViewInject(value = R.layout.activity_main)
//public class MainActivity extends Activity implements View.OnClickListener {
public class MainActivity extends Activity {
  @ViewInject(R.id.id_btn)
  private Button mBtn1;
  @ViewInject(R.id.id_btn02)
  private Button mBtn2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ViewInjectUtils.inject(this);

//    mBtn1.setOnClickListener(this);
//    mBtn2.setOnClickListener(this);
  }

  /*@Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.id_btn:
        Toast.makeText(MainActivity.this, "Why do you click me ?",
          Toast.LENGTH_SHORT).show();
        break;

      case R.id.id_btn02:
        Toast.makeText(MainActivity.this, "I am sleeping !!!",
          Toast.LENGTH_SHORT).show();
        break;
    }
  }*/

  @OnClick({R.id.id_btn, R.id.id_btn02})
  public void clickBtnInvoked(View view) {
    switch (view.getId()) {
      case R.id.id_btn:
        Toast.makeText(this, "Inject Btn01 !", Toast.LENGTH_SHORT).show();
        break;
      case R.id.id_btn02:
        Toast.makeText(this, "Inject Btn02 !", Toast.LENGTH_SHORT).show();
        break;
    }
  }

}
