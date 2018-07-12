package com.example.goals.cameraselfdefine;

/**
 * Date: 2018/7/6.
 * Description:
 *
 * @author huyongqiang
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class ScannerView extends View {

    private int screenWidth;
    private int screenHeight;

    private int viewWidth;
    private int viewHeight;

    private int rectWidth;
    private int rectHeight;

    private int rectTop;
    private int rectLeft;
    private int rectRight;
    private int rectBottom;

    private int topTextSize = 40;
    private int bottomTextSize = 14;

    private int topTextMarginBottom = 40;
    private int bottomTextMarginTop = 16;

    private int lineLen;
    private int lineWidht;
    private static final int LINE_WIDTH = 8;
    private static final int TOP_BAR_HEIGHT = 48;
    private static final int BOTTOM_BTN_HEIGHT = 48;
    private static final int LEFT_PADDING = 20;
    private static final int RIGHT_PADDING = 20;
    private static String textStrTop = "身份证正面";
    private static String textStrBottom = "将身份证正面置于此区域，并对其扫描框边缘";

    private Paint backgroundPaint;
    private Paint linePaint;
    private Paint textPaintTop;
    private Paint textPaintBottom;
    private Paint transparentPaint;

    int scannerType;//0:行驶证，1：身份证
    int cardWidth = 0;
    int cardHeight = 0;

    private Rect rect;
    private Rect rectBackGround;
    private Rect rectTransparent;
    private int baseline;

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        screenWidth = UnitUtils.getScreenWidth(context);
        screenHeight = UnitUtils.getScreenHeight(context);

        //透明取景框需要根据不同拍摄内容，调整长宽比
        viewHeight = screenHeight - UnitUtils.dp2px(context, TOP_BAR_HEIGHT + BOTTOM_BTN_HEIGHT);
        viewWidth = screenWidth;

        if (scannerType == 0) {
            cardWidth = 86;
            cardHeight = 60;
            textStrTop = "行驶证首页";
            textStrBottom = "将行驶证首页置于此区域，并对其扫描框边缘";
        } else {
            cardWidth = 160;
            cardHeight = 100;
            textStrTop = "身份证正面";
            textStrBottom = "将身份证正面置于此区域，并对其扫描框边缘";
        }

        rectWidth = screenWidth - UnitUtils.dp2px(context, LEFT_PADDING + RIGHT_PADDING);
        rectHeight = (rectWidth * cardHeight / cardWidth);
        // 相对于此view
        rectTop = (viewHeight - rectHeight) / 2;
        rectLeft = (viewWidth - rectWidth) / 2;
        rectBottom = rectTop + rectHeight;
        rectRight = rectLeft + rectWidth;
        lineLen = UnitUtils.dp2px(context, 20);


        //绘制整个背景，半透明
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Style.FILL);
        backgroundPaint.setAlpha(150);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(3);
        rectBackGround = new Rect(0, 0, screenWidth, screenHeight);

        //绘制透明取景框
        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(Color.TRANSPARENT);
        transparentPaint.setStyle(Style.FILL);
        transparentPaint.setAntiAlias(true);
        transparentPaint.setStrokeWidth(3);
        // 实例化混合模式
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        transparentPaint.setXfermode(porterDuffXfermode);
        rectTransparent = new Rect(rectLeft, rectTop, rectRight, rectBottom);

        //画线
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.SQUARE);
        linePaint.setStrokeWidth(LINE_WIDTH);// 设置线宽
        linePaint.setAlpha(255);

        //绘制上边文字
        textPaintTop = new TextPaint();
        textPaintTop.setAntiAlias(true);
        textPaintTop.setStrokeWidth(3);
        textPaintTop.setColor(Color.WHITE);
        textPaintTop.setTextSize(UnitUtils.sp2px(context, topTextSize));
        textPaintTop.setTextAlign(Paint.Align.CENTER);

        //绘制下边的文字（不会自动换行）
        /*textPaintBottom = new TextPaint();
        textPaintBottom.setAntiAlias(true);
        textPaintBottom.setStrokeWidth(1);
        textPaintBottom.setColor(Color.WHITE);
        textPaintBottom.setTextSize(UnitUtils.sp2px(context, 16));
        textPaintBottom.setTextAlign(Paint.Align.CENTER);*/

        //矩形
        rect = new Rect(rectLeft, rectTop - 80, rectRight, rectTop - 10);
        FontMetricsInt fontMetrics = textPaintTop.getFontMetricsInt();
        baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    }

    public void setScannerType(int scannerType) {
        this.scannerType = scannerType;
        //设置后重绘制
        initView(getContext());
    }

    public int getScannerType() {
        return this.scannerType;
    }

    public void setTopTextMarginBottom(int marginBottomDp) {
        topTextMarginBottom = marginBottomDp;
    }

    public void setBottomTextMarginTop(int marginTopDp) {
        bottomTextMarginTop = marginTopDp;
    }

    public void setTextStrTop(String textStrTopText) {
        textStrTop = textStrTopText;
    }

    public void setTextStrBottom(String textStrTopText) {
        textStrTop = textStrTopText;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //绘制整个半透明背景
        canvas.drawRect(rectBackGround, backgroundPaint);

        //画上边文字
        canvas.drawText(textStrTop,
                rect.centerX(),
                baseline - UnitUtils.dp2px(getContext(), topTextMarginBottom),
                textPaintTop);

        //画测试框
        /*Paint rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Style.FILL);
        canvas.drawRect(rect,rectPaint);*/

        //画下边文字,不会自动换行
        /*canvas.drawText(textStrBottom,
                rect.centerX(),
                baseline + rectHeight + UnitUtils.dp2px(getContext(), 30),
                textPaintBottom);*/


        //画中间透明取景框
        canvas.drawRect(rectTransparent, transparentPaint);

        //画四个角
        canvas.drawLine(rectLeft, rectTop, rectLeft + lineLen, rectTop,
                linePaint);
        canvas.drawLine(rectRight - lineLen, rectTop, rectRight, rectTop,
                linePaint);
        canvas.drawLine(rectLeft, rectTop, rectLeft, rectTop + lineLen,
                linePaint);
        canvas.drawLine(rectRight, rectTop, rectRight, rectTop + lineLen,
                linePaint);
        canvas.drawLine(rectLeft, rectBottom, rectLeft + lineLen, rectBottom,
                linePaint);
        canvas.drawLine(rectRight - lineLen, rectBottom, rectRight, rectBottom,
                linePaint);
        canvas.drawLine(rectLeft, rectBottom - lineLen, rectLeft, rectBottom,
                linePaint);
        canvas.drawLine(rectRight, rectBottom - lineLen, rectRight, rectBottom,
                linePaint);

        //画下边文字,可以自动换行
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(UnitUtils.sp2px(getContext(), bottomTextSize));
        textPaint.setTextAlign(Paint.Align.CENTER);
        StaticLayout layout = new StaticLayout(textStrBottom, textPaint, rectWidth, Layout.Alignment.ALIGN_CENTER,
                1.0F, 1.0F, true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.translate(rectLeft, baseline + rectHeight + UnitUtils.dp2px(getContext(), bottomTextMarginTop));
        canvas.save();
        layout.draw(canvas);
    }

    public int getRectLeft() {
        return rectLeft;
    }

    public int getRectTop() {
        return rectTop;
    }

    public int getRectRight() {
        return rectRight;
    }

    public int getRectBottom() {
        return rectBottom;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

}
