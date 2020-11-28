package com.example.yiliaoyinian.ui.shuju;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartStackingType;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartZoomType;
import com.example.yiliaoyinian.Beans.AllBleThreadBean;
import com.example.yiliaoyinian.Beans.BleAllDataListBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.DateUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LanYaFragment2 extends Fragment {

    AAChartView chart;

    private AAChartModel aaChartModel=null;
    private List<AllBleThreadBean> allBleThreadBeans=new ArrayList<>();


    public LanYaFragment2() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wwexMSGqsu44(List<BleAllDataListBean.ResultBean> msgWarp){
        try {
            if (msgWarp!=null && msgWarp.size()>0 && msgWarp.get(0).getType()==1002){
                allBleThreadBeans.clear();
                for (BleAllDataListBean.ResultBean resultBean : msgWarp) {
                    AllBleThreadBean threadBean=new AllBleThreadBean();
                    threadBean.setCreateTime(DateUtils.tim(resultBean.getCreateTime()+""));
                    threadBean.setDiastolic(resultBean.getDiastolic());
                    threadBean.setHeartrateM(resultBean.getHeartrateM());
                    threadBean.setSystolic(resultBean.getSystolic());
                    allBleThreadBeans.add(threadBean);
                }
                int size=allBleThreadBeans.size();
                String[] time=new String[size];
                for (int i = 0; i < size; i++) {
                    time[i]=allBleThreadBeans.get(i).getCreateTime();
                }

                Object[] xuel=new Object[size];
                Object[] xue2=new Object[size];
                Object[] xue3=new Object[size];
                for (int i = 0; i < size; i++) {
                    xuel[i]=allBleThreadBeans.get(i).getSystolic();
                    xue2[i]=allBleThreadBeans.get(i).getDiastolic();
                    xue3[i]=allBleThreadBeans.get(i).getHeartrateM();
                }
                aaChartModel = new AAChartModel()
                        .chartType(AAChartType.Spline)
                        .title("")
                        .subtitle("")
                        .tooltipEnabled(true)
                        .yAxisTitle("")
                        .markerRadius(2f)
                        .yAxisVisible(true)
                        .yAxisLabelsEnabled(true)
                        .yAxisLineWidth(1f)
                        .yAxisGridLineWidth(1f)
                        .tooltipCrosshairs(false)
                        .zoomType(AAChartZoomType.X)
                        .stacking(AAChartStackingType.Normal) //堆积样式
                        //.xAxisGridLineWidth(1f)
                        //.stacking() //堆积样式
                        .markerSymbol("circle")////折线曲线连接点的类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                        .backgroundColor("#FFFFFF")
                        .axesTextColor("#080808")
                        .yAxisVisible(true)
                        .categories(time)
                        .dataLabelsEnabled(true)
                        .series(new AASeriesElement[]{
                                new AASeriesElement()
                                        .color("#5AD8A6")
                                        .name("收缩压")
                                        .data(xuel),
                                new AASeriesElement()
                                        .color("#FFD9A7")
                                        .name("舒张压")
                                        .data(xue2),
                                new AASeriesElement()
                                        .color("#5CBAEA")
                                        .name("心率")
                                        .data(xue3)
                        });

                chart.aa_drawChartWithChartModel(aaChartModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wwexMSGqsu44(String msgWarp){
        if (msgWarp.equals("setFocusableInTouchModely")){
            Log.d("LanYaFragment2", "顶顶顶");
            chart.setFocusable(false);
            chart.setFocusableInTouchMode(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lan_ya2, container, false);
        chart=view.findViewById(R.id.chart);

        EventBus.getDefault().register(this);


        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }



}
