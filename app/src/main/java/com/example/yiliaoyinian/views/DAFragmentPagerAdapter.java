package com.example.yiliaoyinian.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.yiliaoyinian.ui.fragments1.huanzhe.DAFragment1;
import com.example.yiliaoyinian.ui.fragments1.huanzhe.DAFragment2;
import com.example.yiliaoyinian.ui.fragments1.huanzhe.DAFragment3;
import com.example.yiliaoyinian.ui.fragments1.huanzhe.DAFragment4;
import com.example.yiliaoyinian.ui.fragments1.huanzhe.PatientInfoActivity;

import org.jetbrains.annotations.NotNull;

public class DAFragmentPagerAdapter extends FragmentPagerAdapter {

    private DAFragment1 myFragment1 = null;
    private DAFragment2 myFragment2 = null;
    private DAFragment3 myFragment3 = null;
    private DAFragment4 myFragment4 = null;

    public DAFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new DAFragment1();
        myFragment2 = new DAFragment2();
        myFragment3 = new DAFragment3();
        myFragment4 = new DAFragment4();

    }

    @Override
    public int getCount() {
        return 4;
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
            case PatientInfoActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case PatientInfoActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case PatientInfoActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
            case PatientInfoActivity.PAGE_FORE:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }
}

