package com.example.yiliaoyinian.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.yiliaoyinian.ui.shuju.LanYaFragment1;
import com.example.yiliaoyinian.ui.shuju.LanYaFragment2;
import com.example.yiliaoyinian.ui.shuju.LanYaXueYaJiActivity;

import org.jetbrains.annotations.NotNull;

public class LanYaFragmentPagerAdapter extends FragmentPagerAdapter {

    private LanYaFragment1 myFragment1 = null;
    private LanYaFragment2 myFragment2 = null;


    public LanYaFragmentPagerAdapter(FragmentManager fragmentManager,String deviceType) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new LanYaFragment1(deviceType);
        myFragment2 = new LanYaFragment2();


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
            case LanYaXueYaJiActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case LanYaXueYaJiActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
        }
        return fragment;
    }
}

