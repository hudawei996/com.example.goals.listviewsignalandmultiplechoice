package com.example.goals.listviewsignalandmultiplechoice.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.goals.listviewsignalandmultiplechoice.R;

public class SignalChoiceActivity extends AppCompatActivity {
    public ListView lvSingle;

    public PayCardAdapter payCardAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_choice);

        lvSingle = (ListView) findViewById(R.id.lvSingle);

        payCardAdapter = new PayCardAdapter();
        lvSingle.setAdapter(payCardAdapter);
    }

    public class PayCardAdapter extends BaseAdapter {
        private final String TAG = PayCardAdapter.class.getName();
        int selectedIndex;

        public PayCardAdapter() {
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
                view = LayoutInflater.from(SignalChoiceActivity.this).inflate(R.layout.item_pay_card, null);

                /**
                 * 初始化控件
                 */
//                holder.ivPayModeIcon = (ImageView) view.findViewById(R.id.ivPayModeIcon);
//                holder.tvPayModeName = (TextView) view.findViewById(R.id.tvPayModeName);
                holder.imageView = (ImageView) view.findViewById(R.id.imageView);

                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            /**
             * 设置显示数据
             */
            //holder.tvPayModeName.setText("微信支付");
            //holder.ivPayModeIcon.setImageBitmap();

            //选中，未选中位置设置相应的状态
            if (selectedIndex == position) {
                holder.imageView.setVisibility(View.VISIBLE);//显示打钩
            } else {
                holder.imageView.setVisibility(View.INVISIBLE);//不显示打钩
            }

            /**
             * 点击事件，修改：数据集+被点击者的样式
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //记录当前被点击的位置
                    payCardAdapter.setSelectedIndex(position);
                    //通知数据更新
                    payCardAdapter.notifyDataSetChanged();

                    Log.i(TAG, "onClick:选中位置： " + position);
                }
            });

            return view;
        }

        class Holder {
            TextView tvPayCardNum;
            TextView tvPayCardRemain;
            ImageView imageView;
        }
    }
}
