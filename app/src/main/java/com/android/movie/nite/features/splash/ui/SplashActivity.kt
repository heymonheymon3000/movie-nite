package com.android.movie.nite.features.splash.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.movie.nite.app.MainActivity
import com.android.movie.nite.features.authentication.firebase.model.FirebaseViewModel
import com.android.movie.nite.features.authentication.firebase.ui.FirebaseLoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val firebaseViewModel by viewModels<FirebaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseViewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                FirebaseViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    lifecycleScope.launch  {
                        delay(2000L)
                        startActivity(Intent(applicationContext, FirebaseLoginActivity::class.java))
                        finish()
                    }
                }
                else -> {
                    lifecycleScope.launch  {
                        delay(2000L)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }
}