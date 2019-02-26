package com.example.gsson.lunchbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gsson.lunchbox.base.AbstBaseActivity;

public class MainActivity extends AbstBaseActivity {

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.bt01:
                showToastLong("hello world");
                break;
        }
    }
}
