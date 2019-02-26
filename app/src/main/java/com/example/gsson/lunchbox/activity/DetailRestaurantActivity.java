package com.example.gsson.lunchbox.activity;

import android.view.View;

import com.example.gsson.lunchbox.R;
import com.example.gsson.lunchbox.base.AbstBaseActivity;
import com.example.gsson.lunchbox.commons.Common;

//가람담당_식당상세화면
public class DetailRestaurantActivity extends AbstBaseActivity {

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_detail_restaurant);
        Common.showLogD("test log debug");
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
}
