package com.tutornow.app.ui.student

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.FragmentHomeBinding
import com.tutornow.app.ui.tutor.TutorDetailActivity
import com.tutornow.app.util.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StudentViewModel
    private lateinit var tutorAdapter: TutorListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        val userName = TutorNowApp.instance.sessionManager.getName() ?: "Student"
        binding.tvGreeting.text = "Hey, $userName! 👋"

        tutorAdapter = TutorListAdapter { tutor ->
            startActivity(Intent(requireContext(), TutorDetailActivity::class.java).apply {
                putExtra("tutor_id", tutor.id)
                putExtra("tutor_name", tutor.name)
            })
        }
        binding.rvFeaturedTutors.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFeaturedTutors.adapter = tutorAdapter

        viewModel.tutors.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.rvFeaturedTutors.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.shimmerLayout.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.rvFeaturedTutors.visibility = View.GONE
                    } else {
                        binding.rvFeaturedTutors.visibility = View.VISIBLE
                        binding.tvEmpty.visibility = View.GONE
                        tutorAdapter.submitList(result.data.take(5))
                    }
                }
                is Result.Error -> {
                    binding.shimmerLayout.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.tvEmpty.text = result.message
                }
            }
        }

        viewModel.loadTutors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
