package com.example.goals.listviewsignalandmultiplechoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;


/**
 * ListView优化机制及滑动时数据时出现的数据错乱重复问题
 * http://blog.csdn.net/huanongjingchao/article/details/42918869
 */
public class MultipleChoiceActivity extends AppCompatActivity {
    public ListView lvMultiple;

    public MultiplePayCardAdapter payCardAdapter;

    public HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        lvMultiple = (ListView) findViewById(R.id.lvMultiple);
        payCardAdapter = new MultiplePayCardAdapter();
        lvMultiple.setAdapter(payCardAdapter);

        hashMap = new HashMap<>();
    }

    public class MultiplePayCardAdapter extends BaseAdapter {
        private final String TAG = SignalChoiceActivity.PayCardAdapter.class.getName();
        int selectedIndex;
        private Animation animation;

        public MultiplePayCardAdapter() {
            animation = AnimationUtils.loadAnimation(MultipleChoiceActivity.this, R.anim.bottom_in_anim);
            selectedIndex = -1;
        }

        public void setSelectedIndex(int index) {
            selectedIndex = index;
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final Holder holder;
            if (view == null) {
                holder = new Holder();
                view = LayoutInflater.from(MultipleChoiceActivity.this).inflate(R.layout.item_pay_card, null);

                /**
                 * 找到控件句柄
                 */
                //holder.ivPayModeIcon = (ImageView) view.findViewById(R.id.ivPayModeIcon);
                //holder.tvPayModeName = (TextView) view.findViewById(R.id.tvPayModeName);
                holder.imageView = (ImageView) view.findViewById(R.id.imageView);

                /**
                 * 点击事件监听，修改：数据集合+控件样式
                 */
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View innerView) {
                        int selectPosition = (int) innerView.findViewById(R.id.imageView).getTag();
                        Log.i(TAG, "onClick: 被点击的位置是：" + selectPosition);
                        //记录当前被点击的位置
                        payCardAdapter.setSelectedIndex(selectPosition);

                        ImageView imageView = (ImageView) innerView.findViewById(R.id.imageView);

                        if (imageView.getVisibility() == View.VISIBLE) {
                            // do something
                            imageView.setVisibility(View.INVISIBLE);
                            hashMap.remove(selectPosition + "");

                        } else {
                            imageView.setVisibility(View.VISIBLE);
                            hashMap.put(selectPosition + "", selectPosition + "");

                        }

                        //通知数据更新
                        payCardAdapter.notifyDataSetChanged();

                        Log.i(TAG, "onClick:选中位置： " + selectPosition + "多选集合：" + hashMap.toString());
                    }
                });

                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            /**
             * 赋值(显示用）
             */
            //holder.tvPayModeName.setText("微信支付");
            //holder.ivPayModeIcon.setImageBitmap();

            //把每个位置标记放到imageView的tag中去
            holder.imageView.setTag(position);
            //从hashmap中找到是否选中：选中将图片标识，未选中将图片不标
            if (hashMap.containsKey(position + "")) {
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.imageView.setVisibility(View.INVISIBLE);
            }

            view.startAnimation(animation);

            return view;
        }

        class Holder {
            TextView tvPayCardNum;
            TextView tvPayCardRemain;
            ImageView imageView;
        }
    }
}
