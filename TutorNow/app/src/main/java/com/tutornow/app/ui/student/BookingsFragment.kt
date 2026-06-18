package com.tutornow.app.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutornow.app.databinding.FragmentBookingsBinding
import com.tutornow.app.util.Result

class BookingsFragment : Fragment() {

    private var _binding: FragmentBookingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StudentViewModel
    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        bookingAdapter = BookingAdapter()
        binding.rvBookings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookings.adapter = bookingAdapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadBookings()
        }

        viewModel.bookings.observe(viewLifecycleOwner) { result ->
            binding.swipeRefresh.isRefreshing = false
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.rvBookings.visibility = View.GONE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        binding.rvBookings.visibility = View.VISIBLE
                        bookingAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.tvEmpty.text = result.message
                }
            }
        }

        viewModel.loadBookings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
