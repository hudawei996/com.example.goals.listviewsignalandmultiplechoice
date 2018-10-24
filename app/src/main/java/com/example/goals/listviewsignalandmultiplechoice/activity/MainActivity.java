package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goals.listviewsignalandmultiplechoice.EditTextMagicEffect;
import com.example.goals.listviewsignalandmultiplechoice.R;
import com.example.goals.listviewsignalandmultiplechoice.affinityActivity.FirstActivity;
import com.example.goals.listviewsignalandmultiplechoice.crash.CrashTestActivity;
import com.example.goals.listviewsignalandmultiplechoice.hotfixTest.HotFixActivity;
import com.example.goals.listviewsignalandmultiplechoice.rxBus.EventRxBus;
import com.example.goals.listviewsignalandmultiplechoice.rxBus.RxBus;
import com.example.goals.listviewsignalandmultiplechoice.util.GetPostUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    private Button btnListMultipleChoice;
    private Button btnGoogleCards;
    private PopupWindow popupWindow;
    private View popupWinParent;
    private ImageView imageView;
    private Button get, post;
    private TextView tvEventBus;
    private TextView mTextView;


    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        initView();

        // 获得从URL过来的参数
        getURLData();

        initURLTestButton();
    }

    private void initView() {
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
        tvEventBus = (TextView) findViewById(R.id.tvEventBus);
        //提示信息
        tvEventBus.setText("EventBus消息从FinishAffinity测试中来");
        mTextView = findViewById(R.id.text_view);
        setHtmlText(mTextView);
    }

    private void setHtmlText(TextView textView) {
        /*<p style="text-align:left">居左</p>
<p style="text-align:right">居右</p>
<p style="text-align:center">剧中</p>*/

        String htmlText = "<p style=\"text-align:left\">居左</p>\n" +
                "<p style=\"text-align:left\">居右</p>\n" +
                "<p style=\"text-align:center\">剧中</p>";

        String htmlText1 = "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p style=\"background:#F00\"; align=\"right\">This is some text in a paragraph.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        String LeftText = "居左";
        String RightText = "居右";
        final String resultText = LeftText + "  " + RightText;
        final SpannableString styledResultText = new SpannableString(resultText);
        styledResultText.setSpan((
                        new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE)),
                LeftText.length() + 2,
                LeftText.length() + 2 + RightText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(styledResultText);
    }

    private void initURLTestButton() {

        get = (Button) this.findViewById(R.id.get);
        post = (Button) this.findViewById(R.id.post);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread() {
                    @Override
                    public void run() {
                        response = GetPostUtil.sendGet("http://www.jju.edu.cn/", null);
                        Log.i("response", response);
                    }
                }.start();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread() {
                    @Override
                    public void run() {
                        response = GetPostUtil.sendPost("http://www.jju.edu.cn/", null);
//                      Log.i("response", response);
                    }
                }.start();
            }
        });
    }

    /**
     * 获得从URL过来的参数
     * <p>
     * 测试打开连接地址，https://huyongqiang.github.io/testHtmlOpenApp.html
     */
    private void getURLData() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String arg0 = "arg0 =" + uri.getQueryParameter("arg0");
            String arg1 = "arg1 =" + uri.getQueryParameter("arg1");
            /*另外还有以下几个API来获取相关信息：
            getIntent().getScheme(); //获得Scheme名称
            getIntent().getDataString(); //获得Uri全部路径
            getIntent().getHost(); //获得host
            */
            Toast.makeText(this, "URL传递过来的参数是" + arg0 + "," + arg1, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    public void finishAffinityTest(View view) {
        startActivity(new Intent(this, FirstActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    /**
     * 订阅eventBus事件
     * 用一个详细的类型来区分事件，否则是string类型的消息不能区分，只能一一去匹配
     * 建议定义的类型：{eventCode：XXX，message：XXX}
     *
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(String message) {
        tvEventBus.setText(message);
    }

    public void rxBus(View view) {
        startActivity(new Intent(this, RxBusTestActivity.class));

        //注册接收RxBusEvent
        receiverRxBusEvent();
    }

    /**
     * 注册接收RxBusEvent
     */
    private void receiverRxBusEvent() {
        RxBus.getDefault().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {
                if (event instanceof EventRxBus) {
                    //do something
                    tvEventBus.setText("RxBusEvent,Code:" + ((EventRxBus) event).getEventCode() + ",Msg:" + ((EventRxBus) event)
                            .getEventMsg());
                }/*else if(event instanceof otherEvent){
                    //do otherthing
                }*/
            }
        });
    }


    /**
     * 四种通知页面更新的页面中去
     *
     * @param view
     */
    public void gotoNotifyLayoutChange(View view) {
        startActivity(new Intent(this, NotifyPageChangeActivity.class));
    }

    /**
     * 跳转到输入框特效
     *
     * @param view
     */
    public void gotoMagicEditText(View view) {
        startActivity(new Intent(this, EditTextMagicEffect.class));
    }

    /**
     * 跳转到viewpager特效
     *
     * @param view
     */
    public void gotoViewPagerEffect(View view) {
        startActivity(new Intent(this, ViewPagerEffectActivity.class));
    }

    /**
     * 跳转到viewpager特效2
     *
     * @param view
     */
    public void gotoViewPagerEffect2(View view) {
        startActivity(new Intent(this, ViewPagerEffect2Activity.class));
    }

    /**
     * 跳转到viewpager特效2
     *
     * @param view
     */
    public void gotoActivityLuanch(View view) {
        startActivity(new Intent(this, ActivityLuanchStudy.class));
    }

    public void gotoToolBarLearn(View view) {
        startActivity(new Intent(this, ToolbarLearnActivity.class));
    }

    public void gotoHotFix(View view) {
        startActivity(new Intent(this, HotFixActivity.class));
    }

    public void gotoStatusButton(View view) {
        startActivity(new Intent(this, StatusButtonActivity.class));
    }

    public void gotoTestCrash(View view) {
        startActivity(new Intent(this, CrashTestActivity.class));
    }

    public void gotoTestMultiThread(View view) {
        startActivity(new Intent(this, MultiThreadActivity.class));
    }
}
