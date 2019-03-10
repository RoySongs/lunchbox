package lunchbox.csy.com.lunchbox.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import lunchbox.csy.com.lunchbox.R;


public class DetailViewActivity extends AppCompatActivity{

    private TextView mTextMessage;


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_alarm:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_search:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//                case R.id.navigation_profile:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//
//            }
//            return false;
//        }
//    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_view);

            mTextMessage = (TextView) findViewById(R.id.message);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//            //    다이얼로그로 이미지 띄우기
//            Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.win_view);
//            dialog.setTitle("Custom Dialog");
//
//            TextView tv0 = (TextView) dialog.findViewById(R.id.text0);
//            tv0.setText("Hello. This is a Custom Dialog !");
//
//            ImageView iv0 = (ImageView) dialog.findViewById(R.id.image0);
//            iv0.setImageResource(R.drawable.select_title);
//
//            dialog.show();

        }








}
