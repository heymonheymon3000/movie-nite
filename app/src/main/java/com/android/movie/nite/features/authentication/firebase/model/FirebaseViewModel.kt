package com.android.movie.nite.features.authentication.firebase.model

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class FirebaseViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

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