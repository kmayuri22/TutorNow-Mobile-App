package com.tutornow.app.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.AdminStats
import com.tutornow.app.data.model.UserListItem
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.AdminRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repo = AdminRepository(RetrofitClient.apiService)

    private val _stats = MutableLiveData<Result<AdminStats>>()
    val stats: LiveData<Result<AdminStats>> = _stats

    private val _users = MutableLiveData<Result<List<UserListItem>>>()
    val users: LiveData<Result<List<UserListItem>>> = _users

    fun loadStats() {
        viewModelScope.launch {
            _stats.value = repo.getStats()
        }
    }

    fun loadStudents() {
        viewModelScope.launch {
            _users.value = Result.Loading
            _users.value = repo.getAllStudents()
        }
    }

    fun loadTutors() {
        viewModelScope.launch {
            _users.value = Result.Loading
            _users.value = repo.getAllTutors()
        }
    }
}
