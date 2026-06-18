package com.tutornow.app.ui.tutor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tutornow.app.data.model.Tutor
import com.tutornow.app.databinding.ActivityTutorDetailBinding
import com.tutornow.app.ui.booking.BookingActivity
import com.tutornow.app.util.Result
import com.tutornow.app.util.showToast

class TutorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorDetailBinding
    private lateinit var viewModel: TutorDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TutorDetailViewModel::class.java]

        val tutorId = intent.getIntExtra("tutor_id", -1)
        val tutorName = intent.getStringExtra("tutor_name") ?: ""

        binding.btnBack.setOnClickListener { finish() }
        binding.tvTitle.text = tutorName

        viewModel.tutor.observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    displayTutor(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(result.message)
                }
            }
        }

        viewModel.loadTutor(tutorId)
    }

    private fun displayTutor(tutor: Tutor) {
        val initials = tutor.name.split(" ").take(2).map { it.first().uppercaseChar() }.joinToString("")
        binding.tvInitials.text = initials
        binding.tvName.text = tutor.name
        binding.tvSubjects.text = tutor.subjects?.joinToString(", ") ?: "General"
        binding.tvBio.text = tutor.bio ?: "Experienced tutor ready to help you succeed."
        binding.tvRate.text = "₹${tutor.hourlyRate?.toInt() ?: 0} / hour"
        binding.tvRating.text = "⭐ ${String.format("%.1f", tutor.rating ?: 0.0)} (${tutor.totalReviews ?: 0} reviews)"
        binding.tvExperience.text = "${tutor.experience ?: 0} years experience"
        binding.tvLocation.text = "📍 ${tutor.location ?: "Online"}"
        binding.tvAvailable.text = if (tutor.isAvailable == true) "✅ Available" else "❌ Unavailable"

        binding.btnBookNow.setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java).apply {
                putExtra("tutor_id", tutor.id)
                putExtra("tutor_name", tutor.name)
                putExtra("tutor_subjects", tutor.subjects?.toTypedArray() ?: arrayOf("General"))
            })
        }
    }
}
