package com.example.yiliaoyinian.utils.wifiutils

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import com.example.yiliaoyinian.utils.wifiutils.Constants.NET_WORK_CONNECTION_STATUS_AND_NET_TYPE_CODE
import com.example.yiliaoyinian.utils.wifiutils.Constants.NET_WORK_IS_CONNECTION
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class NetworkService : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    private lateinit var networkReceiver: NetworkReceiver

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        networkReceiver = NetworkReceiver()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    /**
     * 接收网络状态事件
     * @param event 网络状态事件对象
     */
    @Subscribe
    fun onEventMainThread(event: NetWorkEvent) {
        if (event.what == NET_WORK_CONNECTION_STATUS_AND_NET_TYPE_CODE) {
            val isAvailable = event.data.getBoolean(NET_WORK_IS_CONNECTION)
            NetworkManager.INSTANCE.putIsConnection(isAvailable)
            if(isAvailable){
                val netType = event.obj as NetType
                NetworkManager.INSTANCE.putNetworkType(netType)
            }
        }
    }
}
