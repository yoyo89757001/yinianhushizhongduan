package com.example.yiliaoyinian.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.yiliaoyinian.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        int screenWidth = QMUIDisplayHelper.getScreenWidth(this);
        int screenHeight = QMUIDisplayHelper.getScreenHeight(this);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (screenWidth * 0.7);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height= (int) (screenHeight*0.5);
        // 这里还可以设置lp.x，lp.y在x轴，y轴上的坐标，只是这个位置是基于Gravity的
        window.setAttributes(lp);

    }
}