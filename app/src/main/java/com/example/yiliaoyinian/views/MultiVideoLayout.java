package com.example.yiliaoyinian.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 多视频布局
 */
public class MultiVideoLayout extends ViewGroup {

    private final static int ROWS = 2;
    private final static int COLS = 2;

    private Paint paint;
    private float lineWidth;
    private int lineColor1;
    private int lineColor2;
    private int childWidth;
    private int childHeight;
    private int selection;

    public MultiVideoLayout(Context context) {
        super(context);
        init();
    }

    public MultiVideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MultiVideoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lineWidth = 1;
        lineColor1 = Color.parseColor("#333333");
        lineColor2 = Color.parseColor("#ff5700");
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        selection = -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;
            int row = i / ROWS;
            int col = i % ROWS;
            int x = (int) (col * (lineWidth + childWidth));
            int y = (int) (row * (lineWidth + childHeight));
            child.layout(x, y, x + childWidth, y + childHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wm = MeasureSpec.getMode(widthMeasureSpec);
        int hm = MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int height;
        if (wm == MeasureSpec.UNSPECIFIED || hm == MeasureSpec.UNSPECIFIED) {
            width = height = childWidth = childHeight = 0;
            int ms = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == GONE) continue;
                child.measure(ms, ms);
            }
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
            childWidth = (int) ((width - lineWidth * (COLS - 1)) / COLS);
            if (childWidth < 0) childWidth = 0;
            childHeight = (int) ((height - lineWidth * (ROWS - 1)) / ROWS);
            if (childHeight < 0) childHeight = 0;
            int cwms = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int chms = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == GONE) continue;
                child.measure(cwms, chms);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        paint.setColor(lineColor1);
        for (int i = 0; i < COLS - 1; i++) {
            float x = (i + 1) * (childWidth + lineWidth) + lineWidth / 2;
            canvas.drawLine(x, 0, x, getMeasuredHeight(), paint);
        }
        for (int i = 0; i < ROWS - 1; i++) {
            float y = (i + 1) * (childHeight + lineWidth) + lineWidth / 2;
            canvas.drawLine(0, y, getMeasuredWidth(), y, paint);
        }
        super.dispatchDraw(canvas);
        if (selection >= 0) {
            paint.setColor(lineColor2);
            int row = selection / ROWS;
            int col = selection % ROWS;
            float top;
            float bottom;
            float left;
            float right;
            if (col < COLS - 1) {
                right = (col + 1) * (childWidth + lineWidth) + lineWidth / 2;
            } else {
                right = getMeasuredWidth() - lineWidth / 2;
            }
            if (col > 0) {
                left = col * (childWidth + lineWidth) + lineWidth / 2;
            } else {
                left = lineWidth / 2;
            }
            if (row < ROWS - 1) {
                bottom = (row + 1) * (childHeight + lineWidth) + lineWidth / 2;
            } else {
                bottom = getMeasuredHeight() - lineWidth / 2;
            }
            if (row > 0) {
                top = row * (childHeight + lineWidth) + lineWidth / 2;
            } else {
                top = lineWidth / 2;
            }
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
        invalidate();
    }
}
