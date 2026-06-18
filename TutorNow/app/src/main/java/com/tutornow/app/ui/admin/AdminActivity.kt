package com.tutornow.app.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.ActivityAdminBinding
import com.tutornow.app.ui.auth.AuthActivity
import com.tutornow.app.util.Result
import com.tutornow.app.util.showToast

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: AdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        binding.tvGreeting.text = "Admin Dashboard 🛡️"

        val userAdapter = UserListAdapter { user ->
            showToast("User: ${user.name} (${user.role})")
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter

        binding.btnStudents.setOnClickListener {
            viewModel.loadStudents()
            binding.tvSectionTitle.text = "👨‍🎓 All Students"
        }
        binding.btnTutors.setOnClickListener {
            viewModel.loadTutors()
            binding.tvSectionTitle.text = "👨‍🏫 All Tutors"
        }
        binding.btnLogout.setOnClickListener {
            TutorNowApp.instance.sessionManager.clearSession()
            startActivity(Intent(this, AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }

        // Stats
        viewModel.stats.observe(this) { result ->
            if (result is Result.Success) {
                binding.tvTotalStudents.text = "${result.data.totalStudents}"
                binding.tvTotalTutors.text = "${result.data.totalTutors}"
                binding.tvTotalBookings.text = "${result.data.totalBookings}"
            }
        }

        viewModel.users.observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    userAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(result.message)
                }
            }
        }

        viewModel.loadStats()
        viewModel.loadStudents()
        binding.tvSectionTitle.text = "👨‍🎓 All Students"
    }
}
