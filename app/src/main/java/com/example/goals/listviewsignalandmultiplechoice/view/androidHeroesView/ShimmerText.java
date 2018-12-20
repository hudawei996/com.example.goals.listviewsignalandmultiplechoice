package com.example.goals.listviewsignalandmultiplechoice.view.androidHeroesView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Date: 2018/11/2.
 * Description:
 *
 * @author huyongqiang
 */
public class ShimmerText extends android.support.v7.widget.AppCompatTextView {
  int mViewWidth;
  int mViewHeigh;
  Paint mPaint;
  LinearGradient mLinearGradient;
  Matrix mGradientMatrix;
  int mTranslate;

  public ShimmerText(Context context) {
    super(context);
  }

  public ShimmerText(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (mViewWidth == 0) {
      mViewWidth = getMeasuredWidth();

      if (mViewWidth > 0) {
        mPaint = getPaint();
        mLinearGradient = new LinearGradient(
          0,
          0,
          mViewWidth,
          0,
          new int[]{
            Color.BLUE, 0xffffffff, Color.BLUE
          },
          null,
          Shader.TileMode.CLAMP);

        //画笔设置线性梯度。
        mPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();
      }
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mGradientMatrix != null) {
      /*mTranslate += mViewWidth / 5;
      if (mTranslate > 2 * mViewWidth) {
        mTranslate = -mViewWidth;
      }
      mGradientMatrix.setTranslate(mTranslate, 0);
      mLinearGradient.setLocalMatrix(mGradientMatrix);
      postInvalidateDelayed(100);*/


      //每次重绘，距离偏移5分之一
      mTranslate = mTranslate + mViewWidth / 5;

      //如果偏移距离大于宽度的两倍
      if (mTranslate > 2 * mViewWidth) {
        mTranslate = -mViewWidth;
      }

      //TODO 不太明白这里的设置逻辑，为什么要设置矩阵偏移，再是知道线性梯度中去。
      //矩阵是用来移动的
      mGradientMatrix.setTranslate(mTranslate, 0);
      //线性梯度是用来控制颜色变化
      mLinearGradient.setLocalMatrix(mGradientMatrix);

      //延迟100毫秒执行
      postInvalidateDelayed(100);
    }
  }
}
