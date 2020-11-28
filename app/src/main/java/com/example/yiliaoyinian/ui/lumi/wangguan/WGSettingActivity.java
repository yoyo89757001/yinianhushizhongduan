package com.example.yiliaoyinian.ui.lumi.wangguan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.Auth01;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.LuMIDevBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.LumiDevAdapter;
import com.example.yiliaoyinian.ui.lumi.dengpao.DPSettingActivity;
import com.example.yiliaoyinian.ui.lumi.dongjingtie.DongJinTieActivity;
import com.example.yiliaoyinian.ui.lumi.menchuangchuanganqi.MenChuangChuanGanQiActivity;
import com.example.yiliaoyinian.ui.lumi.rentichuanganqi.RenTiChuanGanQiActivity2;
import com.example.yiliaoyinian.ui.lumi.wenshidu.WenShiDuActivity;
import com.example.yiliaoyinian.ui.lumi.wuxiankaiguan.WuXianKaiGuanActivity;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.FilterContext;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WGSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ColorPickerView  colorPickerView=null;
    private TextView infiss;
    private String colors="FFFFFF";
    private int ligt = 50;
    private RecyclerView recyclerview;
    private SwitchCompat switchCompat;
    private AppCompatSeekBar seekBar;
   //private SmartRefreshLayout refreshLayout;
    private LumiDevAdapter adapter;
   // private List<RuKeBean.ResultBean> taskBeanList = new ArrayList<>();
    private QMUIButton bt1,bt2,bt3,bt4;
    private ImageView shezhi;
   // private LinearLayout bottombar;
   // private RelativeLayout relativeLayout;
    private  LinearLayoutManager manager;
    //private boolean sitop =false;
    //private int mun = 0;
    private LinearLayout scrollView;
  //  private GestureDetector gestureDetector;//触摸手势监听事件
    private Vector<WGInfoSave> divescerBeanVector=new Vector<>();
   // private static boolean isDH = false;
   // private ImageView ima;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private String APPID = "4113230e4535848642a52f0b";
    private String APPKEY = "2HoQ1X1420pGAC6DYMzvVKqypV7t9W9p";
    private String wgDid = "";
    private Box<WGInfoSave> wgInfoSaveBox=null;
    private boolean isOpen=true;
    private  WGInfoSave save;
    //private QMUILoadingView loadingView;
    private CollapsingToolbarLayout mCollapsingTopBarLayout;
    private TextView shuliang;
    private AppBarLayout ffff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_g_setting);
        wgInfoSaveBox=MyApplication.myApplication.getWgInfoSaveBox();
        ffff=findViewById(R.id.ffff);
        EventBus.getDefault().register(this);
        wgDid=getIntent().getStringExtra("did");
        mCollapsingTopBarLayout=findViewById(R.id.collapsing_topbar_layout);
       // loadingView=findViewById(R.id.jiazai);
      //  ima=findViewById(R.id.ima);
        scrollView=findViewById(R.id.scrollView);
       // relativeLayout=findViewById(R.id.topbar);
        findViewById(R.id.fanhui).setOnClickListener(this);
        infiss=findViewById(R.id.infiss);
        shezhi=findViewById(R.id.shezhi);
        shezhi.setOnClickListener(this);
        recyclerview=findViewById(R.id.recyclerview);
        switchCompat=findViewById(R.id.switcher);
        seekBar=findViewById(R.id.seekbar);
        seekBar.setMax(100);
        //bottombar=findViewById(R.id.botbar);
       // bottombar.setOnClickListener(this);
        colorPickerView=findViewById(R.id.colorPickerView);
        bt1=findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2=findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3=findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        bt4=findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
        bt1.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt1.setChangeAlphaWhenPress(true);//点击改变透明度
        bt2.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt2.setChangeAlphaWhenPress(true);//点击改变透明度
        bt3.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt3.setChangeAlphaWhenPress(true);//点击改变透明度
        bt4.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt4.setChangeAlphaWhenPress(true);//点击改变透明度
        try {
            save =  wgInfoSaveBox.query().equal(WGInfoSave_.did,wgDid).build().findFirst();
            Log.d("WGSettingActivity", "save:" + save);
            if (save!=null){
                colors = save.getArgb();
                ligt = save.getLight();
                isOpen=save.isOPen();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        switchCompat.setChecked(isOpen);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setEnabled(false);
                isOpen = b;
                if (save!=null){
                    save.setOPen(b);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,wgDid,ligt,isOpen,3);
            }
        });


        View headView = getLayoutInflater().inflate(R.layout.okgfdtthead, null);
        adapter = new LumiDevAdapter(R.layout.wgsetting_item, divescerBeanVector);
        //添加头布局尾布局
        adapter.addHeaderView(headView);
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        View view1 = LayoutInflater.from(this).inflate(R.layout.anull_data, null);
        adapter.setEmptyView(view1);
        shuliang =headView.findViewById(R.id.shuliang);
        //item 点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("点击了","点击了");
                switch (divescerBeanVector.get(position).getModle()){
//                    luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_magnet.aq2","门窗传感器"));
//                    luMiZiYuanBox.put(new LuMiZiYuan("lumi.vibration.aq1","动静贴"));
//                    luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_motion.aq2","人体传感器"));
//                    luMiZiYuanBox.put(new LuMiZiYuan("lumi.sensor_switch.aq3","无线开关"));
                    case "lumi.sensor_motion.aq2":
                        startActivity(new Intent(WGSettingActivity.this, RenTiChuanGanQiActivity2.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                    case "lumi.vibration.aq1":
                        startActivity(new Intent(WGSettingActivity.this, DongJinTieActivity.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                    case "lumi.sensor_magnet.aq2":
                        startActivity(new Intent(WGSettingActivity.this, MenChuangChuanGanQiActivity.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                    case "lumi.sensor_switch.aq3":
                        startActivity(new Intent(WGSettingActivity.this, WuXianKaiGuanActivity.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                    case "lumi.light.aqcn02":
                        startActivity(new Intent(WGSettingActivity.this, DPSettingActivity.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                    case "lumi.weather.v1":
                        startActivity(new Intent(WGSettingActivity.this, WenShiDuActivity.class).putExtra("saveinfo",divescerBeanVector.get(position)));
                        break;
                }
            }
        });
        // 先注册需要点击的子控件id（注意，请不要写在convert方法里）
        adapter.addChildClickViewIds(R.id.ruke);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.ruke) {
                    Log.d("DAFragment3", "position:" + position);

                }
            }
        });
        seekBar.setProgress(ligt);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ligt = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setLight(ligt);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,wgDid,ligt,isOpen,0);
            }
        });

        //colorPickerView.setPaletteDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.zzzfff)));
        colorPickerView.setActionMode(ActionMode.LAST);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colors=envelope.getHexCode().substring(2,8);
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                    if (save!=null){
                        save.setArgb(colors);
                        wgInfoSaveBox.put(save);
                    }
                    loginupdataInfo(colors,wgDid,ligt,isOpen,4);
            }
        });
        colorPickerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_MOVE){
                    Log.d("WGSettingActivity", "进 得得得");
                    mCollapsingTopBarLayout.stopNestedScroll();
                }
                return false;
            }
        });
        infiss.setText("颜色值: #"+colors+"   "+"亮度: "+ligt);
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) colorPickerView.getLayoutParams();
      params.width = (int) (QMUIDisplayHelper.getScreenHeight(getApplicationContext())*0.25f);
      params.height = (int) (QMUIDisplayHelper.getScreenHeight(getApplicationContext())*0.25f);
      colorPickerView.setLayoutParams(params);
      colorPickerView.invalidate();

        CollapsingToolbarLayout.LayoutParams param2s = (CollapsingToolbarLayout.LayoutParams) scrollView.getLayoutParams();
        param2s.height = (int) (QMUIDisplayHelper.getScreenHeight(getApplicationContext())*0.73f);
        scrollView.setLayoutParams(param2s);
        scrollView.invalidate();


//        mCollapsingTopBarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.i("TAG", "scrim: " + animation.getAnimatedValue());
//            }
//        });
//
//        mCollapsingTopBarLayout.addOnOffsetUpdateListener(new QMUICollapsingTopBarLayout.OnOffsetUpdateListener() {
//            @Override
//            public void onOffsetChanged(QMUICollapsingTopBarLayout layout, int offset, float expandFraction) {
//                Log.i("TAG", "offset = " + offset + "; expandFraction = " + expandFraction);
//            }
//        });

//      LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) recyclerview.getLayoutParams();
//      params2.height = (int) ((QMUIDisplayHelper.getScreenHeight(getApplicationContext())-relativeLayout.getHeight())*0.75f);
//      recyclerview.setLayoutParams(params2);
//      recyclerview.invalidate();

//       gestureDetector = new GestureDetector(this,new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                //  Log.d("WGSettingActivity", "e1.getY2():" + e1.getY());
//               // Log.d("WGSettingActivity", "e2.getY2():" + e2.getY());
//                //在滑动的方法里，进行手势滑动事件的判断
//                //e1代表手指按在屏幕上的X轴坐标点，e2代表手指离开屏幕上的X轴坐标点。
//                if(e1.getY() - e2.getY() < 0 && Math.abs((int)(e1.getY() - e2.getY())) > 40){
//                    //向右滑动的判断，如果手指从左向右滑动，就走这个方法
//                     down();
//                    return true;
//                }else if(e1.getY() - e2.getY() > 0 && Math.abs((int)(e1.getY() - e2.getY())) > 40){
//                    //向左滑动的判断，如果手指从右向左滑动，就走这个方法
//                    up();
//                    return true;
//                }else{
//                    return false;
//                }
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//               // Log.d("WGSettingActivity", "e1.getY():" + e1.getY());
//              //  Log.d("WGSettingActivity", "e2.getY():" + e2.getY());
//                //在滑动的方法里，进行手势滑动事件的判断
//                //e1代表手指按在屏幕上的X轴坐标点，e2代表手指离开屏幕上的X轴坐标点。
//                if(e1.getY() - e2.getY() < 0 && Math.abs((int)(e1.getY() - e2.getY())) > 40){
//                    //向右滑动的判断，如果手指从左向右滑动，就走这个方法
//                     down();
//                    return true;
//                }else if(e1.getY() - e2.getY() > 0 && Math.abs((int)(e1.getY() - e2.getY())) > 40){
//                    //向左滑动的判断，如果手指从右向左滑动，就走这个方法
//                    up();
//                    return true;
//                }else{
//                    return false;
//                }
//            }
//        });


//       String did =  MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getDid();
//       if (did==null || did.equals("")){
//           ToastUtils.setFAIL("请先添加网关",colorPickerView);
//       }else

       // loginApiquer(wgDid);

        CoordinatorLayout.LayoutParams params1= (CoordinatorLayout.LayoutParams) ffff.getLayoutParams();
        params1.height= (int) (QMUIDisplayHelper.getScreenHeight(WGSettingActivity.this)*0.73f);
        ffff.setLayoutParams(params1);
        ffff.invalidate();
        loginApi(wgDid);
        link_getlumiList();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("fsdggdgfdgeeefdg")){
            finish();
        }
        if (msgWarp.equals("ttyyhgggghj")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    divescerBeanVector.clear();
                    List<WGInfoSave> wgInfoSaves=MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,3).and().equal(WGInfoSave_.parentDid,wgDid).build().find();
                    divescerBeanVector.addAll(wgInfoSaves);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  loadingView.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                colors = "D8BA4D";
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                loginupdataInfo(colors,wgDid,ligt,isOpen,4);
                break;
            case R.id.bt2:
                colors = "C9FBE4";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,wgDid,ligt,isOpen,4);
                break;
            case R.id.bt3:
                colors = "5770ED";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,wgDid,ligt,isOpen,4);
                break;
            case R.id.bt4:
                colors = "F3DB0B";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,wgDid,ligt,isOpen,4);
                break;
            case R.id.shezhi:
                startActivity(new Intent(this, WGInfosListActivity.class).putExtra("did",wgDid).putExtra("zunm",divescerBeanVector.size()));

                break;

            case R.id.fanhui:
                finish();
                break;
        }
    }




//    private void loginApiquer(String did){
////        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
////        params.set("client_id","4113230e4535848642a52f0b")
////        params.set("response_type","code")
////        params.set("state","yinian")
////        params.set("account","13642730363")
////        params.set("password","ABC123456")
////        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")
//
//        //开启网关添加子设备模式
//        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/child/query?did="+did;
//        // val json = JSONObject()
//        //  json.put("did", did)
//        //  val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
//        String time= String.valueOf(System.currentTimeMillis());
//        HashMap<String,String> header = new HashMap<>(4);
//
//        header.put(CommonRequest.REQUEST_HEADER_APPID,APPID);
//        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
//        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
//
//        String sign= FilterContext.createSign(header, APPKEY, false);
//
//        Request builder = new  Request.Builder()
//                .addHeader("Content-Type", "application/json")
//                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
//                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
//                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
//                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
//                // .post(body.toRequestBody()).url(url).build()
//                .get().url(url).build();
//
//        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                StringBuilder bu = new  StringBuilder();
//                try {
//                    String stA = response.body().string();
//                    Log.d("WGSettingActivity", "查询子设备返回结果:"+stA);
//                    Auto7 healthBean = JSON.parseObject(stA,Auto7.class);
//                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(APPKEY));
//                    JSONArray json  = JSON.parseArray(jsonss);
//                    divescerBeanVector.clear();
//                    for (Object o : json) {
//                        Log.d("WGSettingActivity", "子设备:"+o.toString());
//                       // divescerBeanVector.add(JSON.parseObject(o.toString(),ZiDivescerBean.class));
//                    }
//
//
//                } catch (Exception e) {
//                   Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
//                }
//            }
//        });
//
//    }

    private void loginApiunBind(String did){ //解除绑定
//        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
//        params.set("client_id","4113230e4535848642a52f0b")
//        params.set("response_type","code")
//        params.set("state","yinian")
//        params.set("account","13642730363")
//        params.set("password","ABC123456")
//        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")

        //开启网关添加子设备模式
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/unbind";
        JSONObject json =new  JSONObject();
        try {
            json.put("did", did);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json.put("installCode", "")
      //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign=FilterContext.createSign(header, APPKEY, false);
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();

                MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        StringBuilder bu = new  StringBuilder();
                        try {
                            String stA = response.body().string();
                            Log.d("WGSettingActivity", "解除绑定设备:"+stA);

                        } catch (Exception e) {
                            Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                        }
                    }
                });

    }


    private void loginupdataInfo(String argb,String did,int light,boolean isOpen,int type){//修改网关信息
        //修改网关信息
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/properties/write";
        JSONObject jsonObject =new  JSONObject();
       // JSONObject json =new  JSONObject();
        JSONObject data =new  JSONObject();
        try {
//            json.put("brightness_level", 10); //夜灯亮度，0~100
//            json.put("alarm_status", 1);//报警状态，0:没报警 ，1：报警
//            json.put("corridor_light_status", 1);//夜灯 1：打开 0：关闭
//            json.put("system_volume", 80);//系统音量0～100
//            json.put("argb_value", Color.parseColor("#"+argb));//夜灯ARGB

            jsonObject.put("did",did+"");
            switch (type){
                case 0://亮度
                    jsonObject.put("data",new JSONObject().put("brightness_level", light));
                    break;
                case 1://报警
                    jsonObject.put("data",new JSONObject().put("alarm_status", 1));
                    break;
                case 2://音量
                    jsonObject.put("data",new JSONObject().put("system_volume", 80));
                    break;
                case 3://开关
                    jsonObject.put("data",new JSONObject().put("corridor_light_status", isOpen?1:0));
                    break;
                case 4://颜色
                    jsonObject.put("data",new JSONObject().put("argb_value",Integer.valueOf(argb,16)));
                    break;
            }
            Log.d("WGSettingActivity", jsonObject.toString()+"参数");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json.put("installCode", "")
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(jsonObject.toString(), AESUtil.getAESKey(APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign=FilterContext.createSign(header, APPKEY, false);
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switchCompat.setEnabled(true);
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               // StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "修改网关信息:"+stA);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switchCompat.setEnabled(true);
                        }
                    });
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });

    }


    private QMUITipDialog qmuiTipDialog = null;
    private void loginApi(String did){
        String  url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/query/detail?dids="+did;
//        val jsons = JsonArray()
//        val json =  JSONObject()
//        jsons.add(did);
//        json.put("dids", jsons)
        // val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
        //   println("ssssddfe ${json.toString()}")
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);
        header.put(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG, "zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        // val body:String = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim()
        // header[CommonRequest.REQUEST_HEADER_PAYLOAD] = body.trim()
        header.put(CommonRequest.REQUEST_HEADER_TIME, time);
        //    println("body加密前 ${json.toString()}  key长度 ${AESUtil.getAESKey(APPKEY).size} ")
        //   println("解密body:${AESUtil.decryptCbc(body, AESUtil.getAESKey(APPKEY))}")

        String sign = FilterContext.createSign(header, MyApplication.APPKEY, false);
        // println("body：$body");  println("sign: $sign")
        Request builder =new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                //  .post(body.toRequestBody()).url(url).build()
                .get().url(url).build();
        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    //println("请求地址:${call.request().url}   查询设备返回结果:$stA");
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    JSONArray json = JSON.parseArray(jsonss);
                    for (Object o : json) {
                        Auth01 healthBean1 = JSON.parseObject(o.toString(), Auth01.class);
                        Box<WGInfoSave> wgInfoSave = MyApplication.myApplication.getWgInfoSaveBox();
                        WGInfoSave vv = wgInfoSave.query().equal(WGInfoSave_.did, did).build().findFirst();
                        if (vv != null) {
                            vv.setFirmwareVersion(healthBean1.getFirmwareVersion());
                            wgInfoSave.put(vv);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (healthBean1.getState()==0){
                                    qmuiTipDialog = new QMUITipDialog.Builder(WGSettingActivity.this)
                                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                                            .setTipWord("网关已经离线")
                                            .create();
                                    if (!WGSettingActivity.this.isFinishing())
                                    qmuiTipDialog.show();
                                }
                                bt1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!WGSettingActivity.this.isFinishing()){
                                            if (qmuiTipDialog!=null)
                                            qmuiTipDialog.dismiss();
                                        }
                                    }
                                }, 2000);
                            }
                        });
                        Log.d("WGSettingActivity", healthBean1.toString()+"");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void link_getlumiList() {
        JSONObject json =new  JSONObject();
        try {
            json.put("pinlessUser", MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getPhone());
            json.put("modelType", 3);
            json.put("parentDid",wgDid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WGSettingActivity", json.toString()+"ddddd");
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(RequestBody.create(json.toString(),JSONTYPE))
                .url(Consts.URL2+"/app/lvmi/list");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                List<WGInfoSave> wgInfoSaves=wgInfoSaveBox.query().equal(WGInfoSave_.modelType,3).and().equal(WGInfoSave_.parentDid,wgDid).build().find();
                Log.d("WGSettingActivity", "子设备个数:" + wgInfoSaves.size());
                divescerBeanVector.addAll(wgInfoSaves);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shuliang.setText(wgInfoSaves.size()+"个");
                        //loadingView.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                String ss=null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("LoginActivity", "获取绿米子设备数据"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LuMIDevBean logingBe = gson.fromJson(jsonObject, LuMIDevBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getData()!=null){
                            if (logingBe.getData().size()>0){
                                List<WGInfoSave> saves =  wgInfoSaveBox.query().equal(WGInfoSave_.modelType,3).and().equal(WGInfoSave_.parentDid,save.getDid()).build().find();
                                for (WGInfoSave save : saves) {
                                    wgInfoSaveBox.remove(save);
                                }
                                for (LuMIDevBean.DataDTO datum : logingBe.getData()) {
                                  WGInfoSave wgInfoSave=new WGInfoSave();
                                  wgInfoSave.setModle(datum.getModel());
                                  wgInfoSave.setWeizhi(datum.getPlace());
                                  wgInfoSave.setName(datum.getName());
                                  wgInfoSave.setState(datum.getStatus());
                                  wgInfoSave.setFirmwareVersion(datum.getFirmwareVersion());
                                  wgInfoSave.setDid(datum.getDid());
                                  wgInfoSave.setParentDid(datum.getParentDid());
                                  wgInfoSave.setModelType(datum.getModelType());
                                  wgInfoSave.setPhoto(datum.getStr1());
                                  wgInfoSaveBox.put(wgInfoSave);
                                }
                                List<WGInfoSave> wgInfoSaves=wgInfoSaveBox.query().equal(WGInfoSave_.modelType,3).and().equal(WGInfoSave_.parentDid,wgDid).build().find();
                                Log.d("WGSettingActivity", "子设备个数:" + wgInfoSaves.size());
                                divescerBeanVector.addAll(wgInfoSaves);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shuliang.setText(wgInfoSaves.size()+"个");
                                        //loadingView.setVisibility(View.GONE);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }


}