package com.tutornow.app.ui.student

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutornow.app.databinding.FragmentSearchBinding
import com.tutornow.app.ui.tutor.TutorDetailActivity
import com.tutornow.app.util.Result

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StudentViewModel
    private lateinit var tutorAdapter: TutorListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        tutorAdapter = TutorListAdapter { tutor ->
            startActivity(Intent(requireContext(), TutorDetailActivity::class.java).apply {
                putExtra("tutor_id", tutor.id)
                putExtra("tutor_name", tutor.name)
            })
        }
        binding.rvTutors.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTutors.adapter = tutorAdapter

        binding.btnSearch.setOnClickListener {
            val subject = binding.etSubject.text?.toString()?.takeIf { it.isNotBlank() }
            val location = binding.etLocation.text?.toString()?.takeIf { it.isNotBlank() }
            viewModel.loadTutors(subject = subject, location = location)
        }

        viewModel.tutors.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvTutors.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.rvTutors.visibility = View.GONE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        binding.rvTutors.visibility = View.VISIBLE
                        tutorAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
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
