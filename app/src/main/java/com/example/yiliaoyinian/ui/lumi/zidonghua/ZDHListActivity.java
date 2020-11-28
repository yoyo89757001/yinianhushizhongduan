package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityZDHListBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//选择已有的自动化,添加到触发条件
public class ZDHListActivity extends AppCompatActivity {
    private ActivityZDHListBinding binding=null;
    private BianQianAdapter bianQianAdapter=null;
    private List<WGInfoSave> dangBeanList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZDHListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WGInfoSave bean=new WGInfoSave();
        bean.setName("自动化");
        bean.setModle("1");
        WGInfoSave bean2=new WGInfoSave();
        bean2.setName("定时");
        bean2.setModle("2");
        WGInfoSave bean3=new WGInfoSave();
        bean3.setName("室外天气");
        bean3.setModle("3");
        dangBeanList.add(bean);dangBeanList.add(bean2);dangBeanList.add(bean3);


        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bianQianAdapter=new BianQianAdapter(R.layout.add_dang_item_zidonghua,dangBeanList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerview.setAdapter(bianQianAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<WGInfoSave> wgInfoSavesss=MyApplication.myApplication.getWgInfoSaveBox().getAll();
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
            baseViewHolder.setBackgroundResource(R.id.image,R.drawable.jiedian);

            //   baseViewHolder.setText(R.id.content_tv,taskBean.getNoteContent());


//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
        }
    }



}