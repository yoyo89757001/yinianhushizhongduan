package com.example.yiliaoyinian.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.XunFangBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class XunFangAdapter extends BaseQuickAdapter<XunFangBean.ResultBean.PatrolHouseListBean, BaseViewHolder> implements LoadMoreModule {


    public XunFangAdapter(int layoutResId, @Nullable List<XunFangBean.ResultBean.PatrolHouseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, XunFangBean.ResultBean.PatrolHouseListBean taskBean) {
        try {
            baseViewHolder.setText(R.id.type_tv,taskBean.getTypeName());
            baseViewHolder.setText(R.id.time_tv, DateUtils.time(taskBean.getPatrolTime()));
            baseViewHolder.setText(R.id.hulidengji, taskBean.getNurseLevelName());
            if (taskBean.getGender()==1){
                baseViewHolder.setText(R.id.name,taskBean.getUserName()+"  "+"男");
            }else {
                baseViewHolder.setText(R.id.name,taskBean.getUserName()+"  "+"女");
            }

            baseViewHolder.setText(R.id.chuangwei,taskBean.getPatrolPosition());
            String[] ss=taskBean.getContent().split(" ");
            if (ss.length==12){
                baseViewHolder.setText(R.id.yishi,ss[1]);
                baseViewHolder.setText(R.id.nianmo,ss[3]);
                baseViewHolder.setText(R.id.zhiti,ss[5]);
                baseViewHolder.setText(R.id.shili,ss[7]);
                baseViewHolder.setText(R.id.yinshi,ss[9]);
                baseViewHolder.setText(R.id.paibian,ss[11]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



//        QMUIFloatLayout qmuiFloatLayout= baseViewHolder.findView(R.id.qmuifloatlayout);
//        if (qmuiFloatLayout!=null){
//            qmuiFloatLayout.setGravity(Gravity.CENTER);
//            TextView t=new TextView(getContext());
//            t.setText("ddddddddd");
//            t.setTextColor(Color.parseColor("#333333"));
//        }

//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
