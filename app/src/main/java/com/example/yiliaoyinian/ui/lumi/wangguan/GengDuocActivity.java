package com.example.yiliaoyinian.ui.lumi.wangguan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.FilterContext;
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

public class GengDuocActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatSeekBar seekBar;
    private int seekInt = 50;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private String did ="";
    private Box<WGInfoSave> wgInfoSaveBox=null;
    private  WGInfoSave save;
    private TextView fffeee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geng_duoc);
        wgInfoSaveBox=MyApplication.myApplication.getWgInfoSaveBox();
        did=getIntent().getStringExtra("did");
        findViewById(R.id.fanhui).setOnClickListener(this);
        seekBar=findViewById(R.id.seekbar);
        seekBar.setMax(100);
        fffeee=findViewById(R.id.fffeee);
        fffeee.setText("50%");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekInt = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                fffeee.setText(seekInt+"%");
                loginupdataInfo(did,seekInt);
            }
        });

        try {
            save =  wgInfoSaveBox.query().equal(WGInfoSave_.did,did).build().findFirst();
            Log.d("WGSettingActivity", "save:" + save);
            if (save!=null){
              //  seekInt = save.getArgb();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        seekBar.setProgress(seekInt);


    }


    private void loginupdataInfo(String did,int light){//修改网关信息
        //修改网关信息
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/properties/write";
        JSONObject jsonObject =new  JSONObject();
        // JSONObject json =new  JSONObject();
        //JSONObject data =new  JSONObject();
        try {
//            json.put("brightness_level", 10); //夜灯亮度，0~100
//            json.put("alarm_status", 1);//报警状态，0:没报警 ，1：报警
//            json.put("corridor_light_status", 1);//夜灯 1：打开 0：关闭
//            json.put("system_volume", 80);//系统音量0～100
//            json.put("argb_value", Color.parseColor("#"+argb));//夜灯ARGB

            jsonObject.put("did",did+"");
            jsonObject.put("data",new JSONObject().put("system_volume", light));

            Log.d("WGSettingActivity", jsonObject.toString()+"参数");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json.put("installCode", "")
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(jsonObject.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
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

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GengDuocActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("WGSettingActivity", "修改网关信息:"+stA);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        finish();
    }
}