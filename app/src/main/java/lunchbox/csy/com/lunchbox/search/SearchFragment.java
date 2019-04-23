package lunchbox.csy.com.lunchbox.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lunchbox.csy.com.lunchbox.R;

public class SearchFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private static String TAG = "HomeFragment";
    ViewPager viewPager;
    TabLayout tabLayout;
    public static SearchFragment newInstance() {
        // TODO Parameters
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.viewPager_search);
        SearchViewPagerAdapter searchViewPagerAdapter = new SearchViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(searchViewPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.search_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
