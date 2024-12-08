package com.example.tabbar11;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTital = new ArrayList<>();

    public VPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override

    public Fragment getItem(int position) {

        return fragmentArrayList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String tital){
        fragmentArrayList.add(fragment);
        fragmentTital.add(tital);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        return fragmentTital.get(position);
    }
}
