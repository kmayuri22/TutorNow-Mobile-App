package com.tutornow.app.ui.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.Booking
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.BookingRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class TutorViewModel : ViewModel() {

    private val bookingRepo = BookingRepository(RetrofitClient.apiService)

    private val _pendingBookings = MutableLiveData<Result<List<Booking>>>()
    val pendingBookings: LiveData<Result<List<Booking>>> = _pendingBookings

    private val _upcomingBookings = MutableLiveData<Result<List<Booking>>>()
    val upcomingBookings: LiveData<Result<List<Booking>>> = _upcomingBookings

    private val _actionResult = MutableLiveData<Result<Booking>>()
    val actionResult: LiveData<Result<Booking>> = _actionResult

    fun loadPendingBookings() {
        viewModelScope.launch {
            _pendingBookings.value = Result.Loading
            _pendingBookings.value = bookingRepo.getTutorPendingBookings()
        }
    }

    fun loadUpcomingBookings() {
        viewModelScope.launch {
            _upcomingBookings.value = Result.Loading
            _upcomingBookings.value = bookingRepo.getTutorUpcomingBookings()
        }
    }

    fun updateBookingStatus(bookingId: Int, status: String) {
        viewModelScope.launch {
            _actionResult.value = bookingRepo.updateBookingStatus(bookingId, status)
            loadPendingBookings()
            loadUpcomingBookings()
        }
    }
}
