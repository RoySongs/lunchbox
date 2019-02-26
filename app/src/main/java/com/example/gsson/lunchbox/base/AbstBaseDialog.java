package com.example.gsson.lunchbox.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

public abstract class AbstBaseDialog extends AbstBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 자체 배경을 설정
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setFlags(WindowManager.LayoutParams.ALPHA_CHANGED, WindowManager.LayoutParams.ALPHA_CHANGED);
        getWindow().setGravity(Gravity.CENTER);
        //다이얼로그 영역 외 클릭 시 종료여부(버전 11이상 가능)
        setFinishOnTouchOutside(false);
    }
}
