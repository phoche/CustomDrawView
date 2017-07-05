package com.phoche.customdrawview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.phoche.ScreenSizeUtils;
import com.phoche.customdrawview.R;

/**
 * Create by qinpc on 2017/6/6
 */
public class ArrowView extends View {


    public static final int DIRECTION_UPWARD = 0;
    public static final int DIRECTION_DOWNWARD = 1;
    public static final int DIRECTION_LEFTWARD = 2;
    public static final int DIRECTION_RIGHTWARD = 3;

    public static final int COLOR_GOODS_HIGH_LIGHT = 0xFFFAB74C;
    public static final int COLOR_HIGH_LIGHT = 0xffffffff;
    public static final int COLOR_NORMAL = 0x88ffffff;
    private final Context mContext;

    private int mHeight;
    private int mWidth;
    private Paint mPaint = new Paint();
    private int mLineColor = COLOR_NORMAL;
    private int mDirection = DIRECTION_UPWARD;

    public ArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowView);
        int direction = typedArray.getInteger(R.styleable.ArrowView_arrow_direction, 0);
        switch (direction) {
            case 0:
                mDirection = DIRECTION_UPWARD;
                break;
            case 1:
                mDirection = DIRECTION_DOWNWARD;
                break;
            case 2:
                mDirection = DIRECTION_LEFTWARD;
                break;
            case 3:
                mDirection = DIRECTION_RIGHTWARD;
                break;
        }

        typedArray.recycle();
    }

    public ArrowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context) {
        this(context, null);
        setBackgroundColor(0x00000000);
    }

    public ArrowView(Context context, int direction) {
        this(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int defaultWidth = ScreenSizeUtils.dip2px(mContext, 20);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getPaddingLeft() + getPaddingRight() + defaultWidth;
        }

        return result;
    }

    /**
     * 按照测量模式返回对应的大小
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int defaultHeight = ScreenSizeUtils.dip2px(mContext, 16);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            // 精确模式
            result = size;
        } else {
            // 计算需要的大小
            result = getPaddingTop() + getPaddingBottom() + defaultHeight;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(mLineColor);
        float startX = getPaddingLeft();
        float startY = getPaddingTop();
        float middleX = (mWidth - startX - getPaddingRight()) / 2 + startX;
        float middleY = 0;
        float endX = mWidth - getPaddingRight();
        float endY = 0;
        switch (mDirection) {
            case DIRECTION_UPWARD:
                startY = mHeight - getPaddingBottom();
                middleY = getPaddingTop();
                endY = startY;
                break;

            case DIRECTION_DOWNWARD:
                startY = getPaddingTop();
                middleY = mHeight - getPaddingBottom();
                endY = startY;
                break;

            case DIRECTION_LEFTWARD:
                startX = endX;
                middleX = getPaddingLeft();
                middleY = (mHeight - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
                endY = mHeight - getPaddingBottom();
                break;

            case DIRECTION_RIGHTWARD:
                middleX = endX;
                middleY = (mHeight - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
                endX = startX;
                endY = mHeight - getPaddingBottom();
                break;
            default:
                break;
        }

        canvas.drawLine(startX, startY, middleX, middleY, mPaint);
        canvas.drawLine(middleX - 1.0f, middleY, endX, endY, mPaint);
    }

    public void setLineColor(int color) {
        mLineColor = color;
        invalidate();
    }

}