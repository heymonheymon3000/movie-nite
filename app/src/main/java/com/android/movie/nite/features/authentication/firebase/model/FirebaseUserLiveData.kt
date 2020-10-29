package com.android.movie.nite.features.authentication.firebase.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.LiveData

class FirebaseUserLiveData : LiveData<FirebaseUser?>() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}