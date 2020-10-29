package com.android.movie.nite.features.authentication.firebase.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class FirebaseViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}