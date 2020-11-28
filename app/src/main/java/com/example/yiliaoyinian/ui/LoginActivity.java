package com.example.yiliaoyinian.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.LoginBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.Beans.UserNameBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.DeleteInteface;

import com.example.yiliaoyinian.adapter.PopHeadBlackAdapterLogin;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, DeleteInteface, View.OnClickListener {



    EditText etUser;

    EditText etPass;

    ImageView cbEye;

    Button btnLogin;

    ImageView shezhi;
    private QMUITipDialog qmuiTipDialog = null;
    private String resage = null;
    private PopHeadBlackAdapterLogin adapterLogin;
    private  QMUIPopup popup;
    private List<UserNameBean> userNameBeanList=new ArrayList<>();

    //全局
    //  private IntentFilter intentFilter;
    // private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // @OnClick({R.id.cb_eye, R.id.btn_login,R.id.xiala})
        findViewById(R.id.cb_eye).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.xiala).setOnClickListener(this);
        etUser=findViewById(R.id.et_user);
        etPass=findViewById(R.id.et_pass);
        cbEye=findViewById(R.id.cb_eye);
        btnLogin=findViewById(R.id.btn_login);
        etUser=findViewById(R.id.et_user);


        methodRequiresTwoPermission();
        EventBus.getDefault().register(this);


        userNameBeanList.clear();
        userNameBeanList.addAll(MyApplication.myApplication.getUserNameBeanBox().getAll());
        adapterLogin = new PopHeadBlackAdapterLogin(userNameBeanList,this);

//      List<String> sssss=  DateUtils.getEveryday("2018-11-11","2020-11-11");
//      Log.d("LoginActivity", "sssss.size():" + sssss.size());
//        for (String s : sssss) {
//            Log.d("LoginActivity", s);
//        }

 }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.split(",").length == 2) {
            if (msgWarp.split(",")[0].equals("registrationId")) {
                resage = msgWarp.split(",")[1];
            }
        }

    }

//    public static final String KEY_APP_KEY = "JPUSH_APPKEY";
//
//    // 取得AppKey
//    public static String getAppKey(Context context) {
//        Bundle metaData = null;
//        String appKey = null;
//        try {
//            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
//                    context.getPackageName(), PackageManager.GET_META_DATA);
//            metaData = ai.metaData;
//            if (null != metaData) {
//                appKey = metaData.getString(KEY_APP_KEY);
//                if ((null == appKey) || appKey.length() != 24) {
//                    appKey = null;
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("MainActivity", e.getMessage()+"");
//        }
//        return appKey;
//    }

    private boolean isConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetworkInfo != null) {
                return wifiNetworkInfo.isConnected();
            }
        }
        return false;
    }


    private boolean isValidUrl(String url) {
        return !TextUtils.isEmpty(url) && url.matches(Patterns.WEB_URL.pattern());
    }

    private final int RC_CAMERA_AND_LOCATION = 10000;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK,
                Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经得到许可，就去做吧 //第一次授权成功也会走这个方法
            Log.d("LoginActivity", "成功获得权限");
            File file = new File(MyApplication.SDPATH1);
            Log.d("TAG", "methodRequiresTwoPermission: "+file.getAbsolutePath());
            if (!file.exists()) {
                Log.d("LoginActivity", "file.mkdirs():" + file.mkdirs());
            }
            File file2 = new File(MyApplication.SDPATH2);
            if (!file2.exists()) {
                Log.d("LoginActivity", "file.mkdirs():" + file2.mkdirs());
            }
            File file3 = new File(MyApplication.SDPATH3);
            if (!file3.exists()) {
                Log.d("LoginActivity", "file.mkdirs():" + file3.mkdirs());
            }
//            if (!isConnect()){
//                Log.d("LoginActivity", "没连接WIFI");
//                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                startActivity(intent);
//                return;
//            }

            Log.d("LoginActivity", JPushInterface.getRegistrationID(this) + "注册极光id");
            //登陆了
            if (MyApplication.myApplication.getToken() != null && !MyApplication.myApplication.getToken().equals("")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            JPushInterface.init(getApplicationContext());

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要授予app权限,请点击确定",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        Log.d("LoginActivity", "list.size():" + list.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        Log.d("LoginActivity", "list.size():" + list.size());
        for (String s : list) {
            Log.d("LoginActivity", "被拒绝的权限:"+s);
        }
        Toast.makeText(LoginActivity.this, "权限被拒绝无法正常使用app", Toast.LENGTH_LONG).show();

    }


    private boolean isA = false;




    public static boolean isPhoneNumber(String input) {// 判断手机号码是否规则
        String regex = "(1[0-9][0-9]|15[0-9]|18[0-9])\\d{8}";
        return Pattern.matches(regex, input);//如果不是号码，则返回false，是号码则返回true

    }


    private Call call;

    private void link_loging(String uname, String pwd) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
        JSONObject object = new JSONObject();
        try {
            object.put("phone", uname);
            object.put("password", pwd);
            object.put("deviceType", "1");
            if (resage != null) {
                object.put("deviceId", resage);
            } else if (JPushInterface.getRegistrationID(this) != null) {
                object.put("deviceId", JPushInterface.getRegistrationID(this));
            } else {
                object.put("deviceId", "");
            }
            object.put("deviceBrand", Build.BRAND);
            object.put("deviceModel", Build.MODEL);
            object.put("sysVersion", Build.VERSION.RELEASE);
            object.put("appBuild", Utils.getVersionName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(Consts.URL + "/api/nurse/login");

        // step 3：创建 Call 对象
        call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LoginActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                .setTipWord("网络请求失败")
                                .create();
                        qmuiTipDialog2.show();
                        btnLogin.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                qmuiTipDialog2.dismiss();
                            }
                        }, 2500);
                        if (qmuiTipDialog != null)
                            qmuiTipDialog.dismiss();
                        btnLogin.setEnabled(true);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (qmuiTipDialog != null)
                            qmuiTipDialog.dismiss();
                        btnLogin.setEnabled(true);
                    }
                });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LoginBean logingBe = gson.fromJson(jsonObject, LoginBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult() != null) {
                            SaveInfoBean saveInfoBean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                            saveInfoBean.setNurseName(logingBe.getResult().getNurseName());
                            saveInfoBean.setNurseCode(logingBe.getResult().getNurseCode());
                            saveInfoBean.setHeadImg(logingBe.getResult().getHeadImg());
                            saveInfoBean.setToken(logingBe.getResult().getToken());
                            saveInfoBean.setPhone(logingBe.getResult().getPhone());
                            if (saveInfoBean.getRegistrationId() == null) {
                                if (JPushInterface.getRegistrationID(LoginActivity.this) != null) {
                                    saveInfoBean.setRegistrationId(JPushInterface.getRegistrationID(LoginActivity.this));
                                } else if (resage != null) {
                                    saveInfoBean.setRegistrationId(resage);
                                }
                            }
                            boolean isA=false;
                            for (UserNameBean userNameBean : MyApplication.myApplication.getUserNameBeanBox().getAll()) {
                                if (userNameBean.getUserName().equals(uname)){
                                    isA=true;
                                    break;
                                }
                            }
                            if (!isA){//保存
                                UserNameBean userNameBean=new UserNameBean();
                                userNameBean.setUserName(uname);
                                MyApplication.myApplication.getUserNameBeanBox().put(userNameBean);
                            }
                            MyApplication.myApplication.getSaveInfoBeanBox().put(saveInfoBean);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LoginActivity.this)
                                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                            .setTipWord(jsonObject.get("errorMsg").getAsString())
                                            .create();
                                    qmuiTipDialog2.show();
                                    btnLogin.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            qmuiTipDialog2.dismiss();
                                        }
                                    }, 2500);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LoginActivity.this)
                                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                        .setTipWord(jsonObject.get("errorMsg").getAsString())
                                        .create();
                                qmuiTipDialog2.show();
                                btnLogin.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        qmuiTipDialog2.dismiss();
                                    }
                                }, 2500);
                            }
                        });

                    }

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(LoginActivity.this)
                                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                    .setTipWord("后台数据异常")
                                    .create();
                            qmuiTipDialog2.show();
                            btnLogin.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    qmuiTipDialog2.dismiss();
                                }
                            }, 2500);
                        }
                    });

                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }


//    class NetworkChangeReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isAvailable()) {
//                Toast.makeText(LoginActivity.this, "网络打开",Toast.LENGTH_SHORT).show();
////                Intent intent1 = new Intent("android.intent.action.MAIN");
////                intent1.setComponent(new ComponentName(getApplicationContext().getPackageName(), LoginActivity.class.getName()));
////                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                getApplicationContext().startActivity(intent1);
//            }
//            else {
//                Toast.makeText(LoginActivity.this, "网络关闭",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    @Override
    protected void onDestroy() {
        if (call != null)
            call.cancel();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }


//    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
//    @OnClick(R.id.shezhi)
//    public void onViewClicked() {
//        String URL="http://172.17.8.246:8082";
//       // String URL="http://ig7r8w.natappfree.cc";
//        SaveInfoBean saveInfoBean= MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
//        if (saveInfoBean.getUrl()!=null){
//            URL=saveInfoBean.getUrl();
//        }
//        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(LoginActivity.this);
//        builder.setTitle("设置后端地址")
//                .setSkinManager(QMUISkinManager.defaultInstance(LoginActivity.this))
////                .setPlaceholder(URL)
//                .setDefaultText(URL)
//                .setInputType(InputType.TYPE_CLASS_TEXT)
//                .addAction("取消", new QMUIDialogAction.ActionListener() {
//                    @Override
//                    public void onClick(QMUIDialog dialog, int index) {
//                        dialog.dismiss();
//                    }
//                })
//                .addAction("确定", new QMUIDialogAction.ActionListener() {
//                    @Override
//                    public void onClick(QMUIDialog dialog, int index) {
//                        CharSequence text = builder.getEditText().getText();
//                        if (text != null && text.length() > 0) {
//                            Toast.makeText(LoginActivity.this, "保存成功: " + text, Toast.LENGTH_SHORT).show();
//                            SaveInfoBean saveInfoBean= MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
//                            if (isValidUrl(text.toString())){
//                                saveInfoBean.setUrl(text.toString());
//                                MyApplication.myApplication.getSaveInfoBeanBox().put(saveInfoBean);
//                                Consts.URL=text.toString();
//                            }else {
//                                Toast.makeText(LoginActivity.this, "请填入正确的地址", Toast.LENGTH_SHORT).show();
//                            }
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "请填入地址", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .create(mCurrentDialogStyle).show();
//    }

    @Override
    public void deleteById(Long id) {
        MyApplication.myApplication.getUserNameBeanBox().remove(id);
        userNameBeanList.clear();
        userNameBeanList.addAll(MyApplication.myApplication.getUserNameBeanBox().getAll());
        adapterLogin.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_eye:
                if (!isA) {

                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isA = !isA;


                break;
            case R.id.btn_login:
                if (!isPhoneNumber(etUser.getText().toString().trim())) {
                    Toast tastyToast = TastyToast.makeText(this, "手机号码不正确", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                    return;
                }
                if (etPass.getText().toString().trim().equals("")) {
                    Toast tastyToast = TastyToast.makeText(this, "密码不能为空", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                    return;
                }
                btnLogin.setEnabled(false);
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("登录中...")
                        .create();
                qmuiTipDialog.show();
                link_loging(etUser.getText().toString().trim(), etPass.getText().toString().trim());

                break;
            case R.id.xiala:
                popup = QMUIPopups.listPopup(this, 540, 400, adapterLogin, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        etUser.setText(userNameBeanList.get(position).getUserName());
                        popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 4))
                        .shadow(true)
                        .arrow(false)
                        .bgColor(Color.parseColor("#ffffff"))
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(etUser);
                break;
        }
    }
}
