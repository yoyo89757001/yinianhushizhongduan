package com.example.yiliaoyinian.adapter;


import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.UnFinshBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;



public class UnFinshAdapter extends BaseQuickAdapter<UnFinshBean.ResultBean, BaseViewHolder> implements LoadMoreModule {


    public UnFinshAdapter(int layoutResId, @Nullable List<UnFinshBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UnFinshBean.ResultBean taskBean) {
        try {
            baseViewHolder.setText(R.id.title,taskBean.getItemName());
            if (taskBean.getLastTime()==null || taskBean.getLastTime().equals("") || taskBean.getLastTime().equals("0")){
                baseViewHolder.setText(R.id.time, "时间: "+DateUtils.time(taskBean.getStartTime()+""));
                Log.d("UnFinshAdapter", "ddd1");
            }else {
                baseViewHolder.setText(R.id.time, "时间: "+DateUtils.time(taskBean.getLastTime()+""));
                Log.d("UnFinshAdapter", "ddd2");
            }
            baseViewHolder.setText(R.id.number,taskBean.getBedName());
            baseViewHolder.setText(R.id.name,"患者: "+taskBean.getPatientName());
            switch (taskBean.getServiceType()){//1-医疗护理，2-康复，3-心理，4-照料，5-法律
                case 1:
                    baseViewHolder.setBackgroundResource(R.id.back_bg,R.drawable.zidonghuoqulv1);
                    break;
                case 2:
                    baseViewHolder.setBackgroundResource(R.id.back_bg,R.drawable.zidonghuoqulv2);
                    break;
                case 3:
                    baseViewHolder.setBackgroundResource(R.id.back_bg,R.drawable.zidonghuoqulv3);
                    break;
                case 4:
                    baseViewHolder.setBackgroundResource(R.id.back_bg,R.drawable.zidonghuoqulv4);
                    break;
                case 5:
                    baseViewHolder.setBackgroundResource(R.id.back_bg,R.drawable.zidonghuoqulv5);
                    break;
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
