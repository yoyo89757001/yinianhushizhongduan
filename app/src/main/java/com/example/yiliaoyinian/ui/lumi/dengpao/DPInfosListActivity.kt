package com.example.yiliaoyinian.ui.lumi.dengpao

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.example.yiliaoyinian.Beans.Auth01
import com.example.yiliaoyinian.Beans.Auto7
import com.example.yiliaoyinian.Beans.WGInfoSave
import com.example.yiliaoyinian.Beans.WGInfoSave_
import com.example.yiliaoyinian.MyApplication
import com.example.yiliaoyinian.R
import com.example.yiliaoyinian.dialog.CommomDialog
import com.example.yiliaoyinian.ui.lumi.wangguan.GengDuocActivity
import com.example.yiliaoyinian.ui.lumi.wangguan.WangLuosActivity
import com.example.yiliaoyinian.ui.lumi.zidonghua.ZDHActivity1
import com.example.yiliaoyinian.utils.AESUtil
import com.example.yiliaoyinian.utils.CommonRequest
import com.example.yiliaoyinian.utils.Consts
import com.example.yiliaoyinian.utils.FilterContext
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.EditTextDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_w_g_infos_list.*
import kotlinx.android.synthetic.main.activity_w_g_infos_list.dds
import kotlinx.android.synthetic.main.activity_w_g_infos_list.fanhui
import kotlinx.android.synthetic.main.activity_w_g_infos_list.myTitle
import kotlinx.android.synthetic.main.activity_w_g_infos_list.rl1
import kotlinx.android.synthetic.main.activity_w_g_infos_list.rl3
import kotlinx.android.synthetic.main.activity_w_g_infos_list.rl4
import kotlinx.android.synthetic.main.activity_w_g_infos_list.rl5
import kotlinx.android.synthetic.main.activity_w_g_infos_list.rl9
import kotlinx.android.synthetic.main.activity_w_g_infos_list.sjk3
import kotlinx.android.synthetic.main.activity_w_g_infos_list.sjk310
import kotlinx.android.synthetic.main.activity_w_g_infos_list.sjk311
import kotlinx.android.synthetic.main.activity_w_g_infos_list.sjk4
import kotlinx.android.synthetic.main.activity_w_g_infos_list.yichu
import kotlinx.android.synthetic.main.activity_w_g_infos_list22.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit



class DPInfosListActivity : AppCompatActivity() {
    private val mCurrentDialogStyle = R.style.QMUI_Dialog
    var boxWG : Box<WGInfoSave>? = null
    var save:WGInfoSave? = null
    var did:String? = ""
    var znum:Int? = 0
    var qmdialog:QMUITipDialog? = null

    val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_w_g_infos_list22)
        boxWG=MyApplication.myApplication.wgInfoSaveBox
        did=intent.getStringExtra("did")
        znum=intent.getIntExtra("zunm", 0)
        if (did!=null){
            try {
                save=boxWG?.query()?.equal(WGInfoSave_.did, did!!)?.build()?.findFirst()
                println( "ddddddddddd ${save!!.parentDid}")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        initview()
        if (save!=null){
            try {
                myTitle.setText(save!!.name)
                dds.setText(save!!.name)
                sjk3.setText(save!!.weizhi)
                sjk310.setText(save!!.did.substring(6, did!!.length))
                sjk311.setText(save!!.firmwareVersion)
                sjk3.setText(save!!.weizhi)
                sjk4.setText(save!!.parentDid.substring(6, save!!.parentDid.length))
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        if (did==null){
            return
        }

        //loginApi(did!!)

    }

    fun initview(){
        fanhui.setOnClickListener {
            finish()
        }
        rl1.setOnClickListener {
            val builder = EditTextDialogBuilder(this@DPInfosListActivity)
            builder.setTitle("修改设备名称")
                    .setSkinManager(QMUISkinManager.defaultInstance(this@DPInfosListActivity))
                    .setPlaceholder("请输入设备名称")
                    .setInputType(InputType.TYPE_CLASS_TEXT)
                    .addAction("取消") { dialog, index -> dialog.dismiss() }
                    .addAction("确定") { dialog, index ->
                        val text: CharSequence? = builder.editText.text
                        if (text != null && text.length > 0) {
                            dds.setText(text)
                            save?.name = text.toString()
                            if (save!=null){
                                boxWG?.put(save!!)
                            }
                            linkUpdate(text.toString(),"")
                            println(text)
                        }
                        dialog.dismiss()
                    }
                    .create(mCurrentDialogStyle).show()
        }
        rl3.setOnClickListener {
            val builder = EditTextDialogBuilder(this@DPInfosListActivity)
            builder.setTitle("修改设备位置")
                    .setSkinManager(QMUISkinManager.defaultInstance(this@DPInfosListActivity))
                    .setPlaceholder("请输入设备位置")
                    .setInputType(InputType.TYPE_CLASS_TEXT)
                    .addAction("取消") { dialog, index -> dialog.dismiss() }
                    .addAction("确定") { dialog, index ->
                        val text: CharSequence? = builder.editText.text
                        if (text != null && text.length > 0) {
                            sjk3.setText(text)
                            save?.weizhi = text.toString()
                            if (save!=null){
                                boxWG?.put(save!!)
                            }
                            linkUpdate("",text.toString())
                            println(text)
                        }
                        dialog.dismiss()
                    }
                    .create(mCurrentDialogStyle).show()
        }
        rl4.setOnClickListener {
            startActivity(Intent(this@DPInfosListActivity, GengDuocActivity::class.java).putExtra("did", did))
        }
        rl5.setOnClickListener {
            startActivity(Intent(this@DPInfosListActivity, ZDHActivity1::class.java).putExtra("did", did))
        }
        rl9.setOnClickListener {
            startActivity(Intent(this@DPInfosListActivity, WangLuosActivity::class.java).putExtra("did", did))
        }
        yichu.setOnClickListener {
            val commomDialog = CommomDialog(this@DPInfosListActivity, R.style.dialogs, "确定要移除设备?") { dialog, confirm ->
                Log.d("DAFragment3", "confirm:$confirm")
                if (confirm) {
                    //退出动作
                    if (did!=null){
                        qmdialog = QMUITipDialog.Builder(this@DPInfosListActivity).setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)?.setTipWord("正在加载")?.create()
                        qmdialog?.show()
                        loginApiunBind(did!!)
                    }
                    dialog.dismiss()
                }
            }.setTitle("确认").setPositiveButton("确定")
            commomDialog.show()
        }
    }




    fun linkUpdate(name:String,place:String){
        val url = Consts.URL2 + "/app/lvmi/save"

        val json =  JSONObject()
        json.put("did", did)
        if (name != ""){
            json.put("name", name)
        }
        if (place != ""){
            json.put("place", place)
        }
        val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)

        val builder = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("token", MyApplication.myApplication.getToken())
                .post(requestBody).url(url).build()


        client.newCall(builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了$e")
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   修改设备返回结果:$stA")
                    val healthBean = JSON.parseObject(stA, Auto7::class.java)


                } catch (e: Exception) {
                    println(e.message)
                }
            }
        })
    }

    val JSONTYPE: MediaType = "application/json".toMediaType()
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

        header[CommonRequest.REQUEST_HEADER_APPID] = MyApplication.APPID
        header[CommonRequest.REQUEST_HEADER_LANG] = "zh"
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        val body:String = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim()
        header[CommonRequest.REQUEST_HEADER_PAYLOAD] = body.trim()
        header[CommonRequest.REQUEST_HEADER_TIME] = time

        val sign:String = FilterContext.createSign(header, MyApplication.APPKEY, false)
        println("body：$body");  println("sign: $sign")
        val builder = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                .post(body.toRequestBody()).url(url).build()
        //.get().url(url).build()

        client.newCall(builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("————失败了$e")
                runOnUiThread {
                    qmdialog?.dismiss()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    runOnUiThread() {
                        qmdialog?.dismiss()
                    }
                    val stA = response.body!!.string()
                    println("请求地址:${call.request().url}   解除绑定子设备返回结果:$stA")
                    val healthBean: Auto7 = JSON.parseObject(stA, Auto7::class.java)
                    if (healthBean.code==0){
                    val sass: WGInfoSave? = MyApplication.myApplication.wgInfoSaveBox.query().equal(WGInfoSave_.did,did).build().findFirst()
                        if (sass!=null){
                            MyApplication.myApplication.wgInfoSaveBox.remove(sass)
                        }
                        EventBus.getDefault().post("fsdggdgfdgeeefdg")
                        finish()
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        })

    }

}