package com.example.gsson.lunchbox.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.gsson.lunchbox.R;
import com.example.gsson.lunchbox.base.AbstBaseActivity;
import com.example.gsson.lunchbox.commons.Common;

//가람담당_식당상세화면
public class DetailRestaurantActivity extends AbstBaseActivity {

    TextView tvMenuName;

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_detail_restaurant);
        Common.showLogD("test log debug");
        tvMenuName=(TextView)findViewById(R.id.tvMenuName);

        //음식점 목록 중 하나를 누르면 목록의 데이터를 intent에 담아 이 화면으로 이동_이 화면에서 해당 intent 값 적용
//        getIntentData();
    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                showToastShort("test toast");
                break;
            default:
                break;
        }
    }

    private void getIntentData() {
        Intent i = getIntent();
        String menuName = i.getStringExtra("menuName");
        int distance = i.getIntExtra("distance", -1);

        boolean isException = false;
        isException = menuName == null || distance == -1;

        if (isException) {
            Common.showLogD("화면이동 후 값 받을 때 값이 제대로 안 넘어 옴_isException=" + isException);
        } else {
            tvMenuName.setText(menuName);
        }
    }
}
