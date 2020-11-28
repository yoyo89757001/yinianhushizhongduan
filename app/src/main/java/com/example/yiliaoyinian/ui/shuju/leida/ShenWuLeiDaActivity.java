package com.example.yiliaoyinian.ui.shuju.leida;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yiliaoyinian.Beans.LeiDaBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.views.X5WebView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tencent.smtt.sdk.WebView;
import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import static android.view.View.GONE;


public class ShenWuLeiDaActivity extends BaseActivity {

    View fanhui;
    X5WebView webview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_wu_lei_da);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fanhui=findViewById(R.id.fanhui);
        webview=findViewById(R.id.webview);
        progressBar=findViewById(R.id.progressBar);

        webview.setWebChromeClient(new WebChromeClient());
        progressBar.setMax(100);

        link_complete(getIntent().getStringExtra("deviceCode"));
    }


        public class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {

        @Override
        public void onProgressChanged(WebView webView, int i) {
            Log.d("WebChromeClient", "i:" + i);
            if (i == 100) {
               progressBar.setVisibility(GONE);
            }
            progressBar.setProgress(i);
            super.onProgressChanged(webView, i);
        }
    }


    private void link_complete(String deviceCode) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/sleep/getReportPage?deviceCode=" + deviceCode);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", fanhui);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "雷达" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LeiDaBean logingBe = gson.fromJson(jsonObject, LeiDaBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            webview.loadUrl(logingBe.getResult().getReportPage());
                                        }
                                    });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }



}
