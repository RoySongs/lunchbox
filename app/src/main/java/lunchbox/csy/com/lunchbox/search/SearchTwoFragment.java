package lunchbox.csy.com.lunchbox.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lunchbox.csy.com.lunchbox.R;

public class SearchTwoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search_sub_two, container, false);


        List<SearchDataSet> list = new ArrayList<SearchDataSet>();
        list.add(new SearchDataSet(R.drawable.bbu));
        list.add(new SearchDataSet(R.drawable.zanch));
        list.add(new SearchDataSet(R.drawable.zzor));
        list.add(new SearchDataSet(R.drawable.betnam));
        list.add(new SearchDataSet(R.drawable.bossam));
        list.add(new SearchDataSet(R.drawable.bude));
        list.add(new SearchDataSet(R.drawable.bokk));
        list.add(new SearchDataSet(R.drawable.cal));
        list.add(new SearchDataSet(R.drawable.chicken));
        list.add(new SearchDataSet(R.drawable.care));
        list.add(new SearchDataSet(R.drawable.don));



        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new SearchRecyclerAdapter(getActivity(), list));
        return view;

    }
}
