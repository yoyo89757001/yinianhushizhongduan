package com.example.yiliaoyinian.adapter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.MachineBean;

import com.example.yiliaoyinian.R;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineAdapter extends BaseQuickAdapter<MachineBean.ResultBean, BaseViewHolder> implements LoadMoreModule {
    private int type=1;

    public MachineAdapter(int layoutResId, @Nullable List<MachineBean.ResultBean> data,int type) {
        super(layoutResId, data);
        this.type=type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MachineBean.ResultBean taskBean) {
        try {
            baseViewHolder.setText(R.id.title,taskBean.getDeviceName());
            switch (taskBean.getDeviceType()) {
                case "DEVICE_CAMERA":
                    baseViewHolder.setImageResource(R.id.image, R.drawable.shexiangtou);
                    break;
                case "DEVICE_BBP": //蓝牙血压计
                    baseViewHolder.setImageResource(R.id.image, R.drawable.lanya_item_bg);
                    break;
                case "DEVICE_BP": //普通血压计
                    baseViewHolder.setImageResource(R.id.image, R.drawable.pic_device_bp);
                    break;
                case "DEVICE_RADAR": //生物雷达
                    baseViewHolder.setImageResource(R.id.image, R.drawable.shuimianjiance);
                    break;
                case "DEVICE_BED": //床机
                    baseViewHolder.setImageResource(R.id.image, R.drawable.bingchuang);
                    break;
                case "1": //网关
                    baseViewHolder.setImageResource(R.id.image, R.drawable.device_gateway);
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        if (baseViewHolder.getLayoutPosition()==0){
//            baseViewHolder.setImageResource(R.id.image,R.drawable.lanya_item_bg);
//        }
//        if (baseViewHolder.getLayoutPosition()==1){
//
//        }
//        if (baseViewHolder.getLayoutPosition()==2){
//            baseViewHolder.setImageResource(R.id.image,R.drawable.shuimianjiance);
//        }
//


    //   switch (type){
      //     case 1://全部


//               break;
//           case 2://血压机
//               baseViewHolder.setText(R.id.title,taskBean.getDeviceName());
//               baseViewHolder.setImageResource(R.id.image,R.drawable.lanya_item_bg);
//
//
//               break;
//           case 3://视频监控
//               baseViewHolder.setText(R.id.title,taskBean.getDeviceName());
//               baseViewHolder.setImageResource(R.id.image,R.drawable.shexiangtou);
//              // baseViewHolder.setGone(R.id.v_bg,false);
//             //  ImageView view = baseViewHolder.getView(R.id.image);
//             //  view.setImageBitmap(grey(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.shexiangtou)));
//           //    view.setImageTintList(ColorStateList.valueOf(Color.parseColor("#31BFBFBF")));//设置灰度图，加上着色器
//
//               break;
//           case 4://体征监测
//               baseViewHolder.setText(R.id.title,taskBean.getName());
//               baseViewHolder.setImageResource(R.id.image,R.drawable.shuimianjiance);


         //      break;
   //    }




//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }
    private  Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }


    private static void setViewAndChildrenEnabled(View view, boolean isEnabled) {
        view.setEnabled(isEnabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, isEnabled);
            }
        }
    }


}
