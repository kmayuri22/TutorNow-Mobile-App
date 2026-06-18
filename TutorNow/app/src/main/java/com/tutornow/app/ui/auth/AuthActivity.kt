package com.tutornow.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.ActivityAuthBinding
import com.tutornow.app.ui.admin.AdminActivity
import com.tutornow.app.ui.main.MainActivity
import com.tutornow.app.util.showToast

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = TutorNowApp.instance.sessionManager
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(sessionManager)
        )[AuthViewModel::class.java]

        setupViewPager()
        observeViewModel()
    }

    private fun setupViewPager() {
        val adapter = AuthPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = if (pos == 0) "Login" else "Register"
        }.attach()
    }

    private fun observeViewModel() {
        viewModel.loginSuccess.observe(this) { response ->
            response?.let {
                when (it.role) {
                    "Admin" -> startActivity(Intent(this, AdminActivity::class.java))
                    else -> startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            }
        }

        viewModel.registerSuccess.observe(this) {
            it?.let {
                showToast("Registration successful! Please log in.")
                binding.viewPager.currentItem = 0
            }
        }

        viewModel.error.observe(this) { msg ->
            msg?.let { showToast(it) }
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.loadingOverlay.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }
}
