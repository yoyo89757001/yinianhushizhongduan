package com.example.yiliaoyinian.utils;

import android.os.Build;

import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


import androidx.annotation.NonNull;



public class ViewUtils {

    public static void setViewSelected(@NonNull View view, boolean selected) {
        setViewSelected(view, true, selected);
    }

    /**
     * 设置控件选定状态，ViewGroup类型视图除外
     *
     * @param view     视图
     * @param selected 选定状态
     */
    public static void setWidgetSelected(@NonNull View view, boolean selected) {
        setViewSelected(view, false, selected);
    }

    private static void setViewSelected(View view, boolean vgSet, boolean selected) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (vgSet) {
                viewGroup.setSelected(selected);
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewSelected(child, selected);
            }
        } else {
            view.setSelected(selected);
        }
    }

    public static void onGlobalLayout(@NonNull final View view, @NonNull final Runnable runnable) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                runnable.run();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().addOnGlobalLayoutListener(this);
                }
            }
        });
    }

    /**
     * 计算子View在父View的top
     *
     * @param parent 父View
     * @param child  子View
     * @return 相对于父View的top
     */
    public static int computeTop(@NonNull View parent, @NonNull View child) {
        int top = 0;
        View cur = child;
        do {
            top += cur.getTop();
            cur = (View) cur.getParent();
        } while (cur != parent);
        return top;
    }









    public static void eyeEditText(@NonNull final EditText editText, @NonNull final CheckBox eye) {
        updatePassEdit(editText, eye.isChecked());
        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 切换是否可以看见密码
                updatePassEdit(editText, isChecked);
                editText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editText.setSelection(editText.length());
                    }
                }, 100);
            }
        });
    }

    private static void updatePassEdit(EditText editText, boolean visiblePass) {
        if (visiblePass) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public static int getSpecWithMax(int spec, int max) {
        if (max < 0) {
            return spec;
        }

        int mode = View.MeasureSpec.getMode(spec);
        if (mode == View.MeasureSpec.AT_MOST) {
            int size = View.MeasureSpec.getSize(spec);
            size = Math.min(max, size);
            return View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        } else {
            return spec;
        }
    }




}
