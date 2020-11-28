package com.example.yiliaoyinian.ui.lumi.wangguan

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yiliaoyinian.R
import com.google.android.material.tabs.TabLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITab
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.qmuiteam.qmui.widget.tab.QMUITabView
import kotlinx.android.synthetic.main.activity_w_g_config.*


class WGConfigActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_w_g_config)

        val builder: QMUITabBuilder = tabs.tabBuilder()
        builder.setTypeface(null, Typeface.DEFAULT_BOLD)
        builder.setSelectedIconScale(1.2f)
                .setAllowIconDrawOutside(true)
                .setColor(Color.parseColor("#FFB6B6B6"),Color.parseColor("#FF59B683"))
                .setTextSize(QMUIDisplayHelper.sp2px(applicationContext, 13), QMUIDisplayHelper.sp2px(applicationContext, 15))
                .setDynamicChangeIconColor(false)

        tabs.addTab(builder.setText("我的设备").build(applicationContext))
        tabs.addTab(builder.setText("血压监测").build(applicationContext))
        tabs.addTab(builder.setText("我的设备").build(applicationContext))
        tabs.addTab(builder.setText("血压监测").build(applicationContext))

        tabs.notifyDataChanged()
        tabs.selectTab(0)


    }

}