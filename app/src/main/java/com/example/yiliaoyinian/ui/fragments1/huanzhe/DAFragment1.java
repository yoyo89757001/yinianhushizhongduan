package com.example.yiliaoyinian.ui.fragments1.huanzhe;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartStackingType;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.yiliaoyinian.AAChartCoreLib.AAChartEnum.AAChartZoomType;
import com.example.yiliaoyinian.Beans.ChartChildrenBean;
import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.Beans.MeasurementAllBean;
import com.example.yiliaoyinian.Beans.ServiceTypeBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.HealthDataAdapter;
import com.example.yiliaoyinian.adapter.PopHeadAdapter;
import com.example.yiliaoyinian.utils.DateUtils;
//import com.example.yiliaoyinian.utils.TimeAxisValueFormatter;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DAFragment1 extends Fragment implements View.OnClickListener {
    ImageView quanbu;

    ImageView yichang;

    QMUIButton bt1;

    QMUIButton bt2;

    QMUIButton bt3;

    QMUIButton bt4;

    QMUIButton bt5;
    TextView timeTvJiankang;
    LinearLayout time_ll;
    AAChartView chart;

//    @BindView(R.id.chart2)
//    LineChart chart2;
//    @BindView(R.id.chart3)
//    LineChart chart3;
//    @BindView(R.id.chart4)
//    LineChart chart4;
//    @BindView(R.id.chart5)
//    LineChart chart5;

    private List<ServiceTypeBean> xiangmuList = new ArrayList<>();
    private PopHeadAdapter popHeadAdapterLX = null;
    private QMUIPopup popup = null;
    private RecyclerView recyclerView;
    private HealthDataAdapter adapter;//健康数据适配器
    private List<MeasurementAllBean> healthDataBeanList = new ArrayList<>();
    private List<HuanZheBean.ResultBean.MeasureDataListBean> measureDataListBeanList=new ArrayList<>();
    private List<HuanZheBean.ResultBean.AbnormalListBean> abnormalListBeanArrayList=new ArrayList<>();
    private List<ChartChildrenBean> xueYa1=new ArrayList<>();
    private List<ChartChildrenBean> xueYa2=new ArrayList<>();
    private List<ChartChildrenBean> xueTang=new ArrayList<>();
    private List<ChartChildrenBean> tiWen=new ArrayList<>();
    private List<ChartChildrenBean> maiBo=new ArrayList<>();
    private List<ChartChildrenBean> xinLv=new ArrayList<>();
    private  AAChartModel aaChartModel=null;

    public DAFragment1() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(HuanZheBean.ResultBean msgWarp){//正常数据
      //  Log.d("DAFragment1", "msgWarp1:" + msgWarp);
        if (msgWarp.getAbnormalList()!=null && msgWarp.getAbnormalList().size()>0){
            abnormalListBeanArrayList.addAll(msgWarp.getAbnormalList());
            for (HuanZheBean.ResultBean.AbnormalListBean bean : abnormalListBeanArrayList) {
                MeasurementAllBean allBean=new MeasurementAllBean();
                allBean.setId(bean.getId());
                allBean.setMeasureData(bean.getMeasureData());
                allBean.setMeasuredType(bean.getDataType());
                allBean.setMeasureTime(bean.getMeasureTime());
                allBean.setType(2);//1是正常2是异常
                healthDataBeanList.add(allBean);
            }
        }

        if (msgWarp.getMeasureDataList()!=null && msgWarp.getMeasureDataList().size()>0){
            measureDataListBeanList.addAll(msgWarp.getMeasureDataList());
        }
        adapter.notifyDataSetChanged();
        //处理图表数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (HuanZheBean.ResultBean.MeasureDataListBean dataListBean : measureDataListBeanList) {
                   switch (dataListBean.getDataType()){//1-血氧，2-血压，3-血糖，4-体温，5-脉搏，6-呼吸，7-心率
                       case 1:

                           break;
                       case 2:
                           ChartChildrenBean childrenBean1=new ChartChildrenBean();
                           childrenBean1.setData(dataListBean.getSystolic());//大值
                           childrenBean1.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           xueYa1.add(childrenBean1);

                           ChartChildrenBean childrenBean2=new ChartChildrenBean();
                           childrenBean2.setData(dataListBean.getDiastolic());//小值
                           childrenBean2.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           xueYa2.add(childrenBean2);

                           ChartChildrenBean childrenBean3=new ChartChildrenBean();
                           childrenBean3.setData(dataListBean.getHeartrateM());//心率
                           childrenBean3.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           xinLv.add(childrenBean3);

                           break;
                       case 3:
                           ChartChildrenBean childrenBean4=new ChartChildrenBean();
                           childrenBean4.setData(dataListBean.getGlucose());//心率
                           childrenBean4.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           xueTang.add(childrenBean4);


                       break;
                       case 4:
                           ChartChildrenBean childrenBean5=new ChartChildrenBean();
                           childrenBean5.setData(dataListBean.getTemperature());//心率
                           childrenBean5.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           tiWen.add(childrenBean5);
                           break;
                       case 5:
                           ChartChildrenBean childrenBean6=new ChartChildrenBean();
                           childrenBean6.setData(dataListBean.getHeartrateM());//心率
                           childrenBean6.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           maiBo.add(childrenBean6);
                           break;
                       case 6:

                           break;
                       case 7:
                           ChartChildrenBean childrenBean8=new ChartChildrenBean();
                           childrenBean8.setData(dataListBean.getHeartrateM());//心率
                           childrenBean8.setTime(DateUtils.ti(dataListBean.getMeasureTime()+""));
                           xinLv.add(childrenBean8);
                           break;
                   }
                }

//                public String  animationType;         //动画类型
//                public Integer animationDuration;     //动画时间
//                public String  title;                 //标题内容
//                public String  subtitle;              //副标题内容
//                public String  chartType;             //图表类型
//                public String  stacking;              //堆积样式
//                public String  symbol;                //折线曲线连接点的类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
//                public String  symbolStyle;
//                public String  zoomType;              //缩放类型 AAChartZoomTypeX表示可沿着 x 轴进行手势缩放
//                public Boolean pointHollow;           //折线或者曲线的连接点是否为空心的
//                public Boolean inverted;              //x 轴是否翻转(垂直)
//                public Boolean xAxisReversed;         //x 轴翻转
//                public Boolean yAxisReversed;         //y 轴翻转
//                public Boolean tooltipEnabled;        //是否显示浮动提示框(默认显示)
//                public String  tooltipValueSuffix;    //浮动提示框单位后缀
//                public Boolean tooltipCrosshairs;     //是否显示准星线(默认显示)
//                public Boolean gradientColorEnable;   //是否要为渐变色
//                public Boolean polar;                 //是否极化图形(变为雷达图)
//                public Float   marginLeft;
//                public Float   marginRight;
//                public Boolean dataLabelEnabled;      //是否显示数据
//                public Boolean xAxisLabelsEnabled;    //x轴是否显示数据
//                public String[]categories;            //x轴是否显示数据
//                public Integer xAxisGridLineWidth;    //x轴网格线的宽度
//                public Boolean xAxisVisible;          //x 轴是否显示
//                public Boolean yAxisVisible;          //y 轴是否显示
//                public Boolean yAxisLabelsEnabled;    //y轴是否显示数据
//                public String  yAxisTitle;            //y轴标题
//                public Float   yAxisLineWidth;        //y 轴轴线的宽度
//                public Integer yAxisGridLineWidth;    //y轴网格线的宽度
//                public Object[]colorsTheme;           //图表主题颜色数组
//                public Boolean legendEnabled;         //是否显示图例
//                public String  legendLayout;          //图例数据项的布局。布局类型： "horizontal" 或 "vertical" 即水平布局和垂直布局 默认是：horizontal.
//                public String  legendAlign;           //设定图例在图表区中的水平对齐方式，合法值有left，center 和 right。
//                public String  legendVerticalAlign;   //设定图例在图表区中的垂直对齐方式，合法值有 top，middle 和 bottom。垂直位置可以通过 y 选项做进一步设定。
//                public String  backgroundColor;       //图表背景色
//                public Boolean options3dEnable;       //是否3D化图形(仅对条形图,柱状图有效)
//                public Integer options3dAlphaInt;
//                public Integer options3dBetaInt;
//                public Integer options3dDepth;        //3D图形深度
//                public Integer borderRadius;          //柱状图长条图头部圆角半径(可用于设置头部的形状,仅对条形图,柱状图有效)
//                public Integer markerRadius;          //折线连接点的半径长度
//                public AASeriesElement[] series;
//                public String  titleColor;            //标题颜色
//                public String  subTitleColor;         //副标题颜色
//                public String  axisColor;             //x 轴和 y 轴文字颜色


                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //数据配置完了
                                if (xueYa2.size() <= 0) {
                                    setChartNoData(chart);
                                }else {
//                                    ArrayList<ILineDataSet> sets = new ArrayList<>();//多条数据
//                                    List<Entry> yVals1=new ArrayList<>();
//                                    for (int i = 0; i < xueYa1.size(); i++) {
//                                        yVals1.add(new Entry(i,xueYa1.get(i).getData()));
//                                    }
//                                    List<Entry> yVals2=new ArrayList<>();
//                                    for (int i = 0; i < xueYa2.size(); i++) {
//                                        yVals2.add(new Entry(i,xueYa2.get(i).getData()));
//                                    }

                                    String[] yl=new String[xueYa1.size()];
                                    for (int i = 0; i < xueYa1.size(); i++) {
                                        yl[i]=xueYa1.get(i).getTime();
                                    }
                                    Object[] xuel=new Object[xueYa1.size()];
                                    for (int i = 0; i < xueYa1.size(); i++) {
                                        xuel[i]=xueYa1.get(i).getData();
                                    }
                                    Object[] xue2=new Object[xueYa2.size()];
                                    for (int i = 0; i < xueYa2.size(); i++) {
                                        xue2[i]=xueYa2.get(i).getData();
                                    }
                                    ///*仅仅更新了图表的series数组数据,不改动图表的其他内容*/
                                    //    aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)
                                    // aaChartView.aa_refreshChartWholeContentWithChartModel(aaChartModel)
                                    // 更新 AAChartModel 整体内容(如修改了图表的类型,将 column chart 改为 area chart)之后,刷新图表
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
                                             .zoomType(AAChartZoomType.X)
                                            .tooltipCrosshairs(false)
                                            //.xAxisGridLineWidth(1f)
                                            .stacking(AAChartStackingType.Normal) //堆积样式
                                            .markerSymbol("circle")////折线曲线连接点的类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                                            .backgroundColor("#FFFFFF")
                                            .axesTextColor("#080808")
                                            .yAxisVisible(true)
                                            .categories(yl)
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
                                            });

                                    chart.aa_drawChartWithChartModel(aaChartModel);

//                                    Log.d("DAFragment1", Arrays.toString(yl));
//                                    TimeAxisValueFormatter xAxisFormatter = new TimeAxisValueFormatter(yl);
//                                    XAxis xAxis= chart.getXAxis();
//                                    xAxis.setValueFormatter(xAxisFormatter);
//                                    xAxis.setLabelCount(xueYa2.size(),true);
//
//                                    LineDataSet ds1 = new LineDataSet(yVals1, "收缩压");
//                                    LineDataSet ds2 = new LineDataSet(yVals2, "舒张压");
//
//                                    ds1.setColor(Color.parseColor("#FF00FF"));
//                                    ds1.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//
//                                    ds2.setColor(Color.parseColor("#4169E1"));
//                                    ds2.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//
//                                    sets.add(ds1);
//                                    sets.add(ds2);
//                                    ds1.notifyDataSetChanged();
//                                    ds2.notifyDataSetChanged();
//                                    LineData da = new LineData(sets);
//                                    chart.setData(da);//设置数据
//                                    chart.notifyDataSetChanged();
                                   // chart.invalidate(); // refresh
                                }

/////////////////////////////////////////////////////////////////////
//                                if (xueTang.size() <= 0) {
//                                    setChartNoData(chart2);
//                                }else {
//                                   // ArrayList<ILineDataSet> sets2 = new ArrayList<>();//多条数据
//                                    List<Entry> yVals1 = new ArrayList<>();
//                                    for (int i = 0; i < xueTang.size(); i++) {
//                                        yVals1.add(new Entry(i, xueTang.get(i).getData()));
//                                    }
//                                    String[] yl2 = new String[xueTang.size()];
//                                    for (int i = 0; i < xueTang.size(); i++) {
//                                        yl2[i] = xueTang.get(i).getTime();
//                                    }
//                                    Log.d("DAFragment1", Arrays.toString(yl2));
//                                    TimeAxisValueFormatter xAxisFormatter2 = new TimeAxisValueFormatter(yl2);
//                                    XAxis xAxis2 = chart2.getXAxis();
//                                    xAxis2.setValueFormatter(xAxisFormatter2);
//                                    xAxis2.setLabelCount(xueTang.size(),false);
//
//                                    LineDataSet ds22 = new LineDataSet(yVals1, "血糖");
//
//                                    ds22.setColor(Color.parseColor("#FF00FF"));
//                                    ds22.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//
//                                   // sets2.add(ds22);
//                                    ds22.notifyDataSetChanged();
//                                    LineData da2 = new LineData(ds22);
//                                    chart2.setData(da2);//设置数据
//                                    chart2.notifyDataSetChanged(); // refresh
//                                }
//
////////////////////////////////////////////////////////////////////////
//                                if (tiWen.size() <= 0) {
//                                    setChartNoData(chart3);
//                                }else {
//                                  //  ArrayList<ILineDataSet> sets3 = new ArrayList<>();//多条数据
//                                    List<Entry> yVals13 = new ArrayList<>();
//                                    for (int i = 0; i < tiWen.size(); i++) {
//                                        yVals13.add(new Entry(i, tiWen.get(i).getData()));
//                                    }
//                                    String[] yl3 = new String[tiWen.size()];
//                                    for (int i = 0; i < tiWen.size(); i++) {
//                                        yl3[i] = tiWen.get(i).getTime();
//                                    }
//                                    Log.d("DAFragment1", Arrays.toString(yl3));
//                                    TimeAxisValueFormatter xAxisFormatter3 = new TimeAxisValueFormatter(yl3);
//                                    XAxis xAxis3 = chart3.getXAxis();
//                                    xAxis3.setValueFormatter(xAxisFormatter3);
//                                    xAxis3.setLabelCount(tiWen.size(),false);
//
//                                    LineDataSet ds13 = new LineDataSet(yVals13, "体温");
//
//                                    ds13.setColor(Color.parseColor("#FF00FF"));
//                                    ds13.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//
//                                  //  sets3.add(ds13);
//                                    ds13.notifyDataSetChanged();
//                                    LineData da3 = new LineData(ds13);
//                                    chart3.setData(da3);//设置数据
//                                    chart3.notifyDataSetChanged(); // refresh
//                                }
//
//////////////////////////////////////////////////////////////////////////////
//                                if (maiBo.size() <= 0) {
//                                    setChartNoData(chart4);
//                                }else {
//                                  //  ArrayList<ILineDataSet> sets4 = new ArrayList<>();//多条数据
//                                    List<Entry> yVals14 = new ArrayList<>();
//                                    for (int i = 0; i < maiBo.size(); i++) {
//                                        yVals14.add(new Entry(i, maiBo.get(i).getData()));
//                                    }
//                                    String[] yl4 = new String[maiBo.size()];
//                                    for (int i = 0; i < maiBo.size(); i++) {
//                                        yl4[i] = maiBo.get(i).getTime();
//                                    }
//
//                                    TimeAxisValueFormatter xAxisFormatter4 = new TimeAxisValueFormatter(yl4);
//                                    XAxis xAxis4 = chart4.getXAxis();
//                                    xAxis4.setValueFormatter(xAxisFormatter4);
//                                    xAxis4.setLabelCount(maiBo.size(), false);
//
//                                    LineDataSet ds14 = new LineDataSet(yVals14, "脉搏");
//
//                                    ds14.setColor(Color.parseColor("#FF00FF"));
//                                    ds14.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//
//                                   // sets4.add(ds14);
//                                    ds14.notifyDataSetChanged();
//                                    LineData da4 = new LineData(ds14);
//                                    chart4.setData(da4);//设置数据
//                                    chart4.notifyDataSetChanged(); // refresh
//                                }
//                                /////////////////////////////////////////////////////////////
//
//                                if (xinLv.size() <= 0) {
//                                    setChartNoData(chart5);
//                                }else {
//                                  //  ArrayList<ILineDataSet> sets5 = new ArrayList<>();//多条数据
//
//                                    List<Entry> yVals15 = new ArrayList<>();
//                                    for (int i = 0; i < xinLv.size(); i++) {
//                                        yVals15.add(new Entry(i, xinLv.get(i).getData()));
//                                    }
//                                    String[] yl5 = new String[xinLv.size()];
//                                    for (int i = 0; i < xinLv.size(); i++) {
//                                        yl5[i] = xinLv.get(i).getTime();
//                                    }
//                                    Log.d("DAFragment1", Arrays.toString(yl5));
//                                    TimeAxisValueFormatter xAxisFormatter5 = new TimeAxisValueFormatter(yl5);
//                                    XAxis xAxis5 = chart5.getXAxis();
//                                    xAxis5.setValueFormatter(xAxisFormatter5);
//                                    xAxis5.setLabelCount(xinLv.size(),true);
//
//                                    LineDataSet ds15 = new LineDataSet(yVals15, "心率");
//
//                                    ds15.setColor(Color.parseColor("#FF00FF"));
//                                    ds15.setValueTextColor(Color.parseColor("#808080")); // styling, ...
//                                    ds15.notifyDataSetChanged();
//                                  //  sets5.add(ds15);
//                                    LineData da5 = new LineData(ds15);
//                                    chart5.setData(da5);//设置数据
//                                    chart5.notifyDataSetChanged(); // refresh
                        //        }

                             }
                       });

                }
            }
        }).start();



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_a1, container, false);
      //  @OnClick({R.id.quanbu, R.id.yichang, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.time_ll})
        view.findViewById(R.id.quanbu).setOnClickListener(this);
        view.findViewById(R.id.yichang).setOnClickListener(this);
        view.findViewById(R.id.bt1).setOnClickListener(this);
        view.findViewById(R.id.bt2).setOnClickListener(this);
        view.findViewById(R.id.bt3).setOnClickListener(this);
        view.findViewById(R.id.bt4).setOnClickListener(this);
        view.findViewById(R.id.bt5).setOnClickListener(this);
        view.findViewById(R.id.time_ll).setOnClickListener(this);

        quanbu=view.findViewById(R.id.quanbu);
        yichang=view.findViewById(R.id.yichang);
        timeTvJiankang=view.findViewById(R.id.time_tv_jiankang);
        bt1=view.findViewById(R.id.bt1);
        bt2=view.findViewById(R.id.bt2);
        bt3=view.findViewById(R.id.bt3);
        bt4=view.findViewById(R.id.bt4);
        bt5=view.findViewById(R.id.bt5);
        time_ll=view.findViewById(R.id.time_ll);
        chart=view.findViewById(R.id.chart1);


        EventBus.getDefault().register(this);

        if (getActivity() != null) {
            bt1.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 15));
            bt1.setChangeAlphaWhenPress(true);//点击改变透明度
            bt2.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 15));
            bt2.setChangeAlphaWhenPress(true);//点击改变透明度
            bt3.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 15));
            bt3.setChangeAlphaWhenPress(true);//点击改变透明度
            bt4.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 15));
            bt4.setChangeAlphaWhenPress(true);//点击改变透明度
            bt5.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 15));
            bt5.setChangeAlphaWhenPress(true);//点击改变透明度
            //  xianshi.setBorderColor(Color.parseColor("#59B683"));
        }
        timeTvJiankang.setText(DateUtils.time1(System.currentTimeMillis()+""));

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new HealthDataAdapter(R.layout.health_data_item, healthDataBeanList);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);
            }
        });


        popHeadAdapterLX = new PopHeadAdapter(xiangmuList, getActivity());

//        initChart1();
//        initChart2();
//        initChart3();
//        initChart4();
//        initChart5();

        return view;
    }

//    private void initChart1(){
//        chart.getDescription().setEnabled(false);//禁用描述
//        YAxis rightAxis= chart.getAxisRight();
//        rightAxis.setEnabled(false);//取消右边数值显示
//
//        XAxis xAxis= chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//从底部栏显示数值
//        xAxis.setDrawGridLines(false);//取消网格线
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        chart.animateX(2500);
//        chart.setPinchZoom(true);//不能缩放
//        chart.invalidate(); // refresh
//    }
//
//    private void initChart2(){
//        chart2.getDescription().setEnabled(false);//禁用描述
//        YAxis rightAxis= chart2.getAxisRight();
//        rightAxis.setEnabled(false);//取消右边数值显示
//
//        XAxis xAxis= chart2.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//从底部栏显示数值
//        xAxis.setDrawGridLines(false);//取消网格线
//
//        YAxis leftAxis = chart2.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        chart2.animateX(2500);
//        chart2.setPinchZoom(true);//不能缩放
//        chart2.invalidate(); // refresh
//    }
//
//    private void initChart3(){
//        chart3.getDescription().setEnabled(false);//禁用描述
//        YAxis rightAxis= chart3.getAxisRight();
//        rightAxis.setEnabled(false);//取消右边数值显示
//
//        XAxis xAxis= chart3.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//从底部栏显示数值
//        xAxis.setDrawGridLines(false);//取消网格线
//
//        YAxis leftAxis = chart3.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        chart3.animateX(2500);
//        chart3.setPinchZoom(true);//不能缩放
//        chart3.invalidate(); // refresh
//    }
//
//    private void initChart4(){
//        chart4.getDescription().setEnabled(false);//禁用描述
//        YAxis rightAxis= chart4.getAxisRight();
//        rightAxis.setEnabled(false);//取消右边数值显示
//
//        XAxis xAxis= chart4.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//从底部栏显示数值
//        xAxis.setDrawGridLines(false);//取消网格线
//
//        YAxis leftAxis = chart4.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        chart4.animateX(2500);
//        chart4.setPinchZoom(true);//不能缩放
//        chart4.invalidate(); // refresh
//    }
//
//    private void initChart5(){
//        chart5.getDescription().setEnabled(false);//禁用描述
//        YAxis rightAxis= chart5.getAxisRight();
//        rightAxis.setEnabled(false);//取消右边数值显示
//
//        XAxis xAxis= chart5.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//从底部栏显示数值
//        xAxis.setDrawGridLines(false);//取消网格线
//
//        YAxis leftAxis = chart5.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        chart5.animateX(2500);
//        chart5.setPinchZoom(true);//不能缩放
//        chart5.invalidate(); // refresh
//    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }




    private void reples() {
        bt1.setBackgroundColor(Color.parseColor("#FFF2F2F2"));
        bt1.setTextColor(Color.parseColor("#FFA7A7A7"));
        bt2.setBackgroundColor(Color.parseColor("#FFF2F2F2"));
        bt2.setTextColor(Color.parseColor("#FFA7A7A7"));
        bt3.setBackgroundColor(Color.parseColor("#FFF2F2F2"));
        bt3.setTextColor(Color.parseColor("#FFA7A7A7"));
        bt4.setBackgroundColor(Color.parseColor("#FFF2F2F2"));
        bt4.setTextColor(Color.parseColor("#FFA7A7A7"));
        bt5.setBackgroundColor(Color.parseColor("#FFF2F2F2"));
        bt5.setTextColor(Color.parseColor("#FFA7A7A7"));
    }

    private void setChartNoData(AAChartView chart){
//        chart.setData(null);
//        chart.setNoDataText("暂无数据");
//        chart.setNoDataTextColor(Color.parseColor("#FF59B683"));
//        chart.invalidate();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quanbu:
                quanbu.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.check_circl_fill1));
                yichang.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.check_circl_fill0));
                healthDataBeanList.clear();
                for (HuanZheBean.ResultBean.AbnormalListBean bean : abnormalListBeanArrayList) {
                    MeasurementAllBean allBean = new MeasurementAllBean();
                    allBean.setId(bean.getId());
                    allBean.setMeasureData(bean.getMeasureData());
                    allBean.setMeasuredType(bean.getDataType());
                    allBean.setMeasureTime(bean.getMeasureTime());
                    allBean.setType(2);//1是正常2是异常
                    healthDataBeanList.add(allBean);
                }
                for (HuanZheBean.ResultBean.MeasureDataListBean bean : measureDataListBeanList) {
                    MeasurementAllBean allBean = new MeasurementAllBean();
                    allBean.setId(bean.getId());
                    allBean.setMeasureData(bean.getMeasureData());
                    allBean.setMeasuredType(bean.getDataType());
                    allBean.setMeasureTime(bean.getMeasureTime());
                    allBean.setType(1);//1是正常2是异常
                    healthDataBeanList.add(allBean);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.yichang:
                quanbu.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.check_circl_fill0));
                yichang.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.check_circl_fill1));
                healthDataBeanList.clear();
                for (HuanZheBean.ResultBean.AbnormalListBean bean : abnormalListBeanArrayList) {
                    MeasurementAllBean allBean = new MeasurementAllBean();
                    allBean.setId(bean.getId());
                    allBean.setMeasureData(bean.getMeasureData());
                    allBean.setMeasuredType(bean.getDataType());
                    allBean.setMeasureTime(bean.getMeasureTime());
                    allBean.setType(2);//1是正常2是异常
                    healthDataBeanList.add(allBean);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt1://血压
            {
                reples();
                bt1.setBackgroundColor(Color.parseColor("#FF59B683"));
                bt1.setTextColor(Color.parseColor("#FFFFFFFF"));

                String[] yl=new String[xueYa1.size()];
                for (int i = 0; i < xueYa1.size(); i++) {
                    yl[i]=xueYa1.get(i).getTime();
                }
                Object[] xuel=new Object[xueYa1.size()];
                for (int i = 0; i < xueYa1.size(); i++) {
                    xuel[i]=xueYa1.get(i).getData();
                }
                Object[] xue2=new Object[xueYa2.size()];
                for (int i = 0; i < xueYa2.size(); i++) {
                    xue2[i]=xueYa2.get(i).getData();
                }
                if (aaChartModel!=null){
                    aaChartModel.categories=yl;
                    aaChartModel.series=new AASeriesElement[]{
                            new AASeriesElement()
                                    .color("#5AD8A6")
                                    .name("收缩压")
                                    .data(xuel),
                            new AASeriesElement()
                                    .color("#FFD9A7")
                                    .name("舒张压")
                                    .data(xue2)
                    };
                    chart.aa_refreshChartWithChartModel(aaChartModel);
                }

                //   chart.setVisibility(View.VISIBLE);
//                chart2.setVisibility(View.GONE);
//                chart3.setVisibility(View.GONE);
//                chart4.setVisibility(View.GONE);
//                chart5.setVisibility(View.GONE);

                break;
            }
            case R.id.bt2://血糖
                reples();
                bt2.setBackgroundColor(Color.parseColor("#FF59B683"));
                bt2.setTextColor(Color.parseColor("#FFFFFFFF"));
            {
                String[] yl=new String[xueTang.size()];
                for (int i = 0; i < xueTang.size(); i++) {
                    yl[i]=xueTang.get(i).getTime();
                }
                Object[] xuel=new Object[xueTang.size()];
                for (int i = 0; i < xueTang.size(); i++) {
                    xuel[i]=xueTang.get(i).getData();
                }
                if (aaChartModel!=null){
                    aaChartModel.categories=yl;
                    aaChartModel.series=new AASeriesElement[]{
                            new AASeriesElement()
                                    .color("#5AD8A6")
                                    .name("血糖")
                                    .data(xuel)
                    };
                    chart.aa_refreshChartWithChartModel(aaChartModel);
                }

                //chart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray();

//                chart.setVisibility(View.GONE);
//                chart2.setVisibility(View.VISIBLE);
//                chart3.setVisibility(View.GONE);
//                chart4.setVisibility(View.GONE);
//                chart5.setVisibility(View.GONE);

                break;
            }
            case R.id.bt3: {//体温
                reples();
                bt3.setBackgroundColor(Color.parseColor("#FF59B683"));
                bt3.setTextColor(Color.parseColor("#FFFFFFFF"));

                String[] yl=new String[tiWen.size()];
                for (int i = 0; i < tiWen.size(); i++) {
                    yl[i]=tiWen.get(i).getTime();
                }
                Object[] xuel=new Object[tiWen.size()];
                for (int i = 0; i < tiWen.size(); i++) {
                    xuel[i]=tiWen.get(i).getData();
                }
                if (aaChartModel!=null){
                    aaChartModel.categories=yl;
                    aaChartModel.series=new AASeriesElement[]{
                            new AASeriesElement()
                                    .color("#5AD8A6")
                                    .name("体温")
                                    .data(xuel)
                    };
                    chart.aa_refreshChartWithChartModel(aaChartModel);
                }

//                chart.setVisibility(View.GONE);
//                chart2.setVisibility(View.GONE);
//                chart3.setVisibility(View.VISIBLE);
//                chart4.setVisibility(View.GONE);
//                chart5.setVisibility(View.GONE);


                break;
            }
            case R.id.bt4:
            {
                reples();
                bt4.setBackgroundColor(Color.parseColor("#FF59B683"));
                bt4.setTextColor(Color.parseColor("#FFFFFFFF"));

                String[] yl=new String[maiBo.size()];
                for (int i = 0; i < maiBo.size(); i++) {
                    yl[i]=maiBo.get(i).getTime();
                }
                Object[] xuel=new Object[maiBo.size()];
                for (int i = 0; i < maiBo.size(); i++) {
                    xuel[i]=maiBo.get(i).getData();
                }
                if (aaChartModel!=null){
                    aaChartModel.categories=yl;
                    aaChartModel.series=new AASeriesElement[]{
                            new AASeriesElement()
                                    .color("#5AD8A6")
                                    .name("脉搏")
                                    .data(xuel)
                    };
                    chart.aa_refreshChartWithChartModel(aaChartModel);
                }

//                chart.setVisibility(View.GONE);
//                chart2.setVisibility(View.GONE);
//                chart3.setVisibility(View.GONE);
//                chart4.setVisibility(View.VISIBLE);
//                chart5.setVisibility(View.GONE);



                break;
            }
            case R.id.bt5: {
                reples();
                bt5.setBackgroundColor(Color.parseColor("#FF59B683"));
                bt5.setTextColor(Color.parseColor("#FFFFFFFF"));

                String[] yl=new String[xinLv.size()];
                for (int i = 0; i < xinLv.size(); i++) {
                    yl[i]=xinLv.get(i).getTime();
                }
                Object[] xuel=new Object[xinLv.size()];
                for (int i = 0; i < xinLv.size(); i++) {
                    xuel[i]=xinLv.get(i).getData();
                }

                if (aaChartModel!=null){
                    aaChartModel.categories=yl;
                    aaChartModel.series=new AASeriesElement[]{
                            new AASeriesElement()
                                    .color("#5AD8A6")
                                    .name("心率")
                                    .data(xuel)
                    };
                    chart.aa_refreshChartWithChartModel(aaChartModel);
                }
//                chart.setVisibility(View.GONE);
//                chart2.setVisibility(View.GONE);
//                chart3.setVisibility(View.GONE);
//                chart4.setVisibility(View.GONE);
//                chart5.setVisibility(View.VISIBLE);


                break;
            }
            case R.id.time_ll:
                if (getActivity() != null) {
                    popup = QMUIPopups.listPopup(getActivity(), 520, 400, popHeadAdapterLX, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("LoginActivity", "position:" + position);
                            timeTvJiankang.setText(xiangmuList.get(position).getTypeName());
                            popup.dismiss();
                        }
                    }).edgeProtection(QMUIDisplayHelper.dp2px(getActivity(), 20))
                            // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                            .offsetYIfBottom(QMUIDisplayHelper.dp2px(getActivity(), 4))
                            .shadow(true)
                            .arrow(true)
                            .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                            .show(time_ll);
                }
                break;

        }
    }
}
