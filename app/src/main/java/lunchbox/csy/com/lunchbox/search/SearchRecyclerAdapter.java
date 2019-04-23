package lunchbox.csy.com.lunchbox.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.SplashActivity;
import lunchbox.csy.com.lunchbox.activity.MenuPickerActivity;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<SearchDataSet> list;

    public SearchRecyclerAdapter(List<SearchDataSet> list) {
        this.list = list;
    }

    public SearchRecyclerAdapter(Context context, List<SearchDataSet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_search_sub_two_item, viewGroup, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {

        searchViewHolder.imageView.setImageResource(list.get(i).getImageSrc());
        searchViewHolder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , MenuPickerActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
