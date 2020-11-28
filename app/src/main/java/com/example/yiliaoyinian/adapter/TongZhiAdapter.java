package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.XiTongTongZhiBean;
import com.example.yiliaoyinian.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TongZhiAdapter extends BaseQuickAdapter<XiTongTongZhiBean, BaseViewHolder> implements LoadMoreModule {


    public TongZhiAdapter(int layoutResId, @Nullable List<XiTongTongZhiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, XiTongTongZhiBean taskBean) {
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
      //  baseViewHolder.setText(R.id.title,taskBean.getName());



//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
