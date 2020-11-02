package com.android.movie.nite.features.authentication.firebase.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.movie.nite.app.MainActivity
import com.android.movie.nite.R
import com.android.movie.nite.databinding.FragmentFirebaseLoginBinding
import com.android.movie.nite.features.authentication.firebase.model.FirebaseViewModel
import com.android.movie.nite.utils.Constants
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FirebaseLoginFragment : Fragment() {
    private lateinit var binding: FragmentFirebaseLoginBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_firebase_login, container, false
        )

        firebaseViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            if(FirebaseViewModel.AuthenticationState.AUTHENTICATED == authenticationState) {
                lifecycleScope.launch  {
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
            }
        })

        binding.lifecycleOwner = this
        binding.authButton.setOnClickListener {
            if (Constants.isNetworkConnected) {
                launchSignInFlow()
            } else {
                Snackbar.make(it, "Please connect to internet", Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun launchSignInFlow() {
        @Suppress("DEPRECATION")
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    arrayListOf(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.EmailBuilder().build()
                    )
                )
                .setIsSmartLockEnabled(true)
                    .setTheme(R.style.LoginTheme)
                .setAuthMethodPickerLayout(
                    AuthMethodPickerLayout
                        .Builder(R.layout.fragment_login)
                        .setGoogleButtonId(R.id.google_btn)
                        .setEmailButtonId(R.id.email_btn)
                        .build()
                )
                .build(),
            SIGN_IN_RESULT_CODE, ActivityOptions.makeCustomAnimation(
                context,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
            ).toBundle()
        );
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK) {
                Timber.i("Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }
}