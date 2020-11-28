package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import com.example.yiliaoyinian.Beans.XiTongTongZhiBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XiTongTongZhiAdapter extends BaseQuickAdapter<XiTongTongZhiBean.ResultBean.DataBean, BaseViewHolder> implements LoadMoreModule {


    public XiTongTongZhiAdapter(int layoutResId, @Nullable List<XiTongTongZhiBean.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, XiTongTongZhiBean.ResultBean.DataBean taskBean) {
        try {
            baseViewHolder.setText(R.id.time, DateUtils.time(taskBean.getPublishTime()+""));
            baseViewHolder.setText(R.id.title, taskBean.getTitle());
            baseViewHolder.setText(R.id.content_tv, taskBean.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }




//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
