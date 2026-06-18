package com.tutornow.app.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutornow.app.data.model.Booking
import com.tutornow.app.data.model.BookingRequest
import com.tutornow.app.data.network.RetrofitClient
import com.tutornow.app.data.repository.BookingRepository
import com.tutornow.app.util.Result
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    private val repo = BookingRepository(RetrofitClient.apiService)

    private val _bookingResult = MutableLiveData<Result<Booking>>()
    val bookingResult: LiveData<Result<Booking>> = _bookingResult

    fun createBooking(tutorId: Int, subject: String, bookingType: String, scheduledAt: String, notes: String?) {
        viewModelScope.launch {
            _bookingResult.value = Result.Loading
            _bookingResult.value = repo.createBooking(
                BookingRequest(tutorId, subject, bookingType, scheduledAt, notes = notes)
            )
        }
    }
}
