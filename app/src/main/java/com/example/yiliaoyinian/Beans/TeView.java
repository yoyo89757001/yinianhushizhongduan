package com.example.yiliaoyinian.Beans;

import android.view.View;
import android.widget.TextView;

public class TeView {

    public TeView(TextView textView, View view) {
        this.textView = textView;
        this.view = view;
    }

    private TextView textView;
    private View view;

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
