package lunchbox.csy.com.lunchbox.alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import lunchbox.csy.com.lunchbox.R;

public class AlarmFragment extends Fragment {
    private static String TAG = "HomeFragment";
    AlarmManager alarmManager;
    TimePicker alarmTimepicker;
    PendingIntent pendingIntent;

    public static AlarmFragment newInstance() {
        // TODO Parameters
        AlarmFragment alarmFragment = new AlarmFragment();
        return alarmFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Alarm onCreateView");
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmTimepicker = view.findViewById(R.id.time_picker);

        final Calendar calendar = Calendar.getInstance();
        final Intent alarmIntent = new Intent(this.getContext(), AlarmReceiver.class);

        Button alarmStart = view.findViewById(R.id.btn_start);
        alarmStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int getHour, getMinute;

                if(Build.VERSION.SDK_INT < 16) {
                    Log.d(TAG,"여기111111111111111111111111");

                } else {
                    Log.d(TAG,"여기2222222222222222222222222");
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET},0);
                }
                if(Build.VERSION.SDK_INT < 23){
                    getHour = alarmTimepicker.getCurrentHour();
                    getMinute = alarmTimepicker.getCurrentMinute();

                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimepicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarmTimepicker.getCurrentMinute());
                } else{
                    getHour = alarmTimepicker.getHour();
                    getMinute = alarmTimepicker.getMinute();

                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimepicker.getHour());
                    calendar.set(Calendar.MINUTE, alarmTimepicker.getMinute());
                }


                Toast.makeText(getActivity(),"Alarm 예정 :"+getHour+" 시 "+ getMinute + " 분", Toast.LENGTH_SHORT).show();
                alarmIntent.putExtra("state","alarmStart");
                pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                getActivity().sendBroadcast(alarmIntent);

            }
        });

        Button alarmFinish = view.findViewById(R.id.btn_finish);
        alarmFinish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Alarm 종료", Toast.LENGTH_SHORT).show();
                alarmManager.cancel(pendingIntent);
                alarmIntent.putExtra("state","alarmFinish");

                getActivity().sendBroadcast(alarmIntent);
            }
        });

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("alarmYn",true);
        editor.commit();
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

}
