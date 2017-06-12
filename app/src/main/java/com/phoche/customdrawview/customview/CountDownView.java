package com.phoche.customdrawview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import com.phoche.customdrawview.R;

import java.util.Calendar;
import java.util.TimeZone;

public class CountDownView extends View {

    private static final String sWAITSTATUS = "距开始  ";
    private static final String sENDSOONSTATUS = "还剩  ";
    private static final String[] sTIMEARRS = {"时", "分", "秒"};
    private static final int sDEFAULT_TEXTSIZE = 20;

    // 倒计时背景色
    private int mTimeBackground = 0xFF4A4A4A;
    // 时间字体色
    private int mTimeTextColor = 0xFFFFFFFF;
    // 中间分割线的颜色
    private int mMiddleLineColor = 0xFFFCD71C;
    // 间距
    private int mOffSetX = 6;


    private boolean[] mNeedDraw = {true, true, true};
    private String[] mTimeArrs = new String[]{"00", "00", "00"};

    private int mTextSize = sDEFAULT_TEXTSIZE;
    // 1 ---- 未开始 ,  0 ------  快结束
    private int mShowStatus = 1;

    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();
    private int mWidth;
    private int mHeight;


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);

        mTextSize = (int) typedArray.getDimension(R.styleable.CountDownView_cdv_textsize,
                mTextSize);
        mTimeTextColor = typedArray.getColor(R.styleable.CountDownView_cdv_countdowncolor,
                mTimeTextColor);
        mTimeBackground = typedArray.getColor(R.styleable.CountDownView_cdv_rectbgcolor,
                mTimeBackground);

        typedArray.recycle();

    }

    private void init() {
        mPaint.setTextSize(mTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int defaultHeight = (int) (getPaddingTop() + getPaddingBottom() + mPaint.measureText
                        (sENDSOONSTATUS));
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = defaultHeight;
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        float defaultWidth;

        if (mShowStatus == 0) {
            defaultWidth = mPaint.measureText(sENDSOONSTATUS);
        } else {
            defaultWidth = mPaint.measureText(sWAITSTATUS);
        }

        for (int i = 0; i < mNeedDraw.length; i++) {
            if (mNeedDraw[i]) {
                defaultWidth = defaultWidth + mPaint.measureText(sTIMEARRS[i]) + mPaint.measureText
                        (mTimeArrs[i]) + mOffSetX * 4;
            }
        }

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
           result = (int) defaultWidth;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int startX = getPaddingLeft();
        float headWidth = 0;
        // 字体基线
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseLineY = (height - fontMetrics.bottom - fontMetrics.top) / 2;
        // 开始语
        mPaint.setColor(mTimeBackground);
        mPaint.setTextSize(mTextSize);
        if (mShowStatus == 0) {
            canvas.drawText(sENDSOONSTATUS, startX, baseLineY, mPaint);
            headWidth = mPaint.measureText(sENDSOONSTATUS);
        } else {
            canvas.drawText(sWAITSTATUS, startX, baseLineY, mPaint);
            headWidth = mPaint.measureText(sWAITSTATUS);
        }

        startX += headWidth;
        int endX = startX;
        for (int i = 0; i < mTimeArrs.length; i++) {
            boolean needDraw = mNeedDraw[i];
            if (!needDraw) continue;
            String timeText = mTimeArrs[i];
            String unit = sTIMEARRS[i];
            int timeWidth = (int) mPaint.measureText(timeText);
            endX += timeWidth + mOffSetX * 2;
            // 背景
            mPaint.setColor(mTimeBackground);
            mPaint.setAntiAlias(true);
            mRectF.left = startX;
            mRectF.top = getPaddingTop();
            mRectF.right = endX;
            mRectF.bottom = getHeight() - getPaddingBottom();
            canvas.drawRoundRect(mRectF, 5, 5, mPaint);
            // 时间
            mPaint.setColor(mTimeTextColor);
            canvas.drawText(timeText, startX + mOffSetX, baseLineY, mPaint);            // 分隔线
            mPaint.setColor(mMiddleLineColor);
            mPaint.setStrokeWidth(3);
            canvas.drawLine(startX, getHeight() / 2, endX, getHeight() / 2, mPaint);
            // 单位
            int hourWidth = (int) mPaint.measureText(unit);
            startX = endX + mOffSetX;
            mPaint.setColor(mTimeBackground);
            canvas.drawText(unit, startX, baseLineY, mPaint);
            startX = startX + hourWidth + mOffSetX;
            endX = startX;
        }

    }


    /**
     * 设置倒计时
     * 需要显示单位 先调用 setNeedDrawUnit(boolean[] unit);
     * @param millisecond
     */
    public void setTimeCountdown(long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(millisecond);

        int hour = calendar.get(Calendar.HOUR);
        String hourText = hour < 10 ? "0" + hour : hour + "";
        mTimeArrs[0] = hourText;

        int second = calendar.get(Calendar.SECOND);
        String secondText = second < 10 ? "0" + second : second + "";
        mTimeArrs[2] = secondText;

        int minute = calendar.get(Calendar.MINUTE);
        if(!mNeedDraw[0] || !mNeedDraw[2] && minute < 1 && second < 60)
            minute = 1;
        String minuteText = minute < 10 ? "0" + minute : minute + "";
        mTimeArrs[1] = minuteText;

        invalidate();
    }

    /**
     * 需要显示的单位
     *
     * @param unit  {hour, minute, second}
     */
    public void setNeedDrawUnit(boolean[] unit) {
        mNeedDraw = unit;
        invalidate();
    }

    /**
     * 设置显示的状态
     * @param status 1 ---- 未开始 ,  0 ------  快结束
     */
    public void setShowStatus(int status) {
        mShowStatus = status;
    }

    public void setDownCountSize(int textSize) {
        mTextSize = textSize;
    }
}