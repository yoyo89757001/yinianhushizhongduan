//package com.example.yiliaoyinian.ui.shuju.camera;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.SystemClock;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.example.yiliaoyinian.Beans.SaveInfoBean;
//import com.example.yiliaoyinian.MyApplication;
//import com.example.yiliaoyinian.R;
//import com.example.yiliaoyinian.dialog.CommomDialog;
//import com.example.yiliaoyinian.ui.BaseActivity;
//import com.example.yiliaoyinian.ui.LoginActivity;
//import com.example.yiliaoyinian.ui.xunfang.XunFangJiLuActivity;
//import com.example.yiliaoyinian.ui.zhiban.ZhiBanActivity;
//import com.example.yiliaoyinian.utils.EZUtils;
//import com.example.yiliaoyinian.utils.ToastUtils;
//import com.example.yiliaoyinian.views.MultiVideoLayout;
//import com.example.yiliaoyinian.views.PTZView;
//import com.github.ybq.android.spinkit.SpinKitView;
//import com.qmuiteam.qmui.util.QMUIDisplayHelper;
//import com.qmuiteam.qmui.widget.popup.QMUIPopup;
//import com.qmuiteam.qmui.widget.popup.QMUIPopups;
//import com.videogo.errorlayer.ErrorInfo;
//import com.videogo.exception.BaseException;
//import com.videogo.openapi.EZConstants;
//import com.videogo.openapi.EZOpenSDK;
//import com.videogo.openapi.EZPlayer;
//import com.videogo.openapi.EzvizAPI;
//import com.videogo.openapi.bean.EZCameraInfo;
//import com.videogo.openapi.bean.EZDeviceInfo;
//import com.videogo.realplay.RealPlayStatus;
//import com.videogo.openapi.EZConstants.EZVideoLevel;
//import java.math.BigDecimal;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static com.videogo.openapi.EZConstants.MSG_GOT_STREAM_TYPE;
//import static com.videogo.openapi.EZConstants.MSG_VIDEO_SIZE_CHANGED;
//
//public class CameraPlayActivity extends BaseActivity implements SurfaceHolder.Callback,Handler.Callback {
//
//    @BindView(R.id.fanhui)
//    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.statusBar)
//    RelativeLayout statusBar;
//    @BindView(R.id.ly_video)
//    FrameLayout lyVideo;
//    @BindView(R.id.ib_play)
//    ImageButton ibPlay;
//    @BindView(R.id.ib_pause)
//    ImageButton ibPause;
//    @BindView(R.id.tv_text)
//    TextView tvText;
//    @BindView(R.id.sk_waiting)
//    SpinKitView skWaiting;
//    @BindView(R.id.btn_quality)
//    Button btnQuality;
//    @BindView(R.id.ib_sound)
//    ImageButton ibSound;
//    @BindView(R.id.ib_voice)
//    ImageButton ibVoice;
//    @BindView(R.id.ib_multi)
//    ImageButton ibMulti;
//    @BindView(R.id.ib_full)
//    ImageButton ibFull;
//    @BindView(R.id.ly_action)
//    LinearLayout lyAction;
//    @BindView(R.id.ly_bedPanelFragment)
//    FrameLayout lyBedPanelFragment;
//    @BindView(R.id.ly_connecting)
//    FrameLayout lyConnecting;
//    @BindView(R.id.tv_mask)
//    TextView tvMask;
//    @BindView(R.id.view_ptz)
//    PTZView ptzView;
//    @BindView(R.id.ly_ptz)
//    RelativeLayout lyPtz;
//    @BindView(R.id.surfaceview)
//    SurfaceView surfaceview;
//    private EZDeviceInfo deviceInfo = null;//只显示序列号为参数的设备
//    private EZCameraInfo cameraInfo = null;
//    private String deviceMac = null;//序列号
//    private String validateCode = null;//验证码
//    private EZPlayer mEZPlayer = null;
//    private SurfaceHolder mRealPlaySh = null;
//    private Handler mHandler = null;
//    private int mStatus = RealPlayStatus.STATUS_INIT;
//    private boolean A=true,B=false,send=false;
//    private int ptzO=0;
//    private EZVideoLevel mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_BALANCED;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera_play);
//        ButterKnife.bind(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        deviceMac = getIntent().getStringExtra("deviceMac");
//        validateCode = getIntent().getStringExtra("validateCode");
//        if (!EzvizAPI.getInstance().isLogin()) {
//            ToastUtils.setFAIL("登录摄像头失败,请退出后重试", fanhui);
//            return;
//        }
//
//        ibSound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CameraPlayActivity", "点击");
//                send=!send;
//                ibSound.setSelected(send);
//                if (send){
//                    if (mEZPlayer!=null){
//                        mEZPlayer.openSound();
//                    }
//                }else {
//                    if (mEZPlayer!=null){
//                        mEZPlayer.closeSound();
//                    }
//                }
//
//            }
//        });
//
//
//        mRealPlaySh = surfaceview.getHolder();
//        mRealPlaySh.addCallback(this);
//        mHandler = new Handler(this);
//
//        try {
//            Log.d("CameraPlayActivity", validateCode+"序列号"+deviceMac);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        deviceInfo = EZOpenSDK.getInstance().getDeviceInfo(deviceMac);
//                        do {
//                            SystemClock.sleep(100);
//                            Log.d("CameraPlayActivity", "循环1"+deviceInfo+"  "+A);
//                        } while (deviceInfo == null && A);
//                        cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, 0);
//                        do {
//                            SystemClock.sleep(100);
//                            Log.d("CameraPlayActivity", "循环2");
//                        } while (!B);
//                        Message message=Message.obtain();
//                        message.what=111;
//                        mHandler.sendMessage(message);
//                        Log.d("CameraPlayActivity", "循环结束发送");
//                        //获取单通道设备摄像头信息
//
//                    } catch (BaseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//            //  Log.d("MainActivity", deviceInfo.getDeviceName()+"设备信息");
////            List<EZDeviceInfo> result = MyApplication.getOpenSDK().getDeviceList(0, 10);
////            for (EZDeviceInfo ezDeviceInfo : result) {
////                Log.d("MainActivity", ezDeviceInfo.getDeviceName()+"设备信息2");
////                Log.d("MainActivity", "ezDeviceInfo.getCameraNum():" + ezDeviceInfo.getCameraNum());//cameraNum==1 是单通道设备
////                if (ezDeviceInfo.getCameraInfoList() != null)
////                    Log.d("MainActivity", "ezDeviceInfo.getCameraInfoList().size():" + ezDeviceInfo.getCameraInfoList().size());//size==1是单通道设备
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        ptzView.setOnChangedListener(new PTZView.OnChangedListener() {
//            @Override
//            public void onCursorChanged(@NonNull PTZView view, int oldCursor, int newCursor) {
//                Log.d("CameraPlayActivity", "aaaaaaoldCursor:" + oldCursor);
//                Log.d("CameraPlayActivity", "aaaaaanewCursor:" + newCursor);
//                //ptzO=oldCursor;//1是左 2是上 3是右，4是下
//                switch (newCursor){
//                    case 1:
//                        ptzO=2;
//                        break;
//                    case 2:
//                        ptzO=0;
//                        break;
//                    case 3:
//                        ptzO=3;
//                        break;
//                    case 4:
//                        ptzO=1;
//                        break;
//
//                }
//                //   ptzCursorChanged(view, oldCursor, newCursor);
//            }
//
//            @Override
//            public void onSpeedScaleChanged(@NonNull PTZView view, float oldSpeedScale, float newSpeedScale) {
//              //  Log.d("CameraPlayActivity", "oldSpeedScale:" + oldSpeedScale);
//               // Log.d("CameraPlayActivity", "newSpeedScale:" + newSpeedScale);
//                BigDecimal bgOld = new BigDecimal(oldSpeedScale + "");
//                BigDecimal bgNew = new BigDecimal(newSpeedScale + "");
//                Log.d("CameraPlayActivity", "bg:" + bgOld);
//                Log.d("CameraPlayActivity", "bgNew:" + bgNew);
//                double f1 = bgOld.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                double f2 = bgNew.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                Log.d("CameraPlayActivity", "f1:" + f1);
//                Log.d("CameraPlayActivity", "f2:" + f2);
//                if ((f1==1 && f2==0) || (f1==0 && f2==1) || f2==0){
//                    if (cameraInfo!=null){
//                        try {
//                            switch (ptzO){
//                                case 0:
//                                    Log.d("CameraPlayActivity", "进000000");
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (f2==1){
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTART,1);
//                                                }else {
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTOP,0);
//                                                }
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }).start();
//
//                                    break;
//                                case 1:
//
//                                    Log.d("CameraPlayActivity", "进111111");
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (f2==1){
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTART,1);
//                                                }else {
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTOP,0);
//                                                }
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }).start();
//
//                                    break;
//                                case 2:
//
//                                    Log.d("CameraPlayActivity", "进222222");
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (f2==1){
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTART,1);
//                                                }else {
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTOP,0);
//                                                }
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }).start();
//
//                                    break;
//                                case 3:
//
//                                    Log.d("CameraPlayActivity", "进333333");
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (f2==1){
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTART,1);
//                                                }else {
//                                                    MyApplication.getOpenSDK().controlPTZ(deviceMac,cameraInfo.getCameraNo(), EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTOP,0);
//                                                }
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }).start();
//
//                                    break;
//
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//
//    }
//
//
//
//
//
//    @OnClick({R.id.ib_voice, R.id.btn_quality,R.id.ib_multi})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ib_voice://对讲
//
//                break;
//            case R.id.ib_multi://全屏
//
//                break;
//            case R.id.btn_quality://视频质量切换
//                showMenuPop(btnQuality);
//                break;
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        mRealPlaySh = holder;
//        B=true;
////        if (mEZPlayer != null) {
////            Log.d("CameraPlayActivity", "进来1");
////            if (mStatus == RealPlayStatus.STATUS_INIT) {
////                // 开始播放
////                Log.d("CameraPlayActivity", "进来2");
////                mEZPlayer.setSurfaceHold(mRealPlaySh);
////                mEZPlayer.setPlayVerifyCode(deviceMac);//验证码
////                mEZPlayer.startRealPlay();
////            }
////        }
//
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        if (mEZPlayer != null) {
//            mEZPlayer.setSurfaceHold(holder);
//        }
//        mRealPlaySh = holder;
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (mEZPlayer != null) {
//            mEZPlayer.setSurfaceHold(null);
//        }
//        mRealPlaySh = null;
//    }
//
//    private void setVideoLevel() {
//        if (cameraInfo == null || mEZPlayer == null || deviceInfo == null) {
//            return;
//        }
//        if (deviceInfo.getStatus() == 1) {
//            btnQuality.setEnabled(true);
//        } else {
//            btnQuality.setEnabled(false);
//        }
//        /**************
//         * 本地数据保存 需要更新之前获取到的设备列表信息，开发者自己设置
//         *
//         * Local data saved need to be updated before the obtained device list information, the developer's own settings
//         * *********************/
//        mEZPlayer.stopRealPlay();
//        cameraInfo.setVideoLevel(mCurrentQulityMode.getVideoLevel());
//        mEZPlayer.startRealPlay();
//
//        /**
//         *
//         * 视频质量，2-高清，1-标清，0-流畅
//         * Video quality, 2-HD, 1-standard, 0- smooth
//         *
//         */
//        if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
//            btnQuality.setText("流畅");
//        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
//            btnQuality.setText("标清");
//        } else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
//            btnQuality.setText("高清");
//        }else if (mCurrentQulityMode.getVideoLevel() == EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR.getVideoLevel()){
//            btnQuality.setText("超清");
//        }else{
//            btnQuality.setText("流畅");
//        }
//    }
//
//    @Override
//    public boolean handleMessage(@NonNull Message msg) {
//        if (this.isFinishing()) {
//            return false;
//        }
//       Log.d("CameraPlayActivity", "msg.what:" + msg.what);
//        switch (msg.what) {
//            case 111:
//                setVideoLevel();
//                if (cameraInfo != null && deviceInfo != null &&  deviceInfo.getStatus() != 1) {
//                    tvText.setVisibility(View.VISIBLE);
//                    skWaiting.setVisibility(View.GONE);
//                    ToastUtils.setFAIL("摄像头不在线",fanhui);
//                }else {
//                    if (cameraInfo!=null){
//                        mEZPlayer = EZOpenSDK.getInstance().createPlayer(cameraInfo.getDeviceSerial(), cameraInfo.getCameraNo());
//                        mEZPlayer.setPlayVerifyCode(validateCode);
//                        mEZPlayer.setHandler(mHandler);
//                        mEZPlayer.setSurfaceHold(mRealPlaySh);
//                        //声音开关
//                       // mEZPlayer.openSound();
//                        mEZPlayer.closeSound();
//                        mEZPlayer.startRealPlay();
//
//                        Log.d("CameraPlayActivity", "mEZPlayer.getPlayPort():信息" + mEZPlayer.getOSDTime());
//                    }
//                }
//
//                break;
//            case MSG_VIDEO_SIZE_CHANGED:
//                try {
//                    String temp = (String) msg.obj;
//                    String[] strings = temp.split(":");
//                  //  mVideoWidth = Integer.parseInt(strings[0]);
//                 //   mVideoHeight = Integer.parseInt(strings[1]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_GET_CAMERA_INFO_SUCCESS:
//               // updateLoadingProgress(20);
//               // handleGetCameraInfoSuccess();
//                Log.d("CameraPlayActivity", "获取相机信息成功");
//                // 开始播放
//
//                mEZPlayer.setPlayVerifyCode(deviceMac);//验证码
//                mEZPlayer.startRealPlay();
//
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_START:
//               // updateLoadingProgress(40);
//                Log.d("CameraPlayActivity", "开始");
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_CONNECTION_START:
//                Log.d("CameraPlayActivity", "开始连接");
//               // updateLoadingProgress(60);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_CONNECTION_SUCCESS:
//               // updateLoadingProgress(80);
//                Log.d("CameraPlayActivity", "连接成功");
//
//                mEZPlayer.setPlayVerifyCode(deviceMac);//验证码
//                mEZPlayer.startRealPlay();
//
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
//                skWaiting.setVisibility(View.GONE);
////                ViewGroup playInfoVg = (ViewGroup) findViewById(R.id.vg_play_info);
////                if (playInfoVg != null){
////                    playInfoVg.setVisibility(View.VISIBLE);
////                }
//               // showDecodeType();
//               // handlePlaySuccess(msg);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
//                skWaiting.setVisibility(View.GONE);
//              //  handlePlayFail(msg.obj);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS:
//              //  handleSetVedioModeSuccess();
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL:
//              //  handleSetVedioModeFail(msg.arg1);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_PTZ_SET_FAIL:
//               // handlePtzControlFail(msg);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_SUCCESS:
//               // handleVoiceTalkSucceed();
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_STOP:
//              //  handleVoiceTalkStoped(false);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_FAIL:
//                ErrorInfo errorInfo = (ErrorInfo) msg.obj;
//               // handleVoiceTalkFailed(errorInfo);
//                break;
//            case MSG_GOT_STREAM_TYPE:
//              //  showStreamType(msg.arg1);
//                break;
//            default:
//                // do nothing
//                break;
//        }
//        return false;
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        A=false;
//        B=true;
//        super.onDestroy();
//    }
//
//
//    private QMUIPopup mPopupWindow = null;
//    private void showMenuPop(View view2) {
//        if (isFinishing())
//            return;
//        View view = getLayoutInflater().inflate(R.layout.pop_head_name2, null, false);
//        LinearLayout l1 = view.findViewById(R.id.l1);
//        l1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_FLUNET;
//                setVideoLevel();
//                if (mPopupWindow != null)
//                    mPopupWindow.dismiss();
//            }
//        });
//        LinearLayout l2 = view.findViewById(R.id.l2);
//        l2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_BALANCED;
//                setVideoLevel();
//                if (mPopupWindow != null)
//                    mPopupWindow.dismiss();
//            }
//        });
//        LinearLayout l3 = view.findViewById(R.id.l3);
//        l3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_HD;
//                setVideoLevel();
//                if (mPopupWindow != null)
//                    mPopupWindow.dismiss();
//            }
//        });
//        LinearLayout l4 = view.findViewById(R.id.l4);
//        l4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentQulityMode = EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR;
//                setVideoLevel();
//                if (mPopupWindow != null)
//                    mPopupWindow.dismiss();
//            }
//        });
//        if (mPopupWindow == null) {
//            mPopupWindow = QMUIPopups.popup(CameraPlayActivity.this,
//                    QMUIDisplayHelper.dp2px(CameraPlayActivity.this, 90),
//                    QMUIDisplayHelper.dp2px(CameraPlayActivity.this, 176))
//                    .view(view)
//                    .edgeProtection(QMUIDisplayHelper.dp2px(CameraPlayActivity.this, 6))
//                    .shadow(true)
//                    .arrow(false)
//                    .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
//        }
//        mPopupWindow.show(view2);
//    }
//}
