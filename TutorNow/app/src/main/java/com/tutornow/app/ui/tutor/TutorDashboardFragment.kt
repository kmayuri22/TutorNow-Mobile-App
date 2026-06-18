package com.tutornow.app.ui.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.FragmentTutorDashboardBinding
import com.tutornow.app.ui.student.BookingAdapter
import com.tutornow.app.util.Result
import com.tutornow.app.util.showToast

class TutorDashboardFragment : Fragment() {

    private var _binding: FragmentTutorDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TutorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTutorDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]

        val name = TutorNowApp.instance.sessionManager.getName() ?: "Tutor"
        binding.tvGreeting.text = "Welcome, $name! 🎓"

        val adapter = BookingAdapter { booking, status ->
            viewModel.updateBookingStatus(booking.id, status)
        }
        binding.rvPendingBookings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPendingBookings.adapter = adapter

        viewModel.pendingBookings.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvPendingCount.text = "${result.data.size} pending"
                    if (result.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        adapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.tvEmpty.text = result.message
                }
            }
        }

        viewModel.actionResult.observe(viewLifecycleOwner) { result ->
            if (result is Result.Success) context?.showToast("Booking updated!")
            else if (result is Result.Error) context?.showToast(result.message)
        }

        viewModel.loadPendingBookings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
