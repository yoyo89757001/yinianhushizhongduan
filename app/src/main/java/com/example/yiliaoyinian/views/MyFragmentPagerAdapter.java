package com.example.yiliaoyinian.views;



import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.yiliaoyinian.ui.fragmengmain.Fragment1;
import com.example.yiliaoyinian.ui.fragmengmain.Fragment2;

import com.example.yiliaoyinian.ui.MainActivity;
import com.example.yiliaoyinian.ui.shuju.Fragment3;
import com.example.yiliaoyinian.ui.wode.Fragment4;

import org.jetbrains.annotations.NotNull;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment1 myFragment1 = null;
    private Fragment2 myFragment2 = null;
    private Fragment3 myFragment3 = null;
    private Fragment4 myFragment4 = null;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new Fragment1();
        myFragment2 = new Fragment2();
        myFragment3 = new Fragment3();
        myFragment4 = new Fragment4();

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
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
            case MainActivity.PAGE_FORE:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }
}

