package com.example.yiliaoyinian.ui.lumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.ScrollBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.ScrollLeftAdapter;
import com.example.yiliaoyinian.adapter.ScrollRightAdapter;
import com.example.yiliaoyinian.databinding.ActivityAddDeviceLumiBinding;
import com.example.yiliaoyinian.ui.lumi.wangguan.AddWGActivity1;
import com.iammert.library.ui.multisearchviewlib.MultiSearchView;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


public class AddDeviceLumiActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddDeviceLumiBinding binding = null;
    private List<String> left;
    private List<ScrollBean> right;
    private ScrollLeftAdapter leftAdapter;
    private ScrollRightAdapter rightAdapter;
    //右侧title在数据中所对应的position集合
    private List<Integer> tPosition = new ArrayList<>();
    private Context mContext;
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private GridLayoutManager rightManager;
    private QMUITipDialog qmuiTipDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDeviceLumiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fanhui.setOnClickListener(this);
        mContext = this;


        binding.searchview.setSearchViewListener(new MultiSearchView.MultiSearchViewListener() {
            @Override
            public void onTextChanged(int i, @NotNull CharSequence charSequence) {
                Log.d("AddDeviceLumiActivity", "charSequence:" + charSequence.toString());
            }

            @Override
            public void onSearchComplete(int i, @NotNull CharSequence charSequence) {
                Log.d("AddDeviceLumiActivity", "charSequence:" + charSequence.toString());
            }

            @Override
            public void onSearchItemRemoved(int i) {

            }

            @Override
            public void onItemSelected(int i, @NotNull CharSequence charSequence) {

            }
        });


        initData();
        initLeft();
        initRight();

    }


    private void initRight() {

        rightManager = new GridLayoutManager(mContext, 2);

        if (rightAdapter == null) {
            rightAdapter = new ScrollRightAdapter(R.layout.layout_right_title, R.layout.scroll_right, right);
            binding.recRight.setLayoutManager(rightManager);
            binding.recRight.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , 0
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3)));
                }
            });
            binding.recRight.setAdapter(rightAdapter);
        } else {
            rightAdapter.notifyDataSetChanged();
        }
        //rightAdapter.setList(right);
        // rightAdapter.setNewInstance(right);

        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("AddDeviceLumiActivity", "position:" + position);
                if (right.get(position).getScrollItemBean().getType().equals("1")){//网关
                    startActivity(new Intent(AddDeviceLumiActivity.this, AddWGActivity1.class));
                }else {//其它子设备
                    List<WGInfoSave> saveList= MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,1).build().find();
                    if (saveList.size()>1){
                        try {
                            String[] items = new String[saveList.size()];
                            for (int i = 0; i < saveList.size(); i++) {
                                String sp = saveList.get(i).getDid();
                                String sa=sp.substring(6);
                                items[i] = saveList.get(i).getName()+" "+sa;
                            }
                            final int checkedIndex = 1;
                            new QMUIDialog.CheckableDialogBuilder(AddDeviceLumiActivity.this)
                                    .setCheckedIndex(checkedIndex)
                                    .setSkinManager(QMUISkinManager.defaultInstance(AddDeviceLumiActivity.this))
                                    .addItems(items, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent=new Intent(AddDeviceLumiActivity.this, AddSonDeviceActivity.class);
                                            intent.putExtra("type",right.get(position).getScrollItemBean().getType());
                                            intent.putExtra("did",saveList.get(which).getDid());
                                            startActivity(intent);
                                        }
                                    })
                                    .create(R.style.QMUI_Dialog).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if (saveList.size()==1){
                        Intent intent=new Intent(AddDeviceLumiActivity.this, AddSonDeviceActivity.class);
                        intent.putExtra("type",right.get(position).getScrollItemBean().getType());
                        intent.putExtra("did",saveList.get(0).getDid());
                        startActivity(intent);
                    }else {
                        qmuiTipDialog = new QMUITipDialog.Builder(AddDeviceLumiActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("请先添加网关")
                                .create();
                        qmuiTipDialog.show();
                        binding.fanhui.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                qmuiTipDialog.dismiss();
                            }
                        }, 1500);
                    }
                }
            }
        });

        //设置右侧初始title
        if (right.get(first).isHead()) {
            binding.rightTitle.setText(right.get(first).getHeader());
        }

        binding.recRight.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                tHeight = binding.rightTitle.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (right.get(first).isHead()) {
                    //获取此组名item的view
                    View view = rightManager.findViewByPosition(first);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= tHeight) {
                            binding.rightTitle.setY(view.getTop() - tHeight);
                        } else {
                            //否则不设置
                            binding.rightTitle.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = rightManager.findFirstVisibleItemPosition();
                if (first != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    first = firstPosition;
                    //不设置Y轴的偏移量
                    binding.rightTitle.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (right.get(first).isHead()) {
                        binding.rightTitle.setText(right.get(first).getHeader());
                    } else {
                        binding.rightTitle.setText(right.get(first).getScrollItemBean().getType());
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < left.size(); i++) {
                    if (left.get(i).equals(binding.rightTitle.getText().toString())) {
                        leftAdapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
                if (rightManager.findLastCompletelyVisibleItemPosition() == right.size() - 1) {
                    leftAdapter.selectItem(left.size() - 1);
                }
            }
        });
    }

    private void initLeft() {
        if (leftAdapter == null) {
            leftAdapter = new ScrollLeftAdapter(R.layout.scroll_left, left);
            binding.recLeft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
          //  binding.recLeft.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            binding.recLeft.setAdapter(leftAdapter);
        } else {
            leftAdapter.notifyDataSetChanged();
        }

      //  leftAdapter.setList(left);
        //  leftAdapter.setNewInstance(left);
        leftAdapter.addChildClickViewIds(R.id.item);
        leftAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.item:
                        leftAdapter.selectItem(position);
                        rightManager.scrollToPositionWithOffset(tPosition.get(position), 0);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
        }
    }

    //获取数据(若请求服务端数据,请求到的列表需有序排列)
    private void initData() {
        left = new ArrayList<>();
        left.add("网关");
        left.add("传感器");
        left.add("灯光照明");
        left.add("无线遥控器");


        right = new ArrayList<>();

        right.add(new ScrollBean(true, left.get(0)));//头部
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("网关", "1")));

        right.add(new ScrollBean(true, left.get(1)));//头部
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("人体传感器", "2")));
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("门窗传感器", "3")));
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("温湿度传感器", "4")));
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("动静贴传感器", "5")));


        right.add(new ScrollBean(true, left.get(2)));
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("智能灯泡", "6")));

        right.add(new ScrollBean(true, left.get(3)));
        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("无线开关", "7")));


        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).isHead()) {
                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
                tPosition.add(i);
            }
        }
    }

    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }
}