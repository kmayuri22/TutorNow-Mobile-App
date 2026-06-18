package com.tutornow.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.FragmentProfileBinding
import com.tutornow.app.ui.main.MainActivity
import com.tutornow.app.util.Result
import com.tutornow.app.util.showToast

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val session = TutorNowApp.instance.sessionManager
        binding.tvName.text = session.getName() ?: "User"
        binding.tvEmail.text = session.getEmail() ?: ""
        binding.tvRole.text = session.getRole() ?: "Student"

        val initials = (session.getName() ?: "U").split(" ").take(2).map { it.first().uppercaseChar() }.joinToString("")
        binding.tvInitials.text = initials

        viewModel.profile.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.tvName.text = result.data.name
                    binding.tvEmail.text = result.data.email
                    binding.tvRole.text = result.data.role
                    result.data.bio?.let { binding.tvBio.text = it }
                    result.data.location?.let { binding.tvLocation.text = "📍 $it" }
                }
                is Result.Error -> context?.showToast(result.message)
                else -> {}
            }
        }

        binding.btnLogout.setOnClickListener {
            (activity as? MainActivity)?.logout()
        }

        viewModel.loadProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
