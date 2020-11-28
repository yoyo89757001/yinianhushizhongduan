package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityAddJiuBinding;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class AddJiuActivity extends AppCompatActivity {
    private ActivityAddJiuBinding binding=null;
    private BianQianAdapter bianQianAdapter=null;
    private List<WGInfoSave> dangBeanList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddJiuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        WGInfoSave bean=new WGInfoSave();
        bean.setName("自动化");
        bean.setModle("1");
//        WGInfoSave bean2=new WGInfoSave();
//        bean2.setName("定时");
//        bean2.setModle("2");
//        AddDangBean bean3=new AddDangBean();
//        bean3.setName("室外天气");
//        bean3.setModel("3");
        dangBeanList.add(bean);
        //dangBeanList.add(bean2);
        bianQianAdapter=new BianQianAdapter(R.layout.add_dang_item,dangBeanList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerview.setAdapter(bianQianAdapter);

        bianQianAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (dangBeanList.get(position).getName()){
                    case "自动化":
                        startActivity(new Intent(AddJiuActivity.this,ZDHListActivity.class));
                        break;
                    case "定时":
                       // startActivity(new Intent(AddJiuActivity.this,DingShiActivity.class));
                        break;
                    default:
                        startActivity(new Intent(AddJiuActivity.this,ZhiXingListActivity.class).putExtra("WGInfoSave",dangBeanList.get(position)));
                        break;
                }

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<WGInfoSave> wgInfoSavesss= MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,1).or().equal(WGInfoSave_.modle,"lumi.light.aqcn02").build().find();
                Log.d("ZDHListActivity", "wgInfoSavesss.size():" + wgInfoSavesss.size());
                dangBeanList.addAll(wgInfoSavesss);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bianQianAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }


    private static class BianQianAdapter extends BaseQuickAdapter<WGInfoSave, BaseViewHolder> implements LoadMoreModule {

        public BianQianAdapter(int layoutResId, @Nullable List<WGInfoSave> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, WGInfoSave taskBean) {
            baseViewHolder.setText(R.id.name,taskBean.getName());
            switch (taskBean.getModle()){
                case "1":
                    baseViewHolder.setBackgroundResource(R.id.image,R.drawable.zidonghua1);
                    break;
                case "2":
                    baseViewHolder.setBackgroundResource(R.id.image,R.drawable.dingshi1);
                    break;
//                case "lumi.sensor_magnet.aq2": //门窗传感器
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.device_door_window);
//                    break;
//                case "lumi.vibration.aq1"://动静贴
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.device_movement);
//                    break;
//                case "lumi.sensor_motion.aq2": //人体传感器
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.device_motion);
//                    break;
//                case "lumi.sensor_switch.aq3": //无线开关
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.device_wirelessswitch);
//                    break;
//                case "lumi.gateway.acn01":
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.device_gateway); //网关
//                    break;
//                case "lumi.light.aqcn02":
//                    baseViewHolder.setImageResource(R.id.image,R.drawable.lumilightcwac02); //智能灯泡
//                    break;
                default:
                    Glide.with(getContext())
                            .load(taskBean.getPhoto())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into((ImageView) baseViewHolder.getView(R.id.image));
                    break;
            }
            if (baseViewHolder.getAdapterPosition()==0){
                View v= baseViewHolder.getView(R.id.vvvv);
                ConstraintLayout.LayoutParams constraintLayout= (ConstraintLayout.LayoutParams) v.getLayoutParams();
                constraintLayout.height= QMUIDisplayHelper.dp2px(getContext(),10);
                v.setLayoutParams(constraintLayout);
                v.invalidate();
            }else {
                View v= baseViewHolder.getView(R.id.vvvv);
                ConstraintLayout.LayoutParams constraintLayout= (ConstraintLayout.LayoutParams) v.getLayoutParams();
                constraintLayout.height= QMUIDisplayHelper.dp2px(getContext(),1);
                v.setLayoutParams(constraintLayout);
                v.invalidate();
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishs(String finish) {
        if (finish.equals("finishfinish")){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}