package lunchbox.csy.com.lunchbox.roulette;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import lunchbox.csy.com.lunchbox.MainActivity;
import lunchbox.csy.com.lunchbox.R;

public class FoodRoulette extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Integer> images;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_roulette);

        images = new ArrayList<>();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(getApplicationContext(), images);



        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new CenterScrollListener());

        images.add(R.drawable.bbu);
        images.add(R.drawable.betnam);
        images.add(R.drawable.bokk);
        images.add(R.drawable.bossam);
        images.add(R.drawable.bude);
        images.add(R.drawable.cal);
        images.add(R.drawable.care);
        images.add(R.drawable.chicken);
        images.add(R.drawable.darkbokk);
        images.add(R.drawable.darkgal);
        images.add(R.drawable.darkgom);
        images.add(R.drawable.ddokbul);
        images.add(R.drawable.don);
        images.add(R.drawable.duck);
        images.add(R.drawable.gimbab);
        images.add(R.drawable.hesan);
        images.add(R.drawable.hotdog);
        images.add(R.drawable.mandu);
        images.add(R.drawable.momil);
        images.add(R.drawable.muk);
        images.add(R.drawable.nang);
        images.add(R.drawable.ozing);
        images.add(R.drawable.pasta);
        images.add(R.drawable.pat);
        images.add(R.drawable.pizza1);
        images.add(R.drawable.pizza2);
        images.add(R.drawable.ramen);
        images.add(R.drawable.ramen2);
        images.add(R.drawable.salad);
        images.add(R.drawable.samge);
        images.add(R.drawable.samgub);
        images.add(R.drawable.sand);
        images.add(R.drawable.sul);
        images.add(R.drawable.sun);
        images.add(R.drawable.sunde);
        images.add(R.drawable.taco);
        images.add(R.drawable.tangsuyuk);
        images.add(R.drawable.udong);
        images.add(R.drawable.uk);
        images.add(R.drawable.zanch);
        images.add(R.drawable.zeuk);
        images.add(R.drawable.zza);
        images.add(R.drawable.zzimdak);
        images.add(R.drawable.zzor);



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






        Button buttonRed = (Button) findViewById(R.id.button) ;
        buttonRed.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //롤링 멈추기
                final Runnable stop_runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacks(runnable);
                        int randomPosition = (int)(Math.random()*images.size());
                        recyclerView.smoothScrollToPosition(randomPosition);
                    }
                };
                handler.postDelayed(stop_runnable,0);





                final Runnable dialogdelay = new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FoodRoulette.this);
                        //builder.setIcon(R.drawable.salad);
                        builder.setTitle("당첨");
                        builder.setMessage("메뉴 이름이 나와야하는자리");
                        builder.setPositiveButton("공유하기", dialogListener);
                        builder.setNegativeButton("다시하기", dialogListener);
                        builder.setCancelable(false);


                        alertDialog = builder.create();
                        alertDialog.show();
                    }

                };
                handler.postDelayed(dialogdelay, 1000);



            }







            DialogInterface.OnClickListener dialogListener=new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(dialog==alertDialog && which==DialogInterface.BUTTON_POSITIVE){
                        Intent msg = new Intent(getIntent().ACTION_SEND);
                        msg.addCategory(Intent.CATEGORY_DEFAULT);
                        msg.putExtra(Intent.EXTRA_SUBJECT,"런치RUN!");
                        msg.putExtra(Intent.EXTRA_TEXT, "♥오늘의 점심메뉴는 오리구이 입니다♥");
                        msg.putExtra(Intent.EXTRA_TITLE, "제목");
                        msg.setType("text/plain");
                        startActivity(Intent.createChooser(msg, "공유하기"));


                    }else if(dialog==alertDialog && which==DialogInterface.BUTTON_NEGATIVE){
                        runnable.run();
                    }
                }
            };
        }) ;


    }
}