package lunchbox.csy.com.lunchbox.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import lunchbox.csy.com.lunchbox.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.search_recycle_item_imageView);

    }
}
