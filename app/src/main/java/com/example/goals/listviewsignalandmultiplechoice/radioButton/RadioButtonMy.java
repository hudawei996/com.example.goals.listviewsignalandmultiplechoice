package com.example.goals.listviewsignalandmultiplechoice.radioButton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.goals.listviewsignalandmultiplechoice.R;

/**
 * Date: 2018/5/13.
 * Description:
 *
 * @author huyongqiang
 */

public class RadioButtonMy extends android.support.v7.widget.AppCompatRadioButton {
    public RadioButtonMy(Context context) {
        super(context);

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.radio_button_my, this);
    }

    public RadioButtonMy(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
