package com.example.yiliaoyinian.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.yiliaoyinian.ui.shuju.Fragment3;
import com.example.yiliaoyinian.ui.shuju.MachineFragment1;
import com.example.yiliaoyinian.ui.shuju.MachineFragment2;
import com.example.yiliaoyinian.ui.shuju.MachineFragment3;
import com.example.yiliaoyinian.ui.shuju.MachineFragment4;
import com.example.yiliaoyinian.ui.shuju.MachineFragment5;

import org.jetbrains.annotations.NotNull;

public class MachineFragmentPagerAdapter extends FragmentPagerAdapter {

    private MachineFragment1 myFragment1 = null;
    private MachineFragment2 myFragment2 = null;
    private MachineFragment3 myFragment3 = null;
    private MachineFragment4 myFragment4 = null;
    private MachineFragment5 myFragment5 = null;

    public MachineFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new MachineFragment1();
        myFragment2 = new MachineFragment2();
        myFragment3 = new MachineFragment3();
        myFragment4 = new MachineFragment4();
        myFragment5 = new MachineFragment5();

    }

    @Override
    public int getCount() {
        return 5;
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
            case Fragment3.PAGE_ONE:
                fragment = myFragment1;
                break;
            case Fragment3.PAGE_TWO:
                fragment = myFragment2;
                break;
            case Fragment3.PAGE_THREE:
                fragment = myFragment3;
                break;
            case Fragment3.PAGE_FOR:
                fragment = myFragment4;
                break;
            case Fragment3.PAGE_FILE:
                fragment = myFragment5;
                break;
        }
        return fragment;
    }
}

