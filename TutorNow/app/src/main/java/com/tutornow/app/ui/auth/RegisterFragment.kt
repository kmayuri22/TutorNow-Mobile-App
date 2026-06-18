package com.tutornow.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tutornow.app.R
import com.tutornow.app.TutorNowApp
import com.tutornow.app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionManager = TutorNowApp.instance.sessionManager
        viewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(sessionManager)
        )[AuthViewModel::class.java]

        val roles = arrayOf("Student", "Tutor")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roles)
        binding.roleDropdown.setAdapter(adapter)
        binding.roleDropdown.setText("Student", false)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text?.toString() ?: ""
            val email = binding.etEmail.text?.toString() ?: ""
            val password = binding.etPassword.text?.toString() ?: ""
            val confirmPass = binding.etConfirmPassword.text?.toString() ?: ""
            val role = binding.roleDropdown.text?.toString() ?: "Student"
            viewModel.register(name, email, password, confirmPass, role)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
