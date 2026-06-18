package com.tutornow.app.ui.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tutornow.app.R
import com.tutornow.app.databinding.ActivityBookingBinding
import com.tutornow.app.util.Result
import com.tutornow.app.util.showToast
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var viewModel: BookingViewModel
    private var selectedDateTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        val tutorId = intent.getIntExtra("tutor_id", -1)
        val tutorName = intent.getStringExtra("tutor_name") ?: ""
        val subjects = intent.getStringArrayExtra("tutor_subjects") ?: arrayOf("General")

        binding.tvTutorName.text = "Booking with $tutorName"
        binding.btnBack.setOnClickListener { finish() }

        // Subject dropdown
        val subjectAdapter = ArrayAdapter(this, R.layout.item_dropdown, subjects)
        binding.subjectDropdown.setAdapter(subjectAdapter)
        if (subjects.isNotEmpty()) binding.subjectDropdown.setText(subjects[0], false)

        // Booking type
        val types = arrayOf("Online", "In-Person")
        val typeAdapter = ArrayAdapter(this, R.layout.item_dropdown, types)
        binding.typeDropdown.setAdapter(typeAdapter)
        binding.typeDropdown.setText("Online", false)

        // Date/Time picker
        binding.btnSelectDateTime.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                TimePickerDialog(this, { _, h, min ->
                    selectedDateTime = String.format("%04d-%02d-%02dT%02d:%02d:00", y, m + 1, d, h, min)
                    binding.tvSelectedDateTime.text = "📅 $selectedDateTime"
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnConfirmBooking.setOnClickListener {
            val subject = binding.subjectDropdown.text?.toString() ?: ""
            val bookingType = binding.typeDropdown.text?.toString()?.lowercase() ?: "online"
            val notes = binding.etNotes.text?.toString()

            if (selectedDateTime.isBlank()) {
                showToast("Please select a date and time")
                return@setOnClickListener
            }

            viewModel.createBooking(tutorId, subject, bookingType, selectedDateTime, notes)
        }

        viewModel.bookingResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnConfirmBooking.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showToast("Booking created successfully! 🎉")
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnConfirmBooking.isEnabled = true
                    showToast(result.message)
                }
            }
        }
    }
}
