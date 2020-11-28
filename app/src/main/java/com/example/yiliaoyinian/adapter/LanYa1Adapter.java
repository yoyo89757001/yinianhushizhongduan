package com.example.yiliaoyinian.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.BleAllDataListBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class LanYa1Adapter extends BaseQuickAdapter<BleAllDataListBean.ResultBean, BaseViewHolder> implements LoadMoreModule {


    public LanYa1Adapter(int layoutResId, @Nullable List<BleAllDataListBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BleAllDataListBean.ResultBean taskBean) {
        try {
            baseViewHolder.setText(R.id.name,taskBean.getMeasureUserName());
            baseViewHolder.setText(R.id.chuanghao,taskBean.getBedInfo());
            baseViewHolder.setText(R.id.time, DateUtils.time(taskBean.getCreateTime()+""));
            baseViewHolder.setText(R.id.xueya,taskBean.getSystolic()+"/"+taskBean.getDiastolic());
            baseViewHolder.setText(R.id.maibo,taskBean.getHeartrateM()+"");
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
