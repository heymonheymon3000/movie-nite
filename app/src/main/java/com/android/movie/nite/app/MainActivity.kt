package com.android.movie.nite.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.android.movie.nite.R
import com.android.movie.nite.databinding.ActivityMainBinding
import com.android.movie.nite.features.authentication.firebase.ui.FirebaseLoginActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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
                            startActivity(Intent(applicationContext, FirebaseLoginActivity::class.java))
                            finish()
                        }
                    }
                }
            }
            true
        }
    }
}
