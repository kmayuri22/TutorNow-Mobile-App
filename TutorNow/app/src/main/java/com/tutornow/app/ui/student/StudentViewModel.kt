package com.tutornow.app.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.Booking
import com.tutornow.app.data.model.Tutor
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.BookingRepository
import com.tutornow.app.data.repository.TutorRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    private val tutorRepo = TutorRepository(RetrofitClient.apiService)
    private val bookingRepo = BookingRepository(RetrofitClient.apiService)

    private val _tutors = MutableLiveData<Result<List<Tutor>>>()
    val tutors: LiveData<Result<List<Tutor>>> = _tutors

    private val _bookings = MutableLiveData<Result<List<Booking>>>()
    val bookings: LiveData<Result<List<Booking>>> = _bookings

    fun loadTutors(
        subject: String? = null,
        location: String? = null,
        minExperience: Int? = null,
        minRating: Double? = null
    ) {
        viewModelScope.launch {
            _tutors.value = Result.Loading
            _tutors.value = tutorRepo.searchTutors(
                subject = subject,
                location = location,
                minExperience = minExperience,
                minRating = minRating
            )
        }
    }

    fun loadBookings() {
        viewModelScope.launch {
            _bookings.value = Result.Loading
            _bookings.value = bookingRepo.getMyBookings()
        }
    }
}
