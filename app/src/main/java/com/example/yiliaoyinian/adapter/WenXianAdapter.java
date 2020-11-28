package com.example.yiliaoyinian.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.UnFinshBean;
import com.example.yiliaoyinian.Beans.WenXianBean;
import com.example.yiliaoyinian.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WenXianAdapter extends BaseQuickAdapter<WenXianBean.ResultBean.DataBean, BaseViewHolder> implements LoadMoreModule {


    public WenXianAdapter(int layoutResId, @Nullable List<WenXianBean.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WenXianBean.ResultBean.DataBean taskBean) {
        try {
            baseViewHolder.setText(R.id.title,taskBean.getTitle());
            baseViewHolder.setText(R.id.content_tv,taskBean.getDetail());
            Glide.with(getContext())
                    .load(taskBean.getArticleImg())
                    .thumbnail(0.1f)
                    .into((ImageView) baseViewHolder.getView(R.id.bitimage));
        }catch (Exception e){
            e.printStackTrace();
        }

//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
