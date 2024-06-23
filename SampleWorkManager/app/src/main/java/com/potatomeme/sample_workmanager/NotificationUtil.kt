package com.potatomeme.sample_workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

object NotificationUtil {
    private const val CHANEL_ID = "Chanel Id"
    private const val CHANEL_NAME = "Chanel Name"

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context) {
        val notiChannel = NotificationChannel(
            CHANEL_ID,
            CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "SWM 알림 채널 입니다."
            enableLights(true)
            lightColor = Color.RED
            setShowBadge(true)
            vibrationPattern = longArrayOf(100, 200, 100, 200)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        getManager(context).createNotificationChannel(notiChannel)
    }


    private fun getManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun deleteChannel(context: Context, channelNm: String) {
        getManager(context).deleteNotificationChannel(channelNm)
    }

    fun createIntentNotification(
        context: Context,
        message: String,
        _intentClass: Class<*>,
    ) {
        val intent = Intent(context, _intentClass)
        intent.putExtra("message", message)
        settingNotification(context, message, intent)
    }

    fun createNotification(
        context: Context,
        message: String,
    ) {
        showNotification(context, 1, message)
    }


    private fun settingNotification(
        context: Context,
        message: String,
        intent: Intent,
    ) {
        val notiId = (System.currentTimeMillis() / 1000).toInt()
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            notiId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        showNotification(context, notiId, message, pendingIntent)
    }

    private fun showNotification(
        context: Context,
        notificationId: Int,
        message: String,
        pendingIntent: PendingIntent? = null,
    ) {

        val notificationBuilder = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("SWM")
            .setContentText(message)
            .setAutoCancel(true)
            .setChannelId(CHANEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        pendingIntent?.run {
            notificationBuilder.setContentIntent(pendingIntent)
        }

        getManager(context).notify(notificationId, notificationBuilder.build())
    }
}