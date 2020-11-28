package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.ScrollBean;
import com.example.yiliaoyinian.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class ScrollRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {


    public ScrollRightAdapter(int sectionHeadResId, int layoutResId, @Nullable List<ScrollBean> data) {
        super(sectionHeadResId, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        ScrollBean.ScrollItemBean t = item.getScrollItemBean();
        helper.setText(R.id.righttext, t.getText());
        switch (item.getScrollItemBean().getType()){
            case "1":
                helper.setImageResource(R.id.imagebg,R.drawable.device_gateway);
                break;
            case "2":
                helper.setImageResource(R.id.imagebg,R.drawable.rentibg);
                break;
            case "3":
                helper.setImageResource(R.id.imagebg,R.drawable.device_door_window);
                break;
            case "4":
                helper.setImageResource(R.id.imagebg,R.drawable.wenshi);
                break;
            case "5":
                helper.setImageResource(R.id.imagebg,R.drawable.device_movement);
                break;
            case "6":
                helper.setImageResource(R.id.imagebg,R.drawable.lumilightcwac02);
                break;
            case "7":
                helper.setImageResource(R.id.imagebg,R.drawable.device_wirelessswitch);
                break;
        }

    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder baseViewHolder, @NotNull ScrollBean scrollBean) {
        baseViewHolder.setText(R.id.right_title, scrollBean.getHeader());
    }
}
