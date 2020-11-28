package com.example.yiliaoyinian.ui.wode;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.yiliaoyinian.Beans.MssagesInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WenZhangInfoActivity extends BaseActivity implements View.OnClickListener {

    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.topbar)
//    RelativeLayout topbar;
//    @BindView(R.id.title)
    TextView title;
    TextView contentTv;
//    @BindView(R.id.webview)
//    X5WebView webview;
//    @BindView(R.id.progressBar)
//    ProgressBar mProgressBar;
//    private int newProgress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_zhang_info);
        findViewById(R.id.fanhui).setOnClickListener(this);
        fanhui=findViewById(R.id.fanhui);
        title=findViewById(R.id.title);
        contentTv=findViewById(R.id.content_tv);


//        webview.loadUrl("http://www.hao123.com");
//        webview.setWebChromeClient(new WebChromeClient());
//        mProgressBar.setMax(100);

        link_complete(getIntent().getLongExtra("idid", 0));

    }

//    public class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {
//
//        @Override
//        public void onProgressChanged(WebView webView, int i) {
//            Log.d("WebChromeClient", "i:" + i);
//            newProgress=i;
//            if (newProgress == 100) {
//               mProgressBar.setVisibility(GONE);
//            }
//            mProgressBar.setProgress(newProgress);
//            super.onProgressChanged(webView, i);
//        }
//
//
//    }


    private void link_complete(long pageNum) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/mjArticle/getById?id=" + pageNum);
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
                    Log.d("LoginActivity", "系统通知" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    MssagesInfoBean logingBe = gson.fromJson(jsonObject, MssagesInfoBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult()!=null) {
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                title.setText(logingBe.getResult().getTitle());
                                                RichText
                                                        .from(logingBe.getResult().getContent()) // 数据源
                                                        .autoFix(true) // 是否自动修复，默认true
                                                        .autoPlay(true) // gif图片是否自动播放
                                                        .showBorder(false) // 是否显示图片边框
                                                        .borderColor(Color.RED) // 图片边框颜色
                                                        .borderSize(10) // 边框尺寸
                                                        .borderRadius(50) // 图片边框圆角弧度
                                                        .scaleType(ImageHolder.ScaleType.fit_center) // 图片缩放方式
                                                        .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
                                                        // .fix(imageFixCallback) // 设置自定义修复图片宽高
                                                        //.fixLink(linkFixCallback) // 设置链接自定义回调
                                                        // .noImage(true) // 不显示并且不加载图片
                                                        .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
                                                        .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
                                                        //.imageClick(onImageClickListener) // 设置图片点击回调
                                                        // .imageLongClick(onImageLongClickListener) // 设置图片长按回调
                                                        // .urlClick(onURLClickListener) // 设置链接点击回调
                                                        // .urlLongClick(onUrlLongClickListener) // 设置链接长按回调
//                                                    .placeHolder(new DrawableGetter() {
//                                                        @Override
//                                                        public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
//                                                            holder.setPlaceHolder(getResources().getDrawable(R.drawable.yujiazais));
//                                                            return null;
//                                                        }
//                                                    }) // 设置加载中显示的占位图
                                                        // .error(errorImage) // 设置加载失败的错误图
                                                        // .cache(Cache.ALL) // 缓存类型，默认为Cache.ALL（缓存图片和图片大小信息和文本样式信息）
                                                        // .imageGetter(yourImageGetter) // 设置图片加载器，默认为DefaultImageGetter，使用okhttp实现
                                                        // .imageDownloader(yourImageDownloader) // 设置DefaultImageGetter的图片下载器
                                                        .bind(WenZhangInfoActivity.this) // 绑定richText对象到某个object上，方便后面的清理
                                                        // .done(callback) // 解析完成回调
                                                        .into(contentTv); // 设置目标TextView
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
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



    @Override
    protected void onDestroy() {
        // activity onDestory时
        RichText.clear(this);
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
