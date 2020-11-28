package com.example.yiliaoyinian.adapter;


import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HuZhuXiangMuAdapter extends BaseQuickAdapter<HuanZheBean.ResultBean.ServiceListBean, BaseViewHolder> implements LoadMoreModule {


    public HuZhuXiangMuAdapter(int layoutResId, @Nullable List<HuanZheBean.ResultBean.ServiceListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HuanZheBean.ResultBean.ServiceListBean taskBean) {
        try {
            baseViewHolder.setText(R.id.title,taskBean.getItemName());
            baseViewHolder.setText(R.id.content_tv,taskBean.getServiceContent());
            baseViewHolder.setText(R.id.stime, "开始时间: "+DateUtils.time(taskBean.getStartTime()+""));
            baseViewHolder.setText(R.id.etime,"结束时间: "+DateUtils.time(taskBean.getEndTime()+""));
            if (baseViewHolder.getLayoutPosition()==0){
                baseViewHolder.setGone(R.id.kkk,true);
            }else {
                baseViewHolder.setGone(R.id.kkk,false);
            }
            QMUIButton qmuiButton=baseViewHolder.getView(R.id.wancheng);
            qmuiButton.setBorderColor(Color.parseColor("#59B683"));
            qmuiButton.setRadius(QMUIDisplayHelper.dp2px(getContext(), 13));
            qmuiButton.setChangeAlphaWhenPress(true);//点击改变透明度



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
