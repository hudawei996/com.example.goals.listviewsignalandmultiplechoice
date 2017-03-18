package com.example.goals.listviewsignalandmultiplechoice;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnListMultipleChoice;
    private Button btnGoogleCards;
    private PopupWindow popupWindow;
    private View popupWinParent;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListMultipleChoice = (Button) findViewById(R.id.btnListMultipleChoice);
        btnGoogleCards = (Button) findViewById(R.id.btnGoogleCards);
        popupWinParent = findViewById(R.id.popupWinParent);
        imageView = (ImageView) findViewById(R.id.ivAdd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给显示弹出框按钮添加动画效果
                addAnimation();
                if (popupWindow == null) {
                    popupWin();
                } else if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(imageView, -300, 0);
                }
            }
        });


    }

    public void signalChoice(View view) {
//        startActivity(new Intent(this, SignalChoiceActivity.class));

        Intent intent = new Intent(MainActivity.this, SignalChoiceActivity.class);
        //
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void multipleChoice(View view) {
//        startActivity(new Intent(this, MultipleChoiceActivity.class));

        Intent intent = new Intent(MainActivity.this, MultipleChoiceActivity.class);
        //渐渐显示
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void googleCardsView(View view) {
//        startActivity(new Intent(this, GoogleCardsActivity.class));

        Intent intent = new Intent(MainActivity.this, GoogleCardsActivity.class);
        //吊炸天的特效
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, btnGoogleCards, "shareNames").toBundle());

    }

    public void topPopupWin(View view) {
        if (popupWindow == null) {
            popupWin();
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.showAsDropDown(popupWinParent, 0, 0);
            }
        }
    }

    private void popupWin() {
        popupWindow = new PopupWindow(this);
        View inflateView = LayoutInflater.from(this).inflate(R.layout.popup_win_layout, null);
        popupWindow.setContentView(inflateView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置PopupWindow宽
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置PopupWindow高
        popupWindow.showAsDropDown(popupWinParent, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupAnim);//设置动画
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(MainActivity.this, "PupWindow消失了！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAnimation() {//加入了旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);//设置动画时间
        imageView.setAnimation(rotateAnimation);//设置动画
        imageView.startAnimation(rotateAnimation);//开始动画
    }

    /**
     * 跳转到frameLayout，测试frameLayout中有两层布局，第一层布局的点击效果会受影响
     *
     * @param view
     */
    public void gotoFrameLayout(View view) {
        startActivity(new Intent(this, FrameLayoutActivity.class));
    }
}
