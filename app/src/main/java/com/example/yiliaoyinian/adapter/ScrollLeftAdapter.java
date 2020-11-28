package com.example.yiliaoyinian.adapter;


import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.TeView;
import com.example.yiliaoyinian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<TeView> tv = new ArrayList<>();

    public ScrollLeftAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.left_text, item);
        //将左侧item中的TextView添加到集合中

        tv.add(new TeView((TextView) helper.getView(R.id.left_text),helper.getView(R.id.vv11)));
        //设置进入页面之后,左边列表的初始状态
        if (tv != null) {
            getData();
            if (tv.size() == getData().size()) {
                selectItem(0);
            }
        }

        helper.getView(R.id.item).setSelected(true);
    }

    //传入position,设置左侧列表相应item高亮
    public void selectItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                tv.get(i).getTextView().setBackgroundColor(Color.parseColor("#f8f9fb"));
                tv.get(i).getTextView().setTextColor(ContextCompat.getColor(getContext(), R.color.common_text));

                //以下是指定某一个TextView滚动的效果
               /* tv.get(i).getTextView().setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tv.get(i).getTextView().setFocusable(true);
                tv.get(i).getTextView().setFocusableInTouchMode(true);
                tv.get(i).getTextView().setMarqueeRepeatLimit(-1);*/
                tv.get(i).getView().setVisibility(View.VISIBLE);
            } else {
                tv.get(i).getTextView().setBackgroundColor(Color.parseColor("#ffffff"));
                tv.get(i).getTextView().setTextColor(ContextCompat.getColor(getContext(), R.color.common_text));

                //失去焦点则停止滚动
               /* tv.get(i).getTextView().setEllipsize(TextUtils.TruncateAt.END);
                tv.get(i).getTextView().setFocusable(false);
                tv.get(i).getTextView().setFocusableInTouchMode(false);
                tv.get(i).getTextView().setMarqueeRepeatLimit(0);*/
                tv.get(i).getView().setVisibility(View.INVISIBLE);
            }
        }
    }
}
