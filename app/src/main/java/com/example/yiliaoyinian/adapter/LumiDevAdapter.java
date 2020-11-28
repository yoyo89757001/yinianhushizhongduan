package com.example.yiliaoyinian.adapter;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Vector;



public class LumiDevAdapter extends BaseQuickAdapter<WGInfoSave, BaseViewHolder> implements LoadMoreModule {



    public LumiDevAdapter(int layoutResId, @Nullable Vector<WGInfoSave> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WGInfoSave taskBean) {
        try {
              baseViewHolder.setText(R.id.name,taskBean.getName());
//            luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_magnet.aq2","门窗传感器"));
//            luMiZiYuanBox.put(new LuMiZiYuan("lumi.vibration.aq1","动静贴"));
//            luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_motion.aq2","人体传感器"));
//            luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_switch.aq3","无线开关"));
//              switch (taskBean.getModle()){
//                  case "lumi.sensor_magnet.aq2": //门窗传感器
//                      baseViewHolder.setImageResource(R.id.image,R.drawable.device_door_window);
//                      break;
//                  case "lumi.vibration.aq1"://动静贴
//                      baseViewHolder.setImageResource(R.id.image,R.drawable.device_movement);
//                      break;
//                  case "lumi.sensor_motion.aq2": //人体传感器
//                      baseViewHolder.setImageResource(R.id.image,R.drawable.device_motion);
//                      break;
//                  case "lumi.sensor_switch.aq3": //无线开关
//                      baseViewHolder.setImageResource(R.id.image,R.drawable.device_wirelessswitch);
//                      break;
//                  case "lumi.light.aqcn02"://智能灯泡
//                      baseViewHolder.setImageResource(R.id.image,R.drawable.lumilightcwac02);
//                      break;
//              }

            Glide.with(getContext())
                    .load(taskBean.getPhoto())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners( 5)))
                    .into((ImageView) baseViewHolder.getView(R.id.image));

//            if (taskBean.getGender()==1){
//                baseViewHolder.setText(R.id.sex,"男/"+taskBean.getAge()+"岁");
//            }else {
//                baseViewHolder.setText(R.id.sex,"女/"+taskBean.getAge()+"岁");
//            }
//
//
//            QMUIButton qmuiButton=baseViewHolder.getView(R.id.ruke);
//            qmuiButton.setRadius(QMUIDisplayHelper.dp2px(getContext(), 18));
//            qmuiButton.setChangeAlphaWhenPress(true);//点击改变透明度
        }catch (Exception e){
            e.printStackTrace();
        }



//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
}
