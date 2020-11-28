package com.example.yiliaoyinian.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.yiliaoyinian.ui.fragments1.fuwu.FinshFragment;
import com.example.yiliaoyinian.ui.fragments1.fuwu.Fragment1_1;
import com.example.yiliaoyinian.ui.fragments1.fuwu.UnFinshFragment;

import org.jetbrains.annotations.NotNull;

public class FuWuFragmentPagerAdapter extends FragmentPagerAdapter {

    private UnFinshFragment myFragment1 = null;
    private FinshFragment myFragment2 = null;


    public FuWuFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myFragment1 = new UnFinshFragment();
        myFragment2 = new FinshFragment();


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
            case Fragment1_1.PAGE_ONE:
                fragment = myFragment1;
                break;
            case Fragment1_1.PAGE_TWO:
                fragment = myFragment2;
                break;
        }
        return fragment;
    }
}

