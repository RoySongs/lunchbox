package lunchbox.csy.com.lunchbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000000;
    private lunchbox.csy.com.lunchbox.ProgressImageView progressBar;
    ConstraintLayout splash;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(progressBar.VISIBLE);
        splash = findViewById(R.id.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out);
                splash.startAnimation(anim);
                splash.setVisibility(View.GONE);
            }
        }, 5000);

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }, 5000);
    }
}
