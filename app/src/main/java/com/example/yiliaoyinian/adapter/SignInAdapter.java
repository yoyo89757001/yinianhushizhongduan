package com.example.yiliaoyinian.adapter;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.SignInBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignInAdapter extends BaseQuickAdapter<SignInBean.ResultBean.SignInListBean, BaseViewHolder> implements LoadMoreModule {


    public SignInAdapter(int layoutResId, @Nullable List<SignInBean.ResultBean.SignInListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SignInBean.ResultBean.SignInListBean taskBean) {
        try {
            baseViewHolder.setText(R.id.type_tv,taskBean.getTypeName());
            baseViewHolder.setText(R.id.time_tv, DateUtils.time(taskBean.getSignTime()+""));
           // baseViewHolder.setText(R.id.name,taskBean.getUserName()+"在‘");

            String str1=taskBean.getUserName()+"在 ’<font color='#59B683'>"+taskBean.getPosition()+"</font>‘ 签到成功";
            baseViewHolder.setText(R.id.name,Html.fromHtml(str1));

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
