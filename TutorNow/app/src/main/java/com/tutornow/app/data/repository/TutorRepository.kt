package com.tutornow.app.data.repository

import com.tutornow.app.data.model.*
import com.tutornow.app.data.network.ApiService
import com.tutornow.app.util.Result

class TutorRepository(private val api: ApiService) {

    suspend fun searchTutors(
        subject: String? = null,
        location: String? = null,
        minRate: Double? = null,
        maxRate: Double? = null,
        minExperience: Int? = null,
        minRating: Double? = null
    ): Result<List<Tutor>> {
        return try {
            val response = api.searchTutors(subject, location, minRate, maxRate, minExperience, minRating)
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Search failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getTutorDetail(tutorId: Int): Result<Tutor> {
        return try {
            val response = api.getTutorDetail(tutorId)
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to load tutor: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getTutorAvailability(tutorId: Int): Result<List<Availability>> {
        return try {
            val response = api.getTutorAvailability(tutorId)
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed to load availability: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }
}
