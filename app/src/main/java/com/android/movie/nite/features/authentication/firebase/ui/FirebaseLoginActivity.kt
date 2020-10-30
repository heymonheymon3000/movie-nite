package com.android.movie.nite.features.authentication.firebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.movie.nite.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)
    }
}