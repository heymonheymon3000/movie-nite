package com.android.movie.nite.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.work.WorkManager
import com.android.movie.nite.R
import com.android.movie.nite.databinding.ActivityMainBinding
import com.android.movie.nite.features.authentication.firebase.ui.FirebaseLoginActivity
import com.android.movie.nite.utils.Constants
import com.android.movie.nite.utils.sendNotification
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movie -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.movieFragment)
                }
                R.id.fav_movie -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.movieFragment)
                }
                R.id.setting -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.movieFragment)
                }
                R.id.sign_out -> {
                    AuthUI.getInstance().signOut(applicationContext).addOnCompleteListener {
                        lifecycleScope.launch {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    FirebaseLoginActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }
            }
            true
        }

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(Constants.workerId)
            .observe(this, Observer { info ->
                if (info != null && info.state.isFinished) {
                    val notificationManager = ContextCompat.getSystemService(
                        applicationContext,
                        NotificationManager::class.java
                    ) as NotificationManager
                    notificationManager.sendNotification(applicationContext.getString(R.string.work_completed), applicationContext)
                }
            })
    }
}
