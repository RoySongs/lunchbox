package lunchbox.csy.com.lunchbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;

public class SplashActivity extends AbstBaseActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000000;
    LinearLayout splash;
    String doCheck;

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_splash);

        LinearLayout nextClick = (LinearLayout) findViewById(R.id.nextClick);
        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheck = "Y";
                Intent intent = new Intent(SplashActivity.this, lunchbox.csy.com.lunchbox.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        splash = findViewById(R.id.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out);
                splash.startAnimation(anim);
                splash.setVisibility(View.GONE);
            }
        }, SPLASH_DISPLAY_LENGTH);


//            new Handler().postDelayed(() -> {
//                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
//                SplashActivity.this.startActivity(mainIntent);
//                SplashActivity.this.finish();
//            }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {

    }
}
