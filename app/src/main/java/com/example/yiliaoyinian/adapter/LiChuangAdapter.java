package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.LiChuangBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiChuangAdapter extends BaseQuickAdapter<LiChuangBean, BaseViewHolder> implements LoadMoreModule {


    public LiChuangAdapter(int layoutResId, @Nullable List<LiChuangBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LiChuangBean taskBean) {
        try {
            baseViewHolder.setText(R.id.info,taskBean.getData());
            baseViewHolder.setText(R.id.time, DateUtils.time(taskBean.getPushDate()+""));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
