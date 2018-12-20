package com.example.goals.listviewsignalandmultiplechoice.view.androidHeroesView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class VolumeView extends View {

    private int mWidth;
    private int mRectWidth;
    private int mRectHeight;
    private Paint mPaint;
    private int mRectCount;
    private int offset = 5;
    private double mRandom;
    private LinearGradient mLinearGradient;

    public VolumeView(Context context) {
        super(context);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mRectCount = 12;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //整个view的宽
        mWidth = getWidth();

        //小矩形的宽，高
        mRectHeight = getHeight();
        mRectWidth = (int) (mWidth * 0.6 / mRectCount);

        //线性梯度
        mLinearGradient = new LinearGradient(
                0,
                0,
                mRectWidth,//梯度宽
                mRectHeight,//梯度高
                Color.YELLOW,//最终颜色
                Color.BLUE,//初始颜色
                Shader.TileMode.CLAMP);
        //画笔设置线性梯度的颜色变化
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制mRectCount个矩形
        for (int i = 0; i < mRectCount; i++) {

            mRandom = Math.random();
            //随机高度
            float currentHeight = (float) (mRectHeight * mRandom);

            //绘制矩形（左，上，右，下）
            canvas.drawRect(
                    (float) (mWidth * 0.4 / 2 + mRectWidth * i + offset),
                    currentHeight,
                    (float) (mWidth * 0.4 / 2 + mRectWidth * (i + 1)),
                    mRectHeight,
                    mPaint);
        }

        canvas.drawRect(0,0,300,200,mPaint);
        //矩形宽：right-left = width
        //矩形高：top - bottom = height
        //（left,top)左上角点
        //（right,bottom）右下角点


        //每延迟300毫秒，重新绘制
        postInvalidateDelayed(300);
    }
}
