package com.tutornow.app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.UserProfile
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.AuthRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repo = AuthRepository(RetrofitClient.apiService)

    private val _profile = MutableLiveData<Result<UserProfile>>()
    val profile: LiveData<Result<UserProfile>> = _profile

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = repo.getProfile()
        }
    }
}
