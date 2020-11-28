package com.example.yiliaoyinian.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.SellaceBean;
import com.example.yiliaoyinian.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PatientsAdapter extends BaseQuickAdapter<SellaceBean.ResultBean.DataBean, BaseViewHolder> implements LoadMoreModule {


    public PatientsAdapter(int layoutResId, @Nullable List<SellaceBean.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SellaceBean.ResultBean.DataBean taskBean) {
        try {
            baseViewHolder.setText(R.id.name,taskBean.getPatientName());
            if (taskBean.isSelectType()){//选中
                Log.d("PatientsAdapter", "单独");
                baseViewHolder.setImageResource(R.id.checkbox,R.drawable.danxuan1);
            }else {
                baseViewHolder.setImageResource(R.id.checkbox,R.drawable.danxuan0);
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
