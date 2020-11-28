package com.example.yiliaoyinian.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.ZhanShiDataBean;
import com.example.yiliaoyinian.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZhanShiDataAdapter extends BaseQuickAdapter<ZhanShiDataBean.ResultBean.AbnormalListBean, BaseViewHolder> implements LoadMoreModule {


    public ZhanShiDataAdapter(int layoutResId, @Nullable List<ZhanShiDataBean.ResultBean.AbnormalListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ZhanShiDataBean.ResultBean.AbnormalListBean taskBean) {
        try {
            baseViewHolder.setText(R.id.tile,taskBean.getMeasureData()+"【"+taskBean.getMeasuredUserName()+" "+taskBean.getBedInfo()+"】");
        }catch (Exception e){
            e.printStackTrace();
        }



     //   View vv=baseViewHolder.getView(R.id.tile);
       // if (baseViewHolder.getLayoutPosition()==1){//模拟异常数据
//            baseViewHolder.setBackgroundColor(R.id.root_con, Color.parseColor("#FFFFF1F2"));
//            baseViewHolder.setGone(R.id.jinggao,false);
//            ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) vv.getLayoutParams();
//            params.leftMargin= QMUIDisplayHelper.dp2px(getContext(),8);
//            vv.setLayoutParams(params);
//            vv.invalidate();
//        }else {
//            baseViewHolder.setBackgroundColor(R.id.root_con, Color.parseColor("#ffffffff"));
//            baseViewHolder.setGone(R.id.jinggao,true);
//            ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) vv.getLayoutParams();
//            params.leftMargin= QMUIDisplayHelper.dp2px(getContext(),16);
//            vv.setLayoutParams(params);
//            vv.invalidate();
//        }

//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
