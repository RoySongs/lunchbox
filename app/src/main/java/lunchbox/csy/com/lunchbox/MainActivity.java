package lunchbox.csy.com.lunchbox;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ImageViewCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import lunchbox.csy.com.lunchbox.alarm.AlarmFragment;
import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;
import lunchbox.csy.com.lunchbox.home.HomeFragment;
import lunchbox.csy.com.lunchbox.lib.UIlib;
import lunchbox.csy.com.lunchbox.profile.ProfileFragment;
import lunchbox.csy.com.lunchbox.search.SearchFragment;
import lunchbox.csy.com.lunchbox.setting.SettingFragment;

public class MainActivity extends AbstBaseActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView mTextMessage;
    private BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreateChild() {
        UIlib.getInstance(this).setHideNavigation(true);
        UIlib.getInstance(this).setStatusBarColor(getResources().getColor(R.color.grey));

        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Sign-in Success.", Toast.LENGTH_SHORT).show();

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
        changeItemColor(0);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MainActivity.this.replaceFragment(HomeFragment.newInstance());
                    MainActivity.this.changeItemColor(0);
                    return false;
                case R.id.navigation_alarm:
                    MainActivity.this.replaceFragment(AlarmFragment.newInstance());
                    MainActivity.this.changeItemColor(1);
                    return false;
                case R.id.navigation_search:
                    MainActivity.this.replaceFragment(SearchFragment.newInstance());
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
}

