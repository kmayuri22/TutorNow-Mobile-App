package com.tutornow.app.ui.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.Tutor
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.TutorRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class TutorDetailViewModel : ViewModel() {
    private val repo = TutorRepository(RetrofitClient.apiService)

    private val _tutor = MutableLiveData<Result<Tutor>>()
    val tutor: LiveData<Result<Tutor>> = _tutor

    fun loadTutor(tutorId: Int) {
        viewModelScope.launch {
            _tutor.value = Result.Loading
            _tutor.value = repo.getTutorDetail(tutorId)
        }
    }
}
