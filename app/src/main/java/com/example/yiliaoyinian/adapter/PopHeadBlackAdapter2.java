package com.example.yiliaoyinian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yiliaoyinian.Beans.HuLiZuBaen;
import com.example.yiliaoyinian.R;

import java.util.List;


/**
 * Created by xingchaolei on 2017/12/5.
 */

public class PopHeadBlackAdapter2 extends BaseAdapter {

    private List<HuLiZuBaen.ResultBean> mGroupNames;
    private LayoutInflater mLayoutInflater;




    public PopHeadBlackAdapter2(List<HuLiZuBaen.ResultBean> data, Context context) {
        mGroupNames=data;

    }

//    public List<HuLiZuBaen.ResultBean> getData() {
//        return mGroupNames;
//    }
//
//    public void setData(List<HuLiZuBaen.ResultBean> data) {
//        mGroupNames = data;
//    }



    @Override
    public int getCount() {
        return mGroupNames == null ? 0 : mGroupNames.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupNames == null ? null : mGroupNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.pophead_black_item, parent, false);
            holder = new ViewHolder();
            holder.groupNameTv =  convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (mGroupNames.get(position).getCategory()){//1-护工，2-护士，3-医生，4-护士长，5-院长
            case 1:
                holder.groupNameTv.setText("护工: "+mGroupNames.get(position).getNurseName());
                break;
            case 2:
                holder.groupNameTv.setText("护士: "+mGroupNames.get(position).getNurseName());
                break;
            case 3:
                holder.groupNameTv.setText("医生: "+mGroupNames.get(position).getNurseName());
                break;
            case 4:
                holder.groupNameTv.setText("护士长: "+mGroupNames.get(position).getNurseName());
                break;
            case 5:
                holder.groupNameTv.setText("院长: "+mGroupNames.get(position).getNurseName());
                break;

        }




        return convertView;
    }


    public static class ViewHolder {
        TextView groupNameTv;
    }



}
