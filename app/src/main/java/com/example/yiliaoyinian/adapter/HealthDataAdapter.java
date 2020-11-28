package com.example.yiliaoyinian.adapter;


import android.graphics.Color;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.MeasurementAllBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class HealthDataAdapter extends BaseQuickAdapter<MeasurementAllBean, BaseViewHolder> implements LoadMoreModule {


    public HealthDataAdapter(int layoutResId, @Nullable List<MeasurementAllBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeasurementAllBean taskBean) {
        try {
            View vv=baseViewHolder.getView(R.id.tile);
            if (taskBean.getType()==2){//异常数据
                baseViewHolder.setBackgroundColor(R.id.root_con, Color.parseColor("#FFFFF1F2"));
                baseViewHolder.setGone(R.id.jinggao,false);
                ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) vv.getLayoutParams();
                params.leftMargin= QMUIDisplayHelper.dp2px(getContext(),8);
                vv.setLayoutParams(params);
                vv.invalidate();
                baseViewHolder.setTextColor(R.id.data_tv,Color.parseColor("#FFFA7480"));

            }else {
                baseViewHolder.setTextColor(R.id.data_tv,Color.parseColor("#FF222222"));
                baseViewHolder.setBackgroundColor(R.id.root_con, Color.parseColor("#ffffffff"));
                baseViewHolder.setGone(R.id.jinggao,true);
                ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) vv.getLayoutParams();
                params.leftMargin= QMUIDisplayHelper.dp2px(getContext(),16);
                vv.setLayoutParams(params);
                vv.invalidate();
            }
           // 1-血氧，2-血压，3-血糖，4-体温，5-脉搏，6-呼吸，7-心率"
           switch (taskBean.getMeasuredType()){
               case 1:
                   baseViewHolder.setText(R.id.tile,"血氧");
                   break;
               case 2:
                   baseViewHolder.setText(R.id.tile,"血压");
                   break;
               case 3:
                   baseViewHolder.setText(R.id.tile,"血糖");
                   break;
               case 4:
                   baseViewHolder.setText(R.id.tile,"体温");
                   break;
               case 5:
                   baseViewHolder.setText(R.id.tile,"脉搏");
                   break;
               case 6:
                   baseViewHolder.setText(R.id.tile,"呼吸");
                   break;
               case 7:
                   baseViewHolder.setText(R.id.tile,"心率");
                   break;
           }
            baseViewHolder.setText(R.id.data_tv,taskBean.getMeasureData());
            baseViewHolder.setText(R.id.time_data, DateUtils.ti(taskBean.getMeasureTime()+""));

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
