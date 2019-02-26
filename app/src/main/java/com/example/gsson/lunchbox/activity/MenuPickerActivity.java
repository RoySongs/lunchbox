package com.example.gsson.lunchbox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.example.gsson.lunchbox.R;
import com.example.gsson.lunchbox.base.AbstBaseActivity;
import com.example.gsson.lunchbox.commons.Common;

public class MenuPickerActivity extends AbstBaseActivity {

    //지연 메뉴룰렛 테스트중
    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_menu_picker);
        Common.showLogD("menu_picker debug");

        NumberPicker picker=findViewById(R.id.menu_picker);

        String[] data = new String[]{"토끼","원숭이","사슴","기린","사자","바다거북이","사막여우","살쾡이" +
                "닭","라자냐","크림스파게티","삼겹살도시락","치즈돈까스"};
        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);

    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {

    }
}
