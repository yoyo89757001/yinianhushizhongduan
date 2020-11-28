package com.example.yiliaoyinian.ui.lumi.menchuangchuanganqi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityMenChuangChuanGanSettingQiBinding;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
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


public class MenChuangChuanGanSettingQiActivity extends AppCompatActivity {
    private ActivityMenChuangChuanGanSettingQiBinding binding;
    private WGInfoSave save=null;
    private QMUITipDialog qmuiTipDialog = null;
    private MediaType JSONTYPE  = MediaType.parse("application/json");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMenChuangChuanGanSettingQiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save= (WGInfoSave) getIntent().getSerializableExtra("wgInfoSave");
        if (save!=null){
            try {
                binding.myTitle.setText(save.getName());
                binding.wangguanname.setText(save.getParentDid().substring(6));
                binding.name.setText(save.getName());
                binding.weizhi.setText(save.getWeizhi());
                binding.didname.setText(save.getDid().substring(6));
                binding.banben.setText(save.getFirmwareVersion());
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            return;
        }
        binding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(MenChuangChuanGanSettingQiActivity.this);
                builder.setTitle("修改设备名称")
                        .setSkinManager(QMUISkinManager.defaultInstance(MenChuangChuanGanSettingQiActivity.this))
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
                                    Box<WGInfoSave> saveBox= MyApplication.myApplication.getWgInfoSaveBox();
                                    save.setName(text.toString());
                                    saveBox.put(save);
                                    binding.name.setText(text);
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
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(MenChuangChuanGanSettingQiActivity.this);
                builder.setTitle("修改设备位置")
                        .setSkinManager(QMUISkinManager.defaultInstance(MenChuangChuanGanSettingQiActivity.this))
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
                                    Box<WGInfoSave> saveBox= MyApplication.myApplication.getWgInfoSaveBox();
                                    save.setWeizhi(text.toString());
                                    saveBox.put(save);
                                    binding.name.setText(text);
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
                new CommomDialog(MenChuangChuanGanSettingQiActivity.this, R.style.dialogs, "确定要移除设备?", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            qmuiTipDialog = new QMUITipDialog.Builder(MenChuangChuanGanSettingQiActivity.this)
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
                if (!MenChuangChuanGanSettingQiActivity.this.isFinishing())
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
                    if (!MenChuangChuanGanSettingQiActivity.this.isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "删除设备:"+stA);
                  //  Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                 //   if (healthBean.getCode()==0){
                        if (save!=null){
                            MyApplication.myApplication.getWgInfoSaveBox().remove(save);
                        }
                        EventBus.getDefault().post("ttyyhgggghj");
                        finish();
                //    }
                    //
                    // Log.d("WGSettingActivity", "查询设备属性的历史值json解密:"+jsonss);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }


}