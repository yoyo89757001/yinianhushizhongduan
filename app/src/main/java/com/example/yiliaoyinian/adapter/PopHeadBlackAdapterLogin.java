package com.example.yiliaoyinian.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yiliaoyinian.Beans.UserNameBean;
import com.example.yiliaoyinian.R;

import java.util.List;


/**
 * Created by xingchaolei on 2017/12/5.
 */

public class PopHeadBlackAdapterLogin extends BaseAdapter {

    private List<UserNameBean> mGroupNames;
    private LayoutInflater mLayoutInflater;
    private DeleteInteface deleteInteface=null;


    public PopHeadBlackAdapterLogin(List<UserNameBean> data,DeleteInteface deleteInteface) {
        mGroupNames=data;
        this.deleteInteface=deleteInteface;
    }



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
            convertView = mLayoutInflater.inflate(R.layout.pophead_black_item2, parent, false);
            holder = new ViewHolder();
            holder.groupNameTv =  convertView.findViewById(R.id.title);
            holder.imageView=convertView.findViewById(R.id.shanchu);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInteface.deleteById(mGroupNames.get(position).getId());
            }
        });
        holder.groupNameTv.setText(mGroupNames.get(position).getUserName());
        return convertView;
    }


    public static class ViewHolder {
        TextView groupNameTv;
        ImageView imageView;
    }



}
