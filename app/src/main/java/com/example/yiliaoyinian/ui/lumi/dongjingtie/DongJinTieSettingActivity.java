package com.example.yiliaoyinian.ui.lumi.dongjingtie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;


import com.example.yiliaoyinian.Beans.WGInfoSave;

import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;

import com.example.yiliaoyinian.databinding.ActivityDongJinTieSettingBinding;
import com.example.yiliaoyinian.dialog.CommomDialog;

import com.example.yiliaoyinian.ui.lumi.zidonghua.ZDHActivity1;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.FilterContext;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DongJinTieSettingActivity extends AppCompatActivity {
    private ActivityDongJinTieSettingBinding binding;
    private WGInfoSave save=null;
    private QMUITipDialog qmuiTipDialog = null;
    private MediaType JSONTYPE  = MediaType.parse("application/json");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDongJinTieSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save = (WGInfoSave) getIntent().getSerializableExtra("wgInfoSave");
        if (save != null) {
            try {
                binding.myTitle.setText(save.getName());
                binding.wangguanname.setText(save.getParentDid().substring(6));
                binding.name.setText(save.getName());
                binding.weizhi.setText(save.getWeizhi());
                binding.didname.setText(save.getDid().substring(6));
                binding.banben.setText(save.getFirmwareVersion());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return;
        }
        binding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(DongJinTieSettingActivity.this);
                builder.setTitle("修改设备名称")
                        .setSkinManager(QMUISkinManager.defaultInstance(DongJinTieSettingActivity.this))
                        .setPlaceholder("请输入设备名称")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                CharSequence text = builder.getEditText().getText();
                                if (text != null && text.length() > 0) {
                                    Box<WGInfoSave> saveBox = MyApplication.myApplication.getWgInfoSaveBox();
                                    save.setName(text.toString());
                                    saveBox.put(save);
                                    binding.name.setText(text);
                                    link_getlumiList(text.toString(),"");
                                }
                                dialog.dismiss();
                            }
                        })
                        .create(R.style.QMUI_Dialog).show();
            }
        });

        binding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(DongJinTieSettingActivity.this);
                builder.setTitle("修改设备位置")
                        .setSkinManager(QMUISkinManager.defaultInstance(DongJinTieSettingActivity.this))
                        .setPlaceholder("请输入设备位置")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                CharSequence text = builder.getEditText().getText();
                                if (text != null && text.length() > 0) {
                                    Box<WGInfoSave> saveBox = MyApplication.myApplication.getWgInfoSaveBox();
                                    save.setWeizhi(text.toString());
                                    saveBox.put(save);
                                    binding.name.setText(text);
                                    link_getlumiList("",text.toString());
                                }
                                dialog.dismiss();
                            }
                        })
                        .create(R.style.QMUI_Dialog).show();
            }
        });

        binding.yichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommomDialog(DongJinTieSettingActivity.this, R.style.dialogs, "确定要移除设备?", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            qmuiTipDialog = new QMUITipDialog.Builder(DongJinTieSettingActivity.this)
                                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                    .setTipWord("移除中...")
                                    .create();
                            qmuiTipDialog.show();
                            loginApiununBind(save.getDid());
                            dialog.dismiss();
                        }
                    }
                }).setTitle("确认").setPositiveButton("确定").show();
            }
        });
        binding.rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DongJinTieSettingActivity.this, ZDHActivity1.class).putExtra("did", save.getDid()));
            }
        });

    }

    private void loginApiununBind(String did){ //解除绑定
        //开启网关添加子设备模式
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/unbind";
        JSONObject json =new  JSONObject();
        try {
            json.put("did", did);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);
        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign= FilterContext.createSign(header, MyApplication.APPKEY, false);
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();
        String[] ssee =body.split("\n");

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (!DongJinTieSettingActivity.this.isFinishing())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("RenTiChuanGanQiActivity", "查询设备属性请求地址:" + call.request().url());
                try {
                    if (!DongJinTieSettingActivity.this.isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "查询设备属性的历史值request:"+stA);
                   // Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    if (save!=null){
                        MyApplication.myApplication.getWgInfoSaveBox().remove(save);
                    }
                    EventBus.getDefault().post("ttyyhgggghj");
                    finish();

                    // Log.d("WGSettingActivity", "查询设备属性的历史值json解密:"+jsonss);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }

    private void link_getlumiList(String name,String place) {
        JSONObject json =new  JSONObject();
        try {
            json.put("did", save.getDid());
            if (name!=""){
                json.put("name", name);
            }
            if (place!=""){
                json.put("place", place);
            }
        } catch (JSONException e) {
            Log.d("Fragment3", e.getMessage()+"AllConnects");
        }
        // Log.d("Fragment3", "AllConnects1111");
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(RequestBody.create(json.toString(),JSONTYPE))
                .url(Consts.URL2+"/app/lvmi/save");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        //  Log.d("Fragment3", "AllConnects1111");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                String ss=null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("AllConnects", "修改设备名"+ss);

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }


}