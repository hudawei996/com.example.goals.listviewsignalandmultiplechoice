package com.example.goals.listviewsignalandmultiplechoice;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.TextView;

public class EditTextMagicEffect extends AppCompatActivity implements TextWatcher {

    public TextInputLayout til_mobile;
    public TextInputEditText et_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_magic_effect);

        til_mobile = (TextInputLayout) findViewById(R.id.til_mobile);
        et_mobile = (TextInputEditText) findViewById(R.id.et_mobile);

        et_mobile.addTextChangedListener(this);
        //til_mobile.setError("error input");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() != 0){
            til_mobile.setError("error input");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
