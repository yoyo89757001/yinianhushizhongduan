package com.example.yiliaoyinian.ui.lumi.wangguan


import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.example.yiliaoyinian.Beans.*
import com.example.yiliaoyinian.MyApplication
import com.example.yiliaoyinian.R
import com.example.yiliaoyinian.databinding.ActivityAddWG3Binding
import com.example.yiliaoyinian.utils.*
import com.example.yiliaoyinian.utils.wifiutils.BaseNetworkStatus
import com.example.yiliaoyinian.utils.wifiutils.NetType
import com.example.yiliaoyinian.utils.wifiutils.NetworkManager
import com.example.yiliaoyinian.utils.wifiutils.WifiUtils
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_add_w_g3.*
import lumisdk.CallBack
import lumisdk.Lumisdk
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer


class AddWGActivity3 : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityAddWG3Binding? = null
    private var isA = false
    private var qmuiTipDialog: QMUITipDialog? = null

    private var ssidAll:String = ""
    val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    var fh:Boolean=false
    val JSONTYPE: MediaType = "application/json".toMediaType()
    val APPID:String = "4113230e4535848642a52f0b"
    val APPKEY:String = "2HoQ1X1420pGAC6DYMzvVKqypV7t9W9p"
  //  var nsdManager: NsdManager? = null
/*    private val WIFICIPHER_NOPASS = 0
    private val WIFICIPHER_WEP = 1
    private val WIFICIPHER_WPA = 2*/
    private var mWifiManager: WifiManager? = null
    private var connWifiManager:ConnectivityManager?=null
    // var networkId:Int=0
   // private var esptouchTask: EsptouchTask? = null
    var timer: Timer? = null
    var timesNumber:Int = 0
    var assignSSID:String="lumi-gateway-"
    var bindKey:String = ""
    var did:String = "lumi1.54ef44ca1f79"
    var didList:ArrayList<String> = ArrayList<String>()
    var ssid :String? = ""
    var ip :String? = ""
    var mac :String? = ""
    var rssi :String? = ""
    var password :String? = ""
    var model:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWG3Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        EventBus.getDefault().register(this@AddWGActivity3)
        NetworkManager.INSTANCE.init(this);
        mWifiManager = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
        connWifiManager = getApplicationContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        ssid =  mWifiManager?.connectionInfo?.ssid
        var ipip:Int?=0
        ipip = mWifiManager?.connectionInfo?.ipAddress
        if (ipip == null){
            ipip = 0
        }
        ip = intToIp(ipip)
        mac =  mWifiManager?.connectionInfo?.macAddress
        rssi =  mWifiManager?.connectionInfo?.rssi.toString()

        binding!!.fanhui.setOnClickListener(this)
        binding!!.xiayibu.setOnClickListener(this)
        binding!!.bottomLl.setOnClickListener(this)
        binding!!.xiayibu.setBackgroundColor(Color.parseColor("#40333333"))
        binding!!.xiayibu.radius = QMUIDisplayHelper.dp2px(this, 25)
        binding!!.xiayibu.setChangeAlphaWhenPress(true) //点击改变透明度

        //println("极光id ${MyApplication.myApplication.saveInfoBeanBox.get(123456).registrationId}");

        loginApi3();


    }

    fun intToIp(ipInt: Int): String? {
        val sb = java.lang.StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fanhui -> {
                fh = true
                finish()
            }
            R.id.bottom_ll -> if (isA) {
                isA = false
                binding!!.imbt.setBackgroundResource(R.drawable.wxuanzhong)
                binding!!.xiayibu.setBackgroundColor(Color.parseColor("#40333333"))
                binding!!.xiayibu.radius = QMUIDisplayHelper.dp2px(this, 25)
                binding!!.xiayibu.setChangeAlphaWhenPress(true) //点击改变透明度
            } else {
                isA = true
                binding!!.imbt.setBackgroundResource(R.drawable.yxuanzhong)
                binding!!.xiayibu.setBackgroundColor(Color.parseColor("#FF59B683"))
                binding!!.xiayibu.radius = QMUIDisplayHelper.dp2px(this, 25)
                binding!!.xiayibu.setChangeAlphaWhenPress(true) //点击改变透明度
            }
            R.id.xiayibu -> if (isA) {
                if (xiayibu.text.equals("下一步")) {
                    if (bindKey.equals("")) {
                        qmuiTipDialog = QMUITipDialog.Builder(this@AddWGActivity3)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                .setTipWord("获取bindKey失败,请检查网络")
                                .create()
                        qmuiTipDialog?.show()
                        object : Thread() {
                            override fun run() {
                                sleep(1500L)
                                runOnUiThread {
                                    qmuiTipDialog?.dismiss()
                                }
                            }
                        }.start()
                        return
                    }
                    qmuiTipDialog = QMUITipDialog.Builder(this@AddWGActivity3)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("配网中,请稍后...")
                            .create()
                    qmuiTipDialog?.show()

                    object : Thread() {
                        override fun run() {
                            val wifilist = mWifiManager?.scanResults
                            wifilist?.forEach { res ->
                                println("MainActivity ${res.SSID}")
                                if (res.SSID != null && res.SSID.contains(assignSSID)) {
                                    assignSSID = res.SSID
                                    println("MainActivity ${assignSSID}")
                                    return@forEach
                                }
                            }
                            WifiUtils.getInstance(this@AddWGActivity3).connectWifiNoPws(assignSSID)
                            startTimer()
                        }
                    }.start()
                } else {
                    EventBus.getDefault().post("dghadsajhdbasj") //关闭
                    finish()
                }
            }

        }
    }
    private fun startTimer() {
        timer = fixedRateTimer("", false, 0, 5000) {
            Log.d("MainActivity", "检查是不是连接到了指定的网络。。。" + ssidAll)
            timesNumber++
            if (ssidAll.contains(assignSSID)){
                stopTimer()
            }else{
                if (fh){
                    timer?.cancel()
                    finish()
                    println("结束")
                }
                if (timesNumber>=3){
                    timesNumber=0
                    Log.d("MainActivity", "重新连接")
                    val wifilist =  mWifiManager?.scanResults
                    wifilist?.forEach { res->
                        println("MainActivity ${res.SSID}")
                        if (res.SSID!=null && res.SSID.contains(assignSSID)){
                            assignSSID = res.SSID
                            println("MainActivity ${assignSSID}")
                            return@forEach
                        }
                    }
                    WifiUtils.getInstance(this@AddWGActivity3).connectWifiNoPws(assignSSID)
                }
            }
        }
    }


    private fun stopTimer() {
        timer?.cancel()
        Log.d("MainActivity", "切换网络成功")
        var deviceInfo:String = "";
        val  isBind2WifiChannel:Boolean = false;
        val deviceIP:String = Lumisdk.justGetTargetDeviceIPAddress();
        if (TextUtils.isEmpty(deviceIP)) {
            Log.e("error", "get device IP failed");
            return;
        }

        var DEVICE_ID:String=" "
        try {
            DEVICE_ID = GetDeviceId.getDeviceId(this@AddWGActivity3)
            Log.d("AAAbindKey", "解密前:$bindKey")
            bindKey = AESUtil.decryptCbc(bindKey, AESUtil.getAESKey(APPKEY))
            val jj= JSON.parseObject(bindKey)
            bindKey = jj.getString("bindKey")
            Log.d("AAAbindKey", "解密后: " + bindKey)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        val inetAddress: InetAddress = InetAddress.getByName(deviceIP);
        Log.d("AAA", inetAddress.hostAddress)
        Log.d("AAA", inetAddress.address.toString())
        Log.d("AAA", inetAddress.hostName)
        val para: JSONObject =  JSONObject()
        para.put("ssid", "KYJ")
        para.put("passwd", "kyj@2020")
        para.put("positionId", bindKey)
        para.put("uid", bindKey)
        para.put("cid", DEVICE_ID)
        para.put("lang", "zh")
        para.put("country_domain", "aiot-coap.aqara.cn")
        para.put("bindKey", bindKey)
        para.put("timeZone", Lumisdk.getTimeZone())
        val wifiInfo:ByteArray = para.toString().toByteArray()
        val encryptInfo: ByteArray = Lumisdk.getEncryptedData(wifiInfo)
        Log.d("sendMsg2Gateway:", para.toString())
        val mDatagramSocket: DatagramSocket =  DatagramSocket()
        mDatagramSocket.setSoTimeout(6000);//5秒接收超时
        try {
            val datagramPacket: DatagramPacket =  DatagramPacket(
                    encryptInfo,
                    encryptInfo.size,
                    inetAddress,
                    10008
            );
//            if (null != network) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    network.bindSocket(mDatagramSocket);
//                } else {
//                    isBind2WifiChannel = ConnectivityManager.setProcessDefaultNetwork(network);
//                }
//            }
            val revInfo:ByteArray =  ByteArray(1024 * 100)
            val revPacket: DatagramPacket = DatagramPacket(revInfo, revInfo.size);
            mDatagramSocket.send(datagramPacket);
            mDatagramSocket.receive(revPacket);
            val revData:String =  String(
                    Lumisdk.getDecryptedDataWithLen(
                            revPacket.getData(),
                            revPacket.length.toLong()
                    )
            );
            deviceInfo = revData;
            Log.d("dddd", deviceInfo + " ")
            did = JSON.parseObject(revData).getString("did")
            model = JSON.parseObject(revData).getString("model")
//            val save = MyApplication.myApplication.saveInfoBeanBox
//            val sinfo = save.get(123456);
//            sinfo.did = did
//            save.put(sinfo)
            //MyApplication.myApplication.wgInfoSaveBox.removeAll()

              val wgInfoSave:Box<WGInfoSave>  =  MyApplication.myApplication.wgInfoSaveBox
                val vv : WGInfoSave? = wgInfoSave.query().equal(WGInfoSave_.did, did).build().findFirst()
                if (vv==null){//true, did, "FFFFFF", 50, model, "网关", 50, "",ssid,mac,ip,rssi,"房间"
                    wgInfoSave.put(WGInfoSave(1, "", 1, did, model, "", "网关", true, "ffffff", 50, 60, ssid, mac, ip, rssi, "房间"))
                   // EventBus.getDefault().post("link_did_zi")
                }
            WifiUtils.getInstance(this@AddWGActivity3).connectWifiNoPws2(ssid)
            //loginApi(did)
            //SystemClock.sleep(2000)

        } catch (timeoutException: SocketTimeoutException) {
            //接收超时
            if (deviceInfo.isEmpty()) {
                Log.d("ddddd", "接收超时")
            }
        } catch (e: Exception) {
            Log.d("ddddd", "接收超时" + e.message)
        } finally {
            mDatagramSocket.close();
            if (isBind2WifiChannel) {
                ConnectivityManager.setProcessDefaultNetwork(null);
            }
        }
    }

    fun loginApi(did: String, model: String){
//        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
//        params.set("did",did)
//        params.set("model",model)
//        params.set("pinlessUser",MyApplication.myApplication.saveInfoBeanBox.get(123456).phone)
//        params.set("auroraId",MyApplication.myApplication.saveInfoBeanBox.get(123456).registrationId)
////        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")
        //查看设备信息
        val url = Consts.URL2 + "/app/lvmi/save"
        val json =  JSONObject()
        json.put("did", did)
        json.put("model", model)
        json.put("pinlessUser", MyApplication.myApplication.saveInfoBeanBox.get(123456).phone)
        json.put("auroraId", MyApplication.myApplication.saveInfoBeanBox.get(123456).registrationId)

        //  val requestBody: RequestBody = RequestBody.create(params,JSONTYPE)
//     //   println("ssssddfe ${json.toString()}")
//        val time:String=System.currentTimeMillis().toString()
//        val header: HashMap<String, String> = HashMap(4)
//
//        header[CommonRequest.REQUEST_HEADER_APPID] = APPID
//        header[CommonRequest.REQUEST_HEADER_LANG] = "zh"
//        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
//       // val body:String = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim()
//       // header[CommonRequest.REQUEST_HEADER_PAYLOAD] = body.trim()
//        header[CommonRequest.REQUEST_HEADER_TIME] = time
//
//    //    println("body加密前 ${json.toString()}  key长度 ${AESUtil.getAESKey(APPKEY).size} ")
//     //   println("解密body:${AESUtil.decryptCbc(body, AESUtil.getAESKey(APPKEY))}")
//
//        val sign:String = FilterContext.createSign(header, APPKEY, false)
       // println("body：$body");  println("sign: $sign")
        val builder = Request.Builder()
                  .addHeader("token", MyApplication.myApplication.getToken())
//                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
//                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
//                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
//                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                .post(json.toString().toRequestBody(JSONTYPE)).url(url).build()
             //.get().url(url).build()

        client.newCall(builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了${call.request().url}")
                runOnUiThread {
                    qmuiTipDialog?.dismiss()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   上传给后台返回结果:$stA")
                    val ssddd = JSON.parseObject(stA).getIntValue("code")


//                    for (resultDTO in healthBean.result) {
//                        println("————收到固件版本 ${resultDTO.firmwareVersion}")
//                        val wgInfoSave:Box<WGInfoSave>  =  MyApplication.myApplication.wgInfoSaveBox
//                        val vv : WGInfoSave? = wgInfoSave.query().equal(WGInfoSave_.did, did).build().findFirst()
//                        if (vv!=null){
//                           vv.firmwareVersion = resultDTO.firmwareVersion
//                            wgInfoSave.put(vv)
//                        }
//                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        })

    }

    fun loginApiquer(did: String){
//        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
//        params.set("client_id","4113230e4535848642a52f0b")
//        params.set("response_type","code")
//        params.set("state","yinian")
//        params.set("account","13642730363")
//        params.set("password","ABC123456")
//        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")

        //开启网关添加子设备模式
        val url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/child/query?did=$did"
        // val json = JSONObject()
        //  json.put("did", did)
        //  val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
        val time:String=System.currentTimeMillis().toString()
        val header: HashMap<String, String> = HashMap(4)

        header[CommonRequest.REQUEST_HEADER_APPID] = APPID
        header[CommonRequest.REQUEST_HEADER_LANG] = "zh"
        header[CommonRequest.REQUEST_HEADER_TIME] = time

        val sign:String = FilterContext.createSign(header, APPKEY, false)

        val builder = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                // .post(body.toRequestBody()).url(url).build()
                .get().url(url).build()

        client.newCall(builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了$e")
            }

            override fun onResponse(call: Call, response: Response) {
                val bu: StringBuilder = StringBuilder()
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   \n查询网关子设备返回结果:$stA")
                    val healthBean: Auto7 = JSON.parseObject(stA, Auto7::class.java)
                    val jsonss: String = AESUtil.decryptCbc(healthBean.result, AESUtil.getAESKey(APPKEY))
                    println("jsonss:$jsonss")
                    val json: JSONArray = JSON.parseArray(jsonss)
                    runOnUiThread() {
                        didList.clear()
                        // 回到主线程刷新UI吧
                        println(json.size)
                        for (result in json) {
                            val obj = JSON.parseObject(result.toString())
                            val did: String = obj.getString("did")
                            bu.append(did)
                            bu.append("\n")
                            didList.add(did)
                        }
                        // fff.setText(bu.toString())
                    }
                } catch (e: Exception) {
                    println(e.message)
                } finally {
                    runOnUiThread() {
                        // fff.setText(bu.toString())
                    }
                }
            }
        })
    }

    fun loginApiunBind(did: String){
//        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
//        params.set("client_id","4113230e4535848642a52f0b")
//        params.set("response_type","code")
//        params.set("state","yinian")
//        params.set("account","13642730363")
//        params.set("password","ABC123456")
//        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")

        //开启网关添加子设备模式
        val url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/unbind"
        val json = JSONObject()
        json.put("did", did)
        //json.put("installCode", "")
        val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
        val time:String=System.currentTimeMillis().toString()
        val header: HashMap<String, String> = HashMap(4)

        header[CommonRequest.REQUEST_HEADER_APPID] = APPID
        header[CommonRequest.REQUEST_HEADER_LANG] = "zh"
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        val body:String = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim()
        header[CommonRequest.REQUEST_HEADER_PAYLOAD] = body.trim()
        header[CommonRequest.REQUEST_HEADER_TIME] = time

        println("body加密前 ${json.toString()}  key长度 ${AESUtil.getAESKey(APPKEY).size} ")
        println("解密body:${AESUtil.decryptCbc(body, AESUtil.getAESKey(APPKEY))}")

        val sign:String = FilterContext.createSign(header, APPKEY, false)
        println("body：$body");  println("sign: $sign")
        val builder = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                .post(body.toRequestBody()).url(url).build()
        //.get().url(url).build()

        client.newCall(builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了$e")
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   解除绑定子设备返回结果:$stA")
                    //  val healthBean: Auto6 = JSON.parseObject(stA, Auto6::class.java)
                    runOnUiThread() {
                        // 回到主线程刷新UI吧

                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        })

    }


    fun loginApi2(code: String, redirect_uri: String){
        val url = "https://aiot-oauth2.aqara.cn/access_token"
        val requestBody: FormBody = FormBody.Builder()
                .add("client_id", APPID)
                .add("client_secret", APPKEY)
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("redirect_uri", redirect_uri)
                .build()

        val builder = Request.Builder()
        builder.url(url)
        builder.addHeader("Content-Type", "application/json")
                .post(requestBody)
        //.get()

        client.newCall(builder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了$e")

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   返回结果:$stA")
                    val healthBean: Auto3 = JSON.parseObject(stA, Auto3::class.java)
                    println(healthBean.openId)
                    //  Lumisdk.aiotAuth(APPID,APPKEY,healthBean.openId,this@MainActivity)
                    runOnUiThread() {
                        // 回到主线程刷新UI吧

                    }
                } catch (ee: Exception) {

                }


            }

        })

    }


    fun loginApi3(){
        val timeLong:String = System.currentTimeMillis().toString()
        val msign:String = "Appid=${MyApplication.APPID}&Lang=en&Time=$timeLong".toLowerCase(Locale.ROOT)
        val sign:String = "$msign&${MyApplication.APPKEY}"
        val signl:String = MD5Util.MD5(sign)
        println(signl)
        val url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/bind/get"
        val builder = Request.Builder()
        builder.url(url)
        builder
                .addHeader("Appid", APPID)
                .addHeader("Sign", signl)
                .addHeader("Lang", "en")
                .addHeader("Time", timeLong)
                // .post(requestBody)
                .get()

        client.newCall(builder.build()).enqueue(object : Callback, CallBack {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了${call.request().url} ${e.message}")
                //  WifiUtils.getInstance(this@MainActivity).connectWifiNoPws(assignSSID)
                // startTimer()
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   获取bindKey返回结果:$stA")
                    val healthBean: Auto4 = JSON.parseObject(stA, Auto4::class.java)
                    bindKey = healthBean.result
                    println("bindKey: $bindKey")
                    if (healthBean.result.equals("")) {
                        runOnUiThread() {
                            Toast.makeText(this@AddWGActivity3, healthBean.message, Toast.LENGTH_LONG)
                                    .show()
                        }
                        return
                    }
                    runOnUiThread() {
                        // 回到主线程刷新UI吧
//                        println(ssid+"ssidsss")
//                        val json = JSONObject()
//                        json.put("ssid", ssid)
//                        json.put("passwd", "kyj@2020")
//                        json.put("country_domain", "aiot-rpc.aqara.cn")
//                        json.put("bindKey", "0bKjUFJFwh8I9wzWzCWSf34KKCkstiG9X64QPvCKfVg=")
//                        println("json参数:"+json.toString())
                        //   Lumisdk.gatewayFastLink(true,json.toString(),this)

                    }
                } catch (ee: Exception) {
                    println(ee)
                }
            }

            override fun onSuccess(response: String?) {
                println("response:" + response)
            }

            override fun onFaied(code: Long, errorInfo: String?) {
                println("code:" + code.toString())
                println("errorInfo:" + errorInfo)

            }

        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun wxMSG5(msgWarp: TSWGBean) {
        Log.d("MainActivity", "收到的JSONObject：" + msgWarp.toString())
        try {
            if (msgWarp.code==0){
                xiayibu.setText("完成")
                qmuiTipDialog?.dismiss()
            }else{
                qmuiTipDialog?.dismiss()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
 /*   override fun onSuccess(response: String?) {
        println("response:" + response)
    }

    override fun onFaied(code: Long, errorInfo: String?) {
        println("responsecode:" + code.toString())
        println("responseerrorInfo:" + errorInfo)
    }*/

    val callback = object : BaseNetworkStatus() {
        override fun isConnect(available: Boolean) {
            runOnUiThread {
               // println("1")
                setTVContentViewValue(available)
            }
        }

        override fun getNetworkType(type: NetType) {
            runOnUiThread {
               // println("2")
                setTVNetTypeValue(type)
            }
        }

    }

//    val callback1 = object : NetworkConnectCallback() {
//        override fun isConnect(available: Boolean) {
//            runOnUiThread {
//                println("3")
//                setTVContentViewValue(available)
//            }
//        }
//
//    }

//    val callback2 = object : NetworkTypeCallback() {
//        override fun getNetworkType(type: NetType) {
//            runOnUiThread {
//                println("4")
//                setTVNetTypeValue(type)
//            }
//        }
//    }

    override fun onBackPressed() {
        println("按了返回键")
        fh = true
        super.onBackPressed()
    }

    override fun onResume() {
        NetworkManager.INSTANCE.registerNetWorkStatusCallback(callback)
       // NetworkManager.INSTANCE.registerNetWorkStatusCallback(callback1)
     //   NetworkManager.INSTANCE.registerNetWorkStatusCallback(callback2)
        super.onResume()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        NetworkManager.INSTANCE.unregisterNetWorkStatusCallback(callback)
       // NetworkManager.INSTANCE.unregisterNetWorkStatusCallback(callback1)
      //  NetworkManager.INSTANCE.unregisterNetWorkStatusCallback(callback2)
        super.onStop()

    }

    override fun onDestroy() {
        timer?.cancel()
        NetworkManager.INSTANCE.destory()
        EventBus.getDefault().unregister(this@AddWGActivity3)
        super.onDestroy()
        println("结束掉了添加网关页面")
    }

    private fun setTVNetTypeValue(type: NetType) {
        println("当前网络状态：${type.name}")

    }

    private fun setTVContentViewValue(available: Boolean) {
        println("网络状态：$available, ${mWifiManager?.connectionInfo?.ssid.toString()}")
        if (available){
            ssidAll= mWifiManager?.connectionInfo?.ssid.toString()
        }
        if (available){
            if (ssid==mWifiManager?.connectionInfo?.ssid.toString() && (did != "" && model != "") ){
                  println("二次连接成功")
                 // fanhui.setBackgroundColor(Color.parseColor("#333333"))
                object :Thread(){
                    override fun run() {
                        var isA = 0;
                        while (true){
                            isA+=1
                            println("是否连接: ${connWifiManager!!.activeNetworkInfo.detailedState}")
                            SystemClock.sleep(3000)
                            loginApi(did, model)
                            return
//                            if (connWifiManager!!.activeNetworkInfo.detailedState==NetworkInfo.DetailedState.CONNECTED){
//                            }
//                            SystemClock.sleep(300)
//                            if (isA>40){
//                                return
//                            }
                        }
                    }
                }.start()

            }
        }

    }

}