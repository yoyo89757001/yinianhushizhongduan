package com.example.yiliaoyinian.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.RuKeBean;
import com.example.yiliaoyinian.R;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuKeAdapter extends BaseQuickAdapter<RuKeBean.ResultBean, BaseViewHolder> implements LoadMoreModule {


    public RuKeAdapter(int layoutResId, @Nullable List<RuKeBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RuKeBean.ResultBean taskBean) {
        try {
            baseViewHolder.setText(R.id.name,"姓名: "+taskBean.getPatientName());
            if (taskBean.getGender()==1){
                baseViewHolder.setText(R.id.sex,"男/"+taskBean.getAge()+"岁");
            }else {
                baseViewHolder.setText(R.id.sex,"女/"+taskBean.getAge()+"岁");
            }

            baseViewHolder.setText(R.id.idid,"ID: "+taskBean.getPatientCode());

            QMUIButton qmuiButton=baseViewHolder.getView(R.id.ruke);
            qmuiButton.setRadius(QMUIDisplayHelper.dp2px(getContext(), 18));
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
