package com.tutornow.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.local.SessionManager
import com.tutornow.app.data.model.LoginResponse
import com.tutornow.app.data.model.RegisterResponse
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.AuthRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class AuthViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.apiService)

    private val _loginSuccess = MutableLiveData<LoginResponse?>()
    val loginSuccess: LiveData<LoginResponse?> = _loginSuccess

    private val _registerSuccess = MutableLiveData<RegisterResponse?>()
    val registerSuccess: LiveData<RegisterResponse?> = _registerSuccess

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Please fill in all fields"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.login(email, password)) {
                is Result.Success -> {
                    sessionManager.saveSession(
                        result.data.accessToken,
                        result.data.role,
                        result.data.name,
                        result.data.email,
                        result.data.userId
                    )
                    _loginSuccess.value = result.data
                }
                is Result.Error -> _error.value = result.message
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun register(name: String, email: String, password: String, confirmPass: String, role: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _error.value = "Please fill in all fields"
            return
        }
        if (password != confirmPass) {
            _error.value = "Passwords do not match"
            return
        }
        if (password.length < 6) {
            _error.value = "Password must be at least 6 characters"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.register(name, email, password, role)) {
                is Result.Success -> _registerSuccess.value = result.data
                is Result.Error -> _error.value = result.message
                else -> {}
            }
            _isLoading.value = false
        }
    }
}

class AuthViewModelFactory(private val sessionManager: SessionManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(sessionManager) as T
    }
}
