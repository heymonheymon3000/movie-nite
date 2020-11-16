package com.android.movie.nite.features.main.ui

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.work.WorkManager
import com.android.movie.nite.R
import com.android.movie.nite.databinding.ActivityMainBinding
import com.android.movie.nite.features.authentication.firebase.ui.FirebaseLoginActivity
import com.android.movie.nite.utils.Constants
import com.android.movie.nite.utils.sendNotification
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("onCreate called!!!")

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)


        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movie -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.movieFragment)
                }
                R.id.fav_movie -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.favoriteMovieFragment)
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

        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
            .addOnCompleteListener { task ->
                var msg = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.message_subscribe_failed)
                }
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
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

//        navigationController = findNavController(R.id.nav_host_fragment)
//        NavigationUI.setupActionBarWithNavController(this,
//            navigationController)
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called!!!")

        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navigationController.navigateUp()
//    }


}
