package com.tutornow.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.ActivitySplashBinding
import com.tutornow.app.ui.admin.AdminActivity
import com.tutornow.app.ui.auth.AuthActivity
import com.tutornow.app.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNext()
        }, 2200)
    }

    private fun navigateToNext() {
        val sessionManager = TutorNowApp.instance.sessionManager
        if (sessionManager.isLoggedIn()) {
            when (sessionManager.getRole()) {
                "Admin" -> startActivity(Intent(this, AdminActivity::class.java))
                else -> startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()
    }
}
