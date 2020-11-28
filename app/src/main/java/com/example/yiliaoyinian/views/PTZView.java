package com.example.yiliaoyinian.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.MathUtils;


/**
 * 摄像头PTZ控制罗盘
 */
public class PTZView extends View {

    public static final int CURSOR_NONE = 0;
    public static final int CURSOR_LEFT = 1;
    public static final int CURSOR_UP = 2;
    public static final int CURSOR_RIGHT = 3;
    public static final int CURSOR_DOWN = 4;

    private CenterBlock[] centerBlocks;
    private float centerBlockX; // 中心块位置：Y
    private float centerBlockY; // 中心块位置：X
    private int currentCenterBlock; // 当前的中心块
    private int radius; // 自身半径
    private int centerBlockRadius; // 中心块半径
    private double[] point = new double[2]; // 点，辅助对象
    private Paint bgPaint; // 背景画笔
    private PositionAnimator positionAnimator; // 位置动画
    private int cursor; // 表示罗盘指向
    private float speedScale; // 速度比例
    private OnChangedListener onChangedListener;

    public PTZView(Context context) {
        super(context);
        init();
    }

    public PTZView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PTZView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor("#f2f2f2"));
        bgPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        centerBlocks = new CenterBlock[]{
                new CenterBlock(
                        ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        dpToPx(getContext(), 8),
                        Color.parseColor("#1a000000")
                ),
                new CenterBlock(
                        ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        dpToPx(getContext(), 8),
                        Color.parseColor("#1a000000")
                )
        };
        currentCenterBlock = 0;
        cursor = CURSOR_NONE;
        speedScale = 0;
        // 动画
        positionAnimator = new PositionAnimator() {
            @Override
            void onPositionChanged(float x, float y) {
                setCenterBlockPosition(x, y);
            }
        };
    }

    public  float dpToPx(@NonNull Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public  float spToPx(@NonNull Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public  int px(@NonNull Context context, float dp) {
        return (int) dpToPx(context, dp);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != centerBlocks) {
            for (CenterBlock cb : centerBlocks) {
                cb.recycle();
            }
            centerBlocks = null;
        }
        if (null != positionAnimator) {
            positionAnimator.cancel();
            positionAnimator = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerBlockX = getMeasuredWidth() / 2;
        centerBlockY = getMeasuredHeight() / 2;
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
        centerBlockRadius = radius / 3;
        int cbw = centerBlockRadius * 2;
        int cbh = centerBlockRadius * 2;
        for (CenterBlock cb : centerBlocks) {
            cb.initSize(cbw, cbh);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getMeasuredWidth() / 2;
        int cy = getMeasuredHeight() / 2;
        canvas.drawCircle(cx, cy, radius, bgPaint);
        centerBlocks[currentCenterBlock].draw(canvas, centerBlockX, centerBlockY);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 开始滑动时，需要关闭动画
                if (null != positionAnimator) {
                    positionAnimator.cancel();
                }
                performClick();
                currentCenterBlock = 1;
                setCenterBlockPosition(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                centerBlockX = event.getX();
                centerBlockY = event.getY();
                setCenterBlockPosition(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                currentCenterBlock = 0;
                float finalX = getMeasuredWidth() / 2;
                float finalY = getMeasuredHeight() / 2;
                positionAnimator.set(centerBlockX, centerBlockY, finalX, finalY);
                positionAnimator.start();
                break;
        }
        return true;
    }

    public OnChangedListener getOnChangedListener() {
        return onChangedListener;
    }

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    private void setCenterBlockPosition(float centerBlockX, float centerBlockY) {
        int cx = getMeasuredWidth() / 2;
        int cy = getMeasuredHeight() / 2;
        double len = MathUtils.distanceAB(centerBlockX, centerBlockY, cx, cy);
        double validRadius = Math.max(0, radius - centerBlockRadius);
        if (len > validRadius) {
            // 超出圆的范围
            // 计算夹角
            double angle = computeAngle(centerBlockX, centerBlockY);
            MathUtils.circlePoint(cx, cy, validRadius, angle, point, 0);
            this.centerBlockX = (float) point[0];
            this.centerBlockY = (float) point[1];
        } else {
            this.centerBlockX = centerBlockX;
            this.centerBlockY = centerBlockY;
        }
        // 更新指向
        updateCursor();
        invalidate();
    }

    /**
     * 计算某点的角度
     *
     * @param x 点：X
     * @param y 点：Y
     * @return 角度
     */
    private double computeAngle(double x, double y) {
        int cx = getMeasuredWidth() / 2;
        int cy = getMeasuredHeight() / 2;
        double angle = MathUtils.angleBAC(cx, cy, getMeasuredWidth(), cy, x, y);
        int quadrant = MathUtils.getQuadrant(x - cx, y - cy);
        if (quadrant > 2) {
            // 处于第三或第四象限
            angle = 360 - angle;
        }
        return angle;
    }

    private void updateCursor() {
        int cx = getMeasuredWidth() / 2;
        int cy = getMeasuredHeight() / 2;
        double len = MathUtils.distanceAB(centerBlockX, centerBlockY, cx, cy);
        double validRadius = Math.max(0, radius - centerBlockRadius);
        int cursor;
        float speedScale;
        if (len > validRadius / 2) {
            // 进入有效的指向范围
            double angle = computeAngle(centerBlockX, centerBlockY);
            // System.out.println("PTZView:angle=" + angle);
            long value = (long) (angle + 45);
            int index = (int) (value / 90);
            switch (index) {
                case 1:
                    cursor = CURSOR_DOWN;
                    speedScale = (float) (len / (validRadius / 2) - 1);
                    break;
                case 2:
                    cursor = CURSOR_LEFT;
                    speedScale = (float) (len / (validRadius / 2) - 1);
                    break;
                case 3:
                    cursor = CURSOR_UP;
                    speedScale = (float) (len / (validRadius / 2) - 1);
                    break;
                case 0:
                    cursor = CURSOR_RIGHT;
                    speedScale = (float) (len / (validRadius / 2) - 1);
                    break;
                default:
                    cursor = CURSOR_NONE;
                    speedScale = 0;
                    break;
            }
        } else {
            // 无效的指向范围
            cursor = CURSOR_NONE;
            speedScale = 0;
        }
        setCursor(cursor);
        setSpeedScale(speedScale);
    }

    private void setCursor(int cursor) {
        if (this.cursor != cursor) {
            int oldCursor = this.cursor;
            this.cursor = cursor;
            if (null != onChangedListener) {
                onChangedListener.onCursorChanged(this, oldCursor, cursor);
            }
        }
    }

    private void setSpeedScale(float speedScale) {
        if (this.speedScale != speedScale) {
            float oldSpeedScale = this.speedScale;
            this.speedScale = speedScale;
            if (null != onChangedListener) {
                onChangedListener.onSpeedScaleChanged(this, oldSpeedScale, speedScale);
            }
        }
    }

    /**
     * 获取指向
     *
     * @return 指向
     */
    public int getCursor() {
        return cursor;
    }

    /**
     * 获取速度比例
     *
     * @return 速度比例
     */
    public float getSpeedScale() {
        return speedScale;
    }

    /**
     * 指向发生变化监听器
     */
    public interface OnChangedListener {

        /**
         * 方向发生变化
         *
         * @param view      视图
         * @param oldCursor 旧方向
         * @param newCursor 新方向
         */
        void onCursorChanged(@NonNull PTZView view, int oldCursor, int newCursor);

        /**
         * 速度比例发生变化
         *
         * @param view          视图
         * @param oldSpeedScale 旧速度比例
         * @param newSpeedScale 新速度比例
         */
        void onSpeedScaleChanged(@NonNull PTZView view, float oldSpeedScale, float newSpeedScale);
    }

    abstract static class PositionAnimator extends ValueAnimator {
        float startX;
        float startY;
        float lenX;
        float lenY;

        PositionAnimator() {
            setDuration(200);
            addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) getAnimatedValue();
                    float x = startX + scale * lenX;
                    float y = startY + scale * lenY;
                    onPositionChanged(x, y);
                }
            });
        }

        void set(float fromX, float fromY, float toX, float toY) {
            startX = fromX;
            startY = fromY;
            lenX = toX - fromX;
            lenY = toY - fromY;
            setFloatValues(0f, 1f);
        }

        abstract void onPositionChanged(float x, float y);
    }

    static class CenterBlock {

        private int color;
        private float shadowRadius;
        private int shadowColor;
        private Bitmap bitmap;
        private Canvas canvas;
        private int width;
        private int height;
        private Paint paint = new Paint();
        private RectF helpRectF = new RectF();
        private Rect helpRect = new Rect();

        CenterBlock(int color, float shadowRadius, int shadowColor) {
            this.color = color;
            this.shadowRadius = shadowRadius;
            this.shadowColor = shadowColor;
        }

        void draw(Canvas canvas, float x, float y) {
            if (null != bitmap && !bitmap.isRecycled() && width > 0 && height > 0) {
                helpRectF.left = x - width / 2 - shadowRadius;
                helpRectF.right = x + width / 2 + shadowRadius;
                helpRectF.top = y - height / 2 - shadowRadius;
                helpRectF.bottom = y + height / 2 + shadowRadius;
                int needWidth = (int) (width + shadowRadius * 2);
                int needHeight = (int) (height + shadowRadius * 2);
                helpRect.set(0, 0, needWidth, needHeight);
                canvas.drawBitmap(bitmap, helpRect, helpRectF, null);
            }
        }

        void initSize(int width, int height) {
            if (width != this.width || height != this.height) {
                int needWidth = (int) (width + shadowRadius * 2);
                int needHeight = (int) (height + shadowRadius * 2);
                if (needWidth > getRealWidth() || needHeight > getRealHeight()) {
                    recycle();
                    bitmap = Bitmap.createBitmap(needWidth, needHeight, Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(bitmap);
                }
                this.width = width;
                this.height = height;
                genImage();
            }
        }

        /**
         * 释放中心块位图
         */
        private void recycle() {
            if (null != bitmap) {
                if (bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                bitmap = null;
            }
            if (null != canvas) {
                canvas = null;
            }
        }

        /**
         * 获取中心块的真正宽度，即位图大小
         *
         * @return 中心块的真正宽度
         */
        private int getRealWidth() {
            if (null != bitmap && !bitmap.isRecycled()) {
                return bitmap.getWidth();
            }
            return 0;
        }

        /**
         * 获取中心块的真正高度，即位图大小
         *
         * @return 中心块的真正高度
         */
        private int getRealHeight() {
            if (null != bitmap && !bitmap.isRecycled()) {
                return bitmap.getHeight();
            }
            return 0;
        }

        /**
         * 产生中心块图像
         */
        private void genImage() {
            if (null != canvas && null != bitmap && !bitmap.isRecycled()) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                if (width > 0 && height > 0) {
                    paint.reset();
                    paint.setColor(color);
                    paint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
                    paint.setAntiAlias(true);
                    helpRectF.set(shadowRadius, shadowRadius,
                            width + shadowRadius, height + shadowRadius);
                    canvas.drawOval(helpRectF, paint);
                }
            }
        }
    }
}
