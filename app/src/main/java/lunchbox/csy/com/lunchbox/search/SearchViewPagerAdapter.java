package lunchbox.csy.com.lunchbox.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchViewPagerAdapter extends FragmentPagerAdapter{

    private String titles[] = new String[]{"상세검색","메뉴모음","추천코스"};
    ArrayList<Fragment> subFragments;
    public SearchViewPagerAdapter(FragmentManager fm) {
        super(fm);
        subFragments = new ArrayList<>();
        subFragments.add(new SearchOneFragment());
        subFragments.add(new SearchTwoFragment());
        subFragments.add(new SearchThirdFragment());
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