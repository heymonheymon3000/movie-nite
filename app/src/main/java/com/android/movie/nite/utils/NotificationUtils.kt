package com.android.movie.nite.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.android.movie.nite.R
import com.android.movie.nite.features.authentication.firebase.ui.FirebaseLoginActivity


// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

// TODO: Step 1.1 extension function to send messages (GIVEN)
/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity
    // TODO: Step 1.11 create intent
    val contentIntent = Intent(applicationContext, FirebaseLoginActivity::class.java)

    // TODO: Step 1.12 create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // TODO: Step 2.0 add style
    val movieImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.logo
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(movieImage)
        .bigLargeIcon(null)



    // TODO: Step 2.2 add snooze action

    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.movie_refresh_worker_notification_channel_id)
    )

    // TODO: Step 1.8 use the new 'breakfast' notification channel

    // TODO: Step 1.3 set title, text and icon to builder
        .setSmallIcon(R.drawable.logo)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)

    // TODO: Step 1.13 set content intent
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

    // TODO: Step 2.1 add style to builder
        .setStyle(bigPicStyle)
        .setLargeIcon(movieImage)

    // TODO: Step 2.3 add snooze action

    // TODO: Step 2.5 set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // TODO: Step 1.4 call notify
    notify(NOTIFICATION_ID, builder.build())

}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}