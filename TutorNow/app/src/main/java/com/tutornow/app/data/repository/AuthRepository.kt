package com.tutornow.app.data.repository

import com.tutornow.app.data.model.*
import com.tutornow.app.data.network.ApiService
import com.tutornow.app.util.Result

class AuthRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Login failed: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun register(name: String, email: String, password: String, role: String): Result<RegisterResponse> {
        return try {
            val response = api.register(RegisterRequest(name, email, password, role))
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Registration failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getProfile(): Result<UserProfile> {
        return try {
            val response = api.getProfile()
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to load profile: ${response.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun updateProfile(request: UpdateProfileRequest): Result<UserProfile> {
        return try {
            val response = api.updateProfile(request)
            if (response.isSuccessful) Result.Success(response.body()!!)
            else Result.Error("Failed to update profile: ${response.errorBody()?.string()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }
}
