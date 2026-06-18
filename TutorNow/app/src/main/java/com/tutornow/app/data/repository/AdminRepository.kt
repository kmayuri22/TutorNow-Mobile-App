package com.tutornow.app.data.repository

import com.tutornow.app.data.model.*
import com.tutornow.app.data.network.ApiService
import com.tutornow.app.util.Result

class AdminRepository(private val api: ApiService) {

    suspend fun getStats(): Result<AdminStats> {
        return try {
            val response = api.getAdminStats()
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to load stats: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getAllStudents(): Result<List<UserListItem>> {
        return try {
            val response = api.getAllStudents()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getAllTutors(): Result<List<UserListItem>> {
        return try {
            val response = api.getAllTutors()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getAllBookings(): Result<List<Booking>> {
        return try {
            val response = api.getAllBookings()
            if (response.isSuccessful) Result.Success(response.body() ?: emptyList())
            else Result.Error("Failed: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun deleteUser(userId: Int): Result<MessageResponse> {
        return try {
            val response = api.deleteUser(userId)
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to delete user: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }
}
