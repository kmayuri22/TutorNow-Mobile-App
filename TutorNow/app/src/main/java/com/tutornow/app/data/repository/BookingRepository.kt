package com.tutornow.app.data.repository

import com.tutornow.app.data.model.*
import com.tutornow.app.data.network.ApiService
import com.tutornow.app.util.Result

class BookingRepository(private val api: ApiService) {

    suspend fun createBooking(request: BookingRequest): Result<Booking> {
        return try {
            val response = api.createBooking(request)
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Booking failed: ${response.errorBody()?.string()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getMyBookings(): Result<List<Booking>> {
        return try {
            val response = api.getMyBookings()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed to load bookings: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun updateBookingStatus(bookingId: Int, status: String): Result<Booking> {
        return try {
            val response = api.updateBookingStatus(bookingId, BookingStatusUpdate(status))
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to update status: ${response.errorBody()?.string()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getTutorPendingBookings(): Result<List<Booking>> {
        return try {
            val response = api.getTutorPendingBookings()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getTutorUpcomingBookings(): Result<List<Booking>> {
        return try {
            val response = api.getTutorUpcomingBookings()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }
}
