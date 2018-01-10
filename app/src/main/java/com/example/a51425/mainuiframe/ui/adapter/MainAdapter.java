package com.example.a51425.mainuiframe.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 51425 on 2017/7/6.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    List<Fragment> datas;

    public MainAdapter(FragmentManager fm, List datas) {
        super(fm);
        this.datas=datas;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = datas.get(position);
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }
}
