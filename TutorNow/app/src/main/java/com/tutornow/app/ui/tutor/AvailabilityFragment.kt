package com.tutornow.app.ui.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tutornow.app.databinding.FragmentAvailabilityBinding

class AvailabilityFragment : Fragment() {

    private var _binding: FragmentAvailabilityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAvailabilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        binding.tvTitle.text = "⏰ Set Availability"
        binding.tvInfo.text = "Manage your available time slots for students to book sessions.\n\nThis feature allows you to set your active hours for each day of the week."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
