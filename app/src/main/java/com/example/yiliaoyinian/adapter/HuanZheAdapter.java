package com.example.yiliaoyinian.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.HuanZheBean0;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HuanZheAdapter extends BaseQuickAdapter<HuanZheBean0.ResultBean, BaseViewHolder> implements LoadMoreModule {


    public HuanZheAdapter(int layoutResId, @Nullable List<HuanZheBean0.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HuanZheBean0.ResultBean taskBean) {
        try {
            baseViewHolder.setText(R.id.fanghao,taskBean.getRoomName());
            baseViewHolder.setText(R.id.chuanghao,taskBean.getBedName());
            baseViewHolder.setText(R.id.logo,taskBean.getBuildName());
            baseViewHolder.setText(R.id.huanzhe,taskBean.getPatientName());
            baseViewHolder.setText(R.id.idid,taskBean.getPatientCode());
            baseViewHolder.setText(R.id.zhuzhi,taskBean.getDoctorName());
            if (taskBean.getGender()==1){
                baseViewHolder.setText(R.id.sex,"男");
            }else {
                baseViewHolder.setText(R.id.sex,"女");
            }
            baseViewHolder.setText(R.id.ruyuanshijian, DateUtils.time1(taskBean.getCheckInTime()));
            baseViewHolder.setText(R.id.bingqing,taskBean.getIllness());
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
