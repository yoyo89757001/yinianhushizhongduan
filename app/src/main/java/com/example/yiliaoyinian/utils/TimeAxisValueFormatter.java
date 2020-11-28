//package com.example.yiliaoyinian.utils;
//
//import android.util.Log;
//import com.github.mikephil.charting.charts.BarLineChartBase;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.formatter.ValueFormatter;
//
///**
// * Created by philipp on 02/06/16.
// */
//public class TimeAxisValueFormatter extends IndexAxisValueFormatter
//{
//
//    private final String[] mMonths;
//
//   // private final BarLineChartBase<?> chart;
//    private int temp=-1;
//
//    public TimeAxisValueFormatter(String[] mMonths) {
//      //  this.chart = chart;
//        this.mMonths=mMonths;
//    }
//
//    @Override
//    public String getFormattedValue(float value) {
//        int days2 = Math.round(value);
//        int days = (int) value;
//        Log.d("TimeAxisValueFormatter", days2+" days:" + temp+" temp"+mMonths[days2%mMonths.length]);
//        if (temp==days2){
//          return "";
//        }else {
//            temp=days2;
////            if (days==0){
////                return mMonths[0];
////            }
////            if (days<mMonths.length){
////                return mMonths[days-1];
////            }else {
////                return mMonths[days-1];
////            }
//            Log.d("TimeAxisValue Formatter", mMonths[days2%mMonths.length]+"数据");
//            return mMonths[days2%mMonths.length];
//        }
//
//    }
//
//
//
//    private int getDaysForMonth(int month, int year) {
//
//        // month is 0-based
//
//        if (month == 1) {
//            boolean is29Feb = false;
//
//            if (year < 1582)
//                is29Feb = (year < 1 ? year + 1 : year) % 4 == 0;
//            else if (year > 1582)
//                is29Feb = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
//
//            return is29Feb ? 29 : 28;
//        }
//
//        if (month == 3 || month == 5 || month == 8 || month == 10)
//            return 30;
//        else
//            return 31;
//    }
//
//    private int determineMonth(int dayOfYear) {
//
//        int month = -1;
//        int days = 0;
//
//        while (days < dayOfYear) {
//            month = month + 1;
//
//            if (month >= 12)
//                month = 0;
//
//            int year = determineYear(days);
//            days += getDaysForMonth(month, year);
//        }
//
//        return Math.max(month, 0);
//    }
//
//    private int determineDayOfMonth(int days, int month) {
//
//        int count = 0;
//        int daysForMonths = 0;
//
//        while (count < month) {
//
//            int year = determineYear(daysForMonths);
//            daysForMonths += getDaysForMonth(count % 12, year);
//            count++;
//        }
//
//        return days - daysForMonths;
//    }
//
//    private int determineYear(int days) {
//
//        if (days <= 366)
//            return 2016;
//        else if (days <= 730)
//            return 2017;
//        else if (days <= 1094)
//            return 2018;
//        else if (days <= 1458)
//            return 2019;
//        else
//            return 2020;
//
//    }
//}
