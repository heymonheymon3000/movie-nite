package com.android.movie.nite.features.movie.service

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.android.movie.nite.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MovieFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        //Timber.i("Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //Timber.i("From: ${remoteMessage.from}")

        remoteMessage.data.let {
            //Timber.i( "Message data payload: %s", remoteMessage.data)
        }

        remoteMessage.notification.let {
            if (it != null) {
                //Timber.i( "Message Notification Body: ${it.body}")
                it.body?.let { it1 -> sendNotification(it1) }
            }
        }
    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }
}