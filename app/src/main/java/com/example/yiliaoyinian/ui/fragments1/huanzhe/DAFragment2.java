package com.example.yiliaoyinian.ui.fragments1.huanzhe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



/**
 * A simple {@link Fragment} subclass.
 */
public class DAFragment2 extends Fragment {

    TextView jiehu;
    TextView jieshao;



    public DAFragment2() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(HuanZheBean.ResultBean msgWarp) {
       jiehu.setText( msgWarp.getNurseLevel().getNurseLevelName());
       jieshao.setText( msgWarp.getNurseLevel().getItemsList());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_a2, container, false);
        jiehu=view.findViewById(R.id.jiehu);
        jieshao=view.findViewById(R.id.jieshao);

        EventBus.getDefault().register(this);


        return view;
    }

    @Override
    public void onDestroyView() {

        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }


}
