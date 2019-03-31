package yapp14th.co.kr.myplant.ui.main;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = null;

    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void init(ArrayList<Fragment> fragments) {
        this.fragments = new ArrayList<>();
        this.fragments.addAll(fragments);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
