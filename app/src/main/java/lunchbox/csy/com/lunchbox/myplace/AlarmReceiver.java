package lunchbox.csy.com.lunchbox.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import lunchbox.csy.com.lunchbox.MainActivity;
import lunchbox.csy.com.lunchbox.R;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String stateValue = intent.getExtras().getString("state");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "testChannle");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "one-channel";
            String channelName = "My Channel One";
            String channelDescription = "My Channel One Description";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);

            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this.context, channelId);

        } else {
            builder = new NotificationCompat.Builder(this.context);
        }

        builder.setSmallIcon(R.drawable.ic_home_black_24dp)
                .setTicker("HEIT")
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle("점심 메뉴 선정")
                .setContentText("짬뽕")
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent).setAutoCancel(true);

        if(Build.VERSION.SDK_INT < 16) {
            notificationManager.notify(1, builder.getNotification());
        }else {
            notificationManager.notify(1, builder.build());
        }


        /*
        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pIntent=PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);

        PendingIntent pIntent1=PendingIntent.getBroadcast(this, 0, new Intent(this, NotiReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_share, "ACTION1", pIntent1).build());
*/
    }
}
