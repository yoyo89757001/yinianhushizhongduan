package com.example.yiliaoyinian.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.IBinder;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.ViewUtils;


public class AppRecyclerView extends RecyclerView implements NestedScrollingChild {

    private int maxWidth;
    private int maxHeight;
    private boolean autoCloseSoftKeyboard;

    public AppRecyclerView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public AppRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        widthSpec = ViewUtils.getSpecWithMax(widthSpec, maxWidth);
        heightSpec = ViewUtils.getSpecWithMax(heightSpec, maxHeight);
        super.onMeasure(widthSpec, heightSpec);
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        requestLayout();
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        requestLayout();
    }

    private void init(AttributeSet attrs) {
        maxWidth = -1;
        maxHeight = -1;
        if (null != attrs) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AppRecyclerView);
            maxWidth = ta.getDimensionPixelSize(R.styleable.AppRecyclerView_maxWidth, -1);
            maxHeight = ta.getDimensionPixelSize(R.styleable.AppRecyclerView_maxHeight, -1);
            ta.recycle();
        }
        // touchHelper = new NestedTouchHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // touchHelper.onTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    /**
     * 是否自动关闭软键盘
     *
     * @return true，自动关闭软键盘
     */
    public boolean isAutoCloseSoftKeyboard() {
        return autoCloseSoftKeyboard;
    }

    /**
     * 设置是否自动关闭软键盘
     *
     * @param autoCloseSoftKeyboard 自动关闭软键盘
     */
    public void setAutoCloseSoftKeyboard(boolean autoCloseSoftKeyboard) {
        this.autoCloseSoftKeyboard = autoCloseSoftKeyboard;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (autoCloseSoftKeyboard) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                IBinder token = getWindowToken();
                if (null != token) {
                    InputMethodManager imm = (InputMethodManager) getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (null != imm) {
                        imm.hideSoftInputFromWindow(token, 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        touchHelper.onTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                super.onTouchEvent(e);
                return true;
            case MotionEvent.ACTION_MOVE:
                switch (touchHelper.getNestedState()) {
                    case NestedTouchHelper.NESTED_STATE_NONE:
                        super.onTouchEvent(e);
                        return true;
                    case NestedTouchHelper.NESTED_STATE_SCROLLING:
                        return true;
                    case NestedTouchHelper.NESTED_STATE_UNABLE:
                    default:
                        return super.onTouchEvent(e);
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(e);
                return true;
            default:
                super.onTouchEvent(e);
                return true;
        }
    }*/
}
