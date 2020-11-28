package com.example.yiliaoyinian.adapter;


import android.graphics.Color;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.UnUpdataBleBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CeLiangJiLuAdapter extends BaseQuickAdapter<UnUpdataBleBean.ResultBean, BaseViewHolder> implements LoadMoreModule {


    public CeLiangJiLuAdapter(int layoutResId, @Nullable List<UnUpdataBleBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UnUpdataBleBean.ResultBean taskBean) {
        try {
            QMUIButton view=baseViewHolder.getView(R.id.shangchuan);
            QMUIButton view2=baseViewHolder.getView(R.id.quxiao);

            view.setRadius(QMUIDisplayHelper.dp2px(getContext(), 15));
            view.setChangeAlphaWhenPress(true);//点击改变透明度

            view2.setBorderColor(Color.parseColor("#FF59B683"));
            view2.setRadius(QMUIDisplayHelper.dp2px(getContext(), 15));
            view2.setChangeAlphaWhenPress(true);//点击改变透明度
            baseViewHolder.setText(R.id.name,taskBean.getMeasureUserName());
            baseViewHolder.setText(R.id.xueya,"收缩压"+taskBean.getSystolic()+"/舒张压"+taskBean.getDiastolic());
            baseViewHolder.setText(R.id.xinlv,taskBean.getHeartrateM()+"");
            baseViewHolder.setText(R.id.name2,taskBean.getNurseGroupName());
            baseViewHolder.setText(R.id.time, DateUtils.time(taskBean.getCreateTime()+""));
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
