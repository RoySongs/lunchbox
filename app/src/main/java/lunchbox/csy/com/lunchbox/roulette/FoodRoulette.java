package lunchbox.csy.com.lunchbox.roulette;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;

import lunchbox.csy.com.lunchbox.MainActivity;
import lunchbox.csy.com.lunchbox.R;

public class FoodRoulette extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Integer> images;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_roulette);

        images = new ArrayList<>();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(getApplicationContext(), images);


        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        images.add(R.drawable.bourne);
        images.add(R.drawable.bvs);
        images.add(R.drawable.cacw);
        images.add(R.drawable.deadpool);
        images.add(R.drawable.doctor);
        images.add(R.drawable.hunger);
        images.add(R.drawable.xmen);

        Button buttonRed = (Button) findViewById(R.id.button) ;
        buttonRed.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int speedScroll = 100;
                final int stopTime = 10000;
                final Handler handler = new Handler();

                //롤링 시작
                final Runnable runnable = new Runnable() {
                    int count = 0;
                    boolean flag = true;
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(count);
                        count++;
                        handler.postDelayed(this,speedScroll);
                    }
                };
                handler.postDelayed(runnable,speedScroll);

                //롤링 멈추기
                final Runnable stop_runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacks(runnable);
                        int randomPosition = (int)(Math.random()*images.size());
                        recyclerView.smoothScrollToPosition(randomPosition);
                    }
                };
                handler.postDelayed(stop_runnable,stopTime);
            }
        }) ;
    }
}
