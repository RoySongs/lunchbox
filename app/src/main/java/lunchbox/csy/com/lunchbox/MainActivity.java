package lunchbox.csy.com.lunchbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import lunchbox.csy.com.lunchbox.alarm.AlarmFragment;
import lunchbox.csy.com.lunchbox.alarm.AlarmPreferenceActivity;
import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;
import lunchbox.csy.com.lunchbox.home.HomeFragment;
import lunchbox.csy.com.lunchbox.lib.UIlib;
import lunchbox.csy.com.lunchbox.location.LocationFragment;
import lunchbox.csy.com.lunchbox.profile.ProfileFragment;
import lunchbox.csy.com.lunchbox.search.SearchFragment;
import lunchbox.csy.com.lunchbox.setting.SettingFragment;

public class MainActivity extends AbstBaseActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView mTextMessage;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private AlertDialog customdialog;
    private ProgressBar progressBar;
    RelativeLayout splash;

    @Override
    protected void onCreateChild() {
        UIlib.getInstance(this).setHideNavigation(true);
        UIlib.getInstance(this).setStatusBarColor(getResources().getColor(R.color.grey));
        setContentView(R.layout.activity_main);

        Log.d("FFFFF", "after LOGIN");

//        progressBar = (ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setVisibility(progressBar.VISIBLE);
//        splash = findViewById(R.id.splash);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeout);
//                splash.startAnimation(anim);
//                splash.setVisibility(View.GONE);
//            }
//        }, 5000);

        mTextMessage = findViewById(R.id.message);
        bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.enableAnimation(false)
                .enableAnimation(false)
                .enableItemShiftingMode(false) // Enable the shifting mode for each item. It will have a shifting animation for item if true. Otherwise the item text is always shown. Default true when item count > 3.
                .enableShiftingMode(false) // Enable the shifting mode for navigation. It will has a shift animation if true. Otherwise all items are the same width. Default true when item count > 3.
                .setIconSize(35) // Set all item ImageView size(dp).
                .setIconsMarginTop(0) // set margin top for all icons.
                .setTextVisibility(false) // Hide Text.
                .setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(HomeFragment.newInstance());
        changeItemColor(2);
    }

    @Override
    protected void onDestoryChild() {

    }

    //xml에 onClick 적용 후 이 곳에서 case문으로 추가하여 버튼 누르면 -하게 기능구현하기
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
//            case R.id.button1:
//                break;
//                default:
//                    break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_alarm){
            startActivity(new Intent(MainActivity.this, AlarmPreferenceActivity.class));
            //MainActivity.this.replaceFragment(AlarmFragment.newInstance());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_myplace:
                    MainActivity.this.replaceFragment(LocationFragment.newInstance());
                    MainActivity.this.changeItemColor(0);
                    return false;
                case R.id.navigation_search:
                    MainActivity.this.replaceFragment(SearchFragment.newInstance());
                    MainActivity.this.changeItemColor(1);
                    return false;
                case R.id.navigation_home:
                    MainActivity.this.replaceFragment(HomeFragment.newInstance());
                    MainActivity.this.changeItemColor(2);
                    return false;
                case R.id.navigation_profile:
                    MainActivity.this.replaceFragment(ProfileFragment.newInstance());
                    MainActivity.this.changeItemColor(3);
                    return false;
                case R.id.navigation_setting:
                    MainActivity.this.replaceFragment(SettingFragment.newInstance());
                    MainActivity.this.changeItemColor(4);
                    return false;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
    }

    private void changeItemColor(int position) {
        Log.d(TAG, "position: " + position);
        Log.d(TAG, "currentItem: " + bottomNavigationViewEx.getCurrentItem());
        for (int i = 0; i < 5; i++) {
            ImageViewCompat.setImageTintList(bottomNavigationViewEx.getIconAt(i), ColorStateList.valueOf(Color.parseColor("#ffbfbfbf")));
        }
        ImageViewCompat.setImageTintList(bottomNavigationViewEx.getIconAt(position), ColorStateList.valueOf(Color.parseColor("#ff000000")));
    }


    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                finish();
            }
        }
    };

    //지연 종료 다이얼로그
    @Override
    public void onBackPressed() {
        AlertDialog.Builder backdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_back_dialog, null);
        backdialog.setView(view);
        backdialog.setCancelable(false);
        backdialog.setPositiveButton("확인", dialogListener);
        backdialog.setNegativeButton("취소", null);


        customdialog = backdialog.create();
        customdialog.show();

    }
}
