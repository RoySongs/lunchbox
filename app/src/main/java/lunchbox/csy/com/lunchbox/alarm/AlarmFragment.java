package lunchbox.csy.com.lunchbox.alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import lunchbox.csy.com.lunchbox.MainActivity;
import lunchbox.csy.com.lunchbox.R;

public class AlarmFragment extends Fragment {
    private static String TAG = "HomeFragment";
    AlarmManager alarmManager;
    TimePicker alarmTimepicker;
    PendingIntent pendingIntent;

    NotificationManager manager;
    NotificationCompat.Builder builder=null;

    public static AlarmFragment newInstance() {
        // TODO Parameters
        AlarmFragment alarmFragment = new AlarmFragment();
        return alarmFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
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

                manager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "one-channel";
                    String channelName = "My Channel One";
                    String channelDescription = "My Channel One Description";
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription(channelDescription);

                    manager.createNotificationChannel(channel);
                    builder = new NotificationCompat.Builder(getContext(), channelId);

                } else {
                    builder = new NotificationCompat.Builder(getContext());
                }

                builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
                builder.setContentTitle("Content Title");
                builder.setContentText("Content Message");
                builder.setAutoCancel(true);
                builder.setWhen(calendar.getTimeInMillis());
                Intent intent=new Intent(getActivity(), MainActivity.class);
                PendingIntent pIntent=PendingIntent.getActivity(getContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pIntent);

                PendingIntent pIntent1=PendingIntent.getBroadcast(getContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.addAction(new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_share, "ACTION1", pIntent1).build());

                Bitmap largeIcon= BitmapFactory.decodeResource(getResources(), R.drawable.arrow_forward_small);
                builder.setLargeIcon(largeIcon);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent1);
                manager.notify(222, builder.build());

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
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }
}
