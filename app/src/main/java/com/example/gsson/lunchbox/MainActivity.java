package com.example.gsson.lunchbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gsson.lunchbox.base.AbstBaseActivity;


public class MainActivity extends AbstBaseActivity {

    AlertDialog customdialog;

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


    DialogInterface.OnClickListener dialogListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==DialogInterface.BUTTON_POSITIVE){
               finish();
            }
        }
    };
    //지연 종료 다이얼로그
    @Override
    public void onBackPressed() {
        AlertDialog.Builder backdialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.activity_back_dialog,null);
        backdialog.setView(view);
        backdialog.setCancelable(false);
        backdialog.setPositiveButton("확인", dialogListener);
        backdialog.setNegativeButton("취소",null);


        customdialog=backdialog.create();
        customdialog.show();
    }

}
