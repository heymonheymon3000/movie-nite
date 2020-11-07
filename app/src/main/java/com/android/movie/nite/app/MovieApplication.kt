package com.android.movie.nite.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.android.movie.nite.BuildConfig
import com.android.movie.nite.R
import com.android.movie.nite.features.movie.work.RefreshDataWorker
import com.android.movie.nite.network.CheckNetwork
import com.android.movie.nite.utils.Constants
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MovieApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        delayedInit()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun delayedInit() {
        applicationScope.launch {
            CheckNetwork(applicationContext).registerNetworkCallback()
            setupNotificationChannels()
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest
                = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        Constants.workerId = repeatingRequest.id

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)

//        val request = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
//        Constants.workerId = request.id
//        WorkManager.getInstance(applicationContext).enqueue(request)

    }

    private fun setupNotificationChannels() {
        createChannel(
            getString(R.string.movie_refresh_worker_notification_channel_id),
            getString(R.string.movie_refresh_worker_notification_channel_name))

        createChannel(
            getString(R.string.movie_refresh_notification_channel_id),
            getString(R.string.movie_refresh_notification_channel_name))
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = applicationContext.getString(R.string.app_name)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
