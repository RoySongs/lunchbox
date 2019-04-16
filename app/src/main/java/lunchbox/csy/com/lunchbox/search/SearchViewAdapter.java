package lunchbox.csy.com.lunchbox.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchViewAdapter extends FragmentPagerAdapter{

    private String titles[] = new String[]{"상세검색","추천메뉴"};
    ArrayList<Fragment> subFragments;
    public SearchViewAdapter(FragmentManager fm) {
        super(fm);
        subFragments = new ArrayList<>();
        subFragments.add(new SearchOneFragment());
        subFragments.add(new SearchTwoFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return subFragments.get(i);
    }

    @Override
    public int getCount() {
        return this.subFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int i){
        return titles[i];
    }

}