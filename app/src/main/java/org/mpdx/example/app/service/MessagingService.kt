package org.mpdx.example.app.service

// import com.google.firebase.messaging.FirebaseMessagingService
// import com.google.firebase.messaging.RemoteMessage
// import android.content.Intent
// import org.mpdx.features.splash.SplashActivity
// import android.app.PendingIntent
// import org.mpdx.R
// import android.app.NotificationChannel
// import android.app.NotificationManager
// import android.app.TaskStackBuilder
// import android.os.Build
// import androidx.core.app.NotificationCompat
// import androidx.core.app.NotificationManagerCompat
// import org.mpdx.android.features.base.BaseActivity
// import timber.log.Timber
// import java.util.Random

/**
 * This class is an example of how to create the Firebase Messaging Service.  The code in this example is commented out
 * because firebase is not implemented in this Example.
 */
class MessagingService
//    : FirebaseMessagingService() {
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        val newIntent = Intent(applicationContext, SplashActivity::class.java)
//        newIntent.putExtra(BaseActivity.EXTRA_DEEP_LINK_ACCOUNT_LIST_ID, remoteMessage.data[BaseActivity.EXTRA_DEEP_LINK_ACCOUNT_LIST_ID])
//        newIntent.putExtra(BaseActivity.EXTRA_DEEP_LINK_CONTACT_URL, remoteMessage.data[BaseActivity.EXTRA_DEEP_LINK_CONTACT_URL])
//        newIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        newIntent.action = "uniqueAction_" + Random().nextInt(500)
//        val stackBuilder = TaskStackBuilder.create(applicationContext)
//        stackBuilder.addNextIntentWithParentStack(newIntent)
//        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
//        val channelId = getString(R.string.push_notification_channel_id)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
//            channel.description = getString(R.string.push_notification_channel_description)
//            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//        val notification = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.mpdx_logo_notification_icon)
//            .setContentTitle(String.format("%s %s", resources.getString(R.string.app_name),
//                resources.getString(R.string.notifications)))
//            .setContentText(remoteMessage.data["message"])
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(remoteMessage.data["message"]))
//            .setAutoCancel(true)
//            .build()
//        if (remoteMessage.messageId == null) {
//            return
//        }
//        val notiId = remoteMessage.messageId.hashCode()
//        val manager = NotificationManagerCompat.from(applicationContext)
//        manager.notify(notiId, notification)
//        Timber.d("Notification sent with id: %s", remoteMessage.messageId)
//    }
// }
