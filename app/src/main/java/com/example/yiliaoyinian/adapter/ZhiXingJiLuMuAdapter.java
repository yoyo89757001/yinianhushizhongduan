package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZhiXingJiLuMuAdapter extends BaseQuickAdapter<HuanZheBean.ResultBean.LogListBean, BaseViewHolder> implements LoadMoreModule {


    public ZhiXingJiLuMuAdapter(int layoutResId, @Nullable List<HuanZheBean.ResultBean.LogListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HuanZheBean.ResultBean.LogListBean taskBean) {
        try {
            baseViewHolder.setText(R.id.title,taskBean.getItemName());
            baseViewHolder.setText(R.id.content_tv,taskBean.getServiceContent());
            baseViewHolder.setText(R.id.stime,"开始时间: "+DateUtils.time(taskBean.getStartTime()+""));
            baseViewHolder.setText(R.id.etime, "结束时间: "+DateUtils.time(taskBean.getEndTime()+""));
            if (taskBean.getEndTime()>=taskBean.getFinishTime()){
                baseViewHolder.setText(R.id.wtime, "完成时间: "+DateUtils.time(taskBean.getFinishTime()+""));
            }else {
                baseViewHolder.setText(R.id.wtime, "完成时间: "+DateUtils.time(taskBean.getFinishTime()+"")+" (已超时)");
            }
            baseViewHolder.setText(R.id.name,"完成人: "+taskBean.getFinishUser());
            if (baseViewHolder.getLayoutPosition()==0){
                baseViewHolder.setGone(R.id.kkk,true);
            }else {
                baseViewHolder.setGone(R.id.kkk,false);
            }
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
