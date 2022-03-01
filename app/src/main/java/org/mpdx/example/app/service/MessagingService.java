package org.mpdx.example.app.service;
//
//import static android.app.PendingIntent.FLAG_IMMUTABLE;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.mpdx.example.R;
//import org.mpdx.android.features.base.BaseActivity;
//import org.mpdx.example.features.splash.SplashActivity;
//
//import java.util.Random;
//
//import timber.log.Timber;
//
//public class MessagingService extends FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Intent newIntent = new Intent(getApplicationContext(), SplashActivity.class);
//        newIntent.putExtra(BaseActivity.EXTRA_DEEP_LINK_ACCOUNT_LIST_ID, remoteMessage.getData().get(BaseActivity.EXTRA_DEEP_LINK_ACCOUNT_LIST_ID));
//        newIntent.putExtra(BaseActivity.EXTRA_DEEP_LINK_CONTACT_URL, remoteMessage.getData().get(BaseActivity.EXTRA_DEEP_LINK_CONTACT_URL));
//        newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        newIntent.setAction("uniqueAction_" + new Random().nextInt(500));
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//        stackBuilder.addNextIntentWithParentStack(newIntent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, FLAG_IMMUTABLE);
//
//        String channelId = getString(R.string.push_notification_channel_id);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(getString(R.string.push_notification_channel_description));
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
//                .setSmallIcon(R.drawable.mpdx_logo_notification_icon)
//                .setContentTitle(String.format("%s %s", getResources().getString(R.string.app_name),
//                        getResources().getString(R.string.notifications)))
//                .setContentText(remoteMessage.getData().get("message"))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")))
//                .setAutoCancel(true)
//                .build();
//        if (remoteMessage.getMessageId() == null) {
//            return;
//        }
//        int notiId = remoteMessage.getMessageId().hashCode();
//        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//        manager.notify(notiId, notification);
//        Timber.d("Notification sent with id: %s", remoteMessage.getMessageId());
//    }
//}
