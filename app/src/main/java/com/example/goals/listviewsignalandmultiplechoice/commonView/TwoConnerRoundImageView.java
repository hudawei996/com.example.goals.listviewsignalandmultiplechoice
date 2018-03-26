package com.example.goals.listviewsignalandmultiplechoice.commonView;

import android.widget.ImageView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.example.goals.listviewsignalandmultiplechoice.R;

/**
 * 重绘的ImageView
 * Created by huyongqiang on 2017/10/31.
 * email:262489227@qq.com
 */
public class TwoConnerRoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap mImageBtp;
    private Rect mImageSrcRect, mImageDesRect;
    private Rect mBtmRect;
    private float mTotalWidth;
    private float mTotalHeight;
    private float mRadus;

    public TwoConnerRoundImageView(Context context, int with, int height, float radus) {
        super(context);
        mTotalWidth = with;
        mTotalHeight = height;
        mRadus = radus;
    }

    public TwoConnerRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TwoConnerRoundImageView);
        mTotalWidth = array.getDimensionPixelSize(R.styleable.TwoConnerRoundImageView_width, 0);
        mTotalHeight = array.getDimensionPixelSize(R.styleable.TwoConnerRoundImageView_height, 0);
        mRadus = array.getDimensionPixelSize(R.styleable.TwoConnerRoundImageView_radus, 0);
        array.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mImageDesRect = new Rect(0, 0, (int) mTotalWidth, (int) mTotalHeight);
        mBtmRect = new Rect(0, 0, (int) mTotalWidth, (int) (mTotalHeight + mRadus));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        canvas.drawColor(Color.WHITE);
        int saveCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, paint, Canvas.ALL_SAVE_FLAG);
        if (mImageBtp != null) {
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(new RectF(mBtmRect), mRadus, mRadus, paint);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(mImageBtp, mImageSrcRect, mImageDesRect, paint);
            paint.setXfermode(null);
        }
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mImageBtp = bm;
        reCalculateRect();
    }

    private void reCalculateRect() {
        mImageSrcRect = new Rect(0, 0, mImageBtp.getWidth(), mImageBtp.getHeight());
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mImageBtp = ((BitmapDrawable) getResources().getDrawable(resId)).getBitmap();
        reCalculateRect();
    }

}