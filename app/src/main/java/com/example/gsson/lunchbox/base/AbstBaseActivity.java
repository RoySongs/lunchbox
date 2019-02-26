package com.example.gsson.lunchbox.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.gsson.lunchbox.commons.DataManager;


public abstract class AbstBaseActivity extends AppCompatActivity {
    protected abstract void onCreateChild();

    protected abstract void onDestoryChild();

    public abstract void onClickListener(View v);

    protected DataManager dataManager = null;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //기본 모드
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //회전 모드
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        dataManager = new DataManager(this);

        onCreateChild();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        onDestoryChild();
    }

    protected void startActivity(Class<?> activity) {
        Intent i = new Intent(this, activity);
        startActivity(i);
    }

    protected void startActivityForResult(Class<?> activity, int requestCode) {
        Intent i = new Intent(this, activity);
        startActivityForResult(i, requestCode);
    }

    protected void showToastShort(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void showToastLong(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
