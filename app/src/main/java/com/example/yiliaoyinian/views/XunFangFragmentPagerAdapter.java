package com.example.yiliaoyinian.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.yiliaoyinian.ui.xunfang.XFFragment1;
import com.example.yiliaoyinian.ui.xunfang.XFFragment2;
import com.example.yiliaoyinian.ui.xunfang.XunFangJiLuActivity;

import org.jetbrains.annotations.NotNull;

public class XunFangFragmentPagerAdapter extends FragmentPagerAdapter {

    private XFFragment1 myFragment1 = null;
    private XFFragment2 myFragment2 = null;


    public XunFangFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new XFFragment1();
        myFragment2 = new XFFragment2();


    }

    @Override
    public int getCount() {
        return 2;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case XunFangJiLuActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case XunFangJiLuActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
        }
        return fragment;
    }
}

