package com.tutornow.app.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tutornow.app.R
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.ActivityMainBinding
import com.tutornow.app.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = TutorNowApp.instance.sessionManager
        val role = sessionManager.getRole()

        val navGraphId: Int
        val menuId: Int

        if (role == "Tutor") {
            navGraphId = R.navigation.nav_tutor
            menuId = R.menu.menu_tutor_bottom_nav
        } else {
            navGraphId = R.navigation.nav_student
            menuId = R.menu.menu_student_bottom_nav
        }

        binding.bottomNav.menu.clear()
        binding.bottomNav.inflateMenu(menuId)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val inflater = navController.navInflater
        val graph = inflater.inflate(navGraphId)
        navController.graph = graph

        binding.bottomNav.setupWithNavController(navController)
    }

    fun logout() {
        TutorNowApp.instance.sessionManager.clearSession()
        startActivity(Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
