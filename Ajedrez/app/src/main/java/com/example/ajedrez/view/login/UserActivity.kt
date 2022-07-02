package com.example.ajedrez.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ajedrez.R
import com.google.firebase.FirebaseApp

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_user)
        showLoginFragment()
    }

    //==============================================================================================
    // Load fragments
    //==============================================================================================
    private fun showLoginFragment() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(R.id.content_container, LoginFragment.newInstance())
            .commitAllowingStateLoss()
    }
}