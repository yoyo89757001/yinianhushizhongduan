package com.example.yiliaoyinian;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDex;
import com.clj.fastble.BleManager;
import com.example.yiliaoyinian.Beans.MyObjectBox;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.Beans.SphygmomanometerDataBean;
import com.example.yiliaoyinian.Beans.UserNameBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.utils.UnCeHandler;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;
import com.zzhoujay.richtext.RichText;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import cn.jpush.android.api.JPushInterface;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import me.jessyan.autosize.AutoSizeConfig;
import okhttp3.OkHttpClient;



/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application {

    ArrayList<Activity> list = new ArrayList<Activity>();
    public static MyApplication myApplication;
    private Box<UserNameBean> userNameBeanBox=null;
    private Box<SaveInfoBean> saveInfoBeanBox=null;

    private Box<WGInfoSave> wgInfoSaveBox=null;

    public static String APPID = "4113230e4535848642a52f0b";
    public static String APPKEY = "2HoQ1X1420pGAC6DYMzvVKqypV7t9W9p";

    public static  String SDPATH1 = null;
    public static  String SDPATH2 = null;
    public static  String SDPATH3 = null;
    private Box<SphygmomanometerDataBean> sphygmomanometerDataBox = null;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            //.cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();


//    public static EZOpenSDK getOpenSDK(){
//        return EZOpenSDK.getInstance();
//    }



    public void init(){
        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this,this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public String getToken(){
        if (saveInfoBeanBox.get(123456).getToken()==null){
            return  "";
        }
        else {return saveInfoBeanBox.get(123456).getToken();}
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        BoxStore mBoxStore = MyObjectBox.builder().androidContext(this).build();
        Bugly.init(this, "577f4cf317", false);
        BleManager.getInstance().init(this);

        saveInfoBeanBox= mBoxStore.boxFor(SaveInfoBean.class);
        userNameBeanBox= mBoxStore.boxFor(UserNameBean.class);
        sphygmomanometerDataBox= mBoxStore.boxFor(SphygmomanometerDataBean.class);

        wgInfoSaveBox= mBoxStore.boxFor(WGInfoSave.class);

        //设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(true);

        JPushInterface.init(this);
        SDPATH1 = getExternalFilesDir(null)+ File.separator+"yinian1";
        SDPATH2 = getExternalFilesDir(null)+File.separator+"yinian2";
        SDPATH3 = getExternalFilesDir(null)+File.separator+"yinian3";


        SaveInfoBean  baoCunBean = mBoxStore.boxFor(SaveInfoBean.class).get(123456L);
        if (baoCunBean == null) {
            baoCunBean = new SaveInfoBean();
            baoCunBean.setId(123456L);
            baoCunBean.setSereveURL("http://21n2c53681.iask.in:9001");
            mBoxStore.boxFor(SaveInfoBean.class).put(baoCunBean);
        }
        
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " x5內核初始化完成的回调" + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

        RichText.initCacheDir(this);
        // Application中配置(设置宽适配)
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setBaseOnWidth(true);

        //init();
    }


    public Box<SaveInfoBean> getSaveInfoBeanBox(){
        return saveInfoBeanBox;
    }
    public Box<SphygmomanometerDataBean> getSphygmomanometerDataBox(){
        return sphygmomanometerDataBox;
    }
    public Box<UserNameBean> getUserNameBeanBox(){
        return userNameBeanBox;
    }

    public Box<WGInfoSave> getWgInfoSaveBox(){
        return wgInfoSaveBox;
    }

}
