package com.example.yiliaoyinian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yiliaoyinian.Beans.PaiBanShowBean;
import com.example.yiliaoyinian.R;

import java.util.List;


/**
 * Created by xingchaolei on 2017/12/5.
 */

public class PopHeadBlackAdapter extends BaseAdapter {

    private List<PaiBanShowBean> mGroupNames;
    private LayoutInflater mLayoutInflater;




    public PopHeadBlackAdapter(List<PaiBanShowBean> data, Context context) {
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
        holder.groupNameTv.setText(mGroupNames.get(position).getContent());


        return convertView;
    }


    public static class ViewHolder {
        TextView groupNameTv;
    }



}
