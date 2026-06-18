package com.tutornow.app.data.model

import com.google.gson.annotations.SerializedName

// ─── Auth ───────────────────────────────────────────────────────────────────

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    val role: String,
    val name: String,
    val email: String,
    @SerializedName("user_id") val userId: Int
)

data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("created_at") val createdAt: String
)

// ─── User / Profile ──────────────────────────────────────────────────────────

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("created_at") val createdAt: String?,
    // Tutor-specific
    val bio: String? = null,
    val subjects: List<String>? = null,
    @SerializedName("hourly_rate") val hourlyRate: Double? = null,
    val location: String? = null,
    val experience: Int? = null,
    val rating: Double? = null,
    @SerializedName("total_reviews") val totalReviews: Int? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("is_available") val isAvailable: Boolean? = null,
    @SerializedName("classes_taught") val classesTaught: List<String>? = null
)

data class UpdateProfileRequest(
    val name: String? = null,
    val bio: String? = null,
    val subjects: List<String>? = null,
    @SerializedName("hourly_rate") val hourlyRate: Double? = null,
    val location: String? = null,
    val experience: Int? = null
)

// ─── Tutor ───────────────────────────────────────────────────────────────────

data class Tutor(
    val id: Int,
    val name: String,
    val email: String,
    val bio: String?,
    val subjects: List<String>?,
    @SerializedName("hourly_rate") val hourlyRate: Double?,
    val location: String?,
    val experience: Int?,
    val rating: Double?,
    @SerializedName("total_reviews") val totalReviews: Int?,
    @SerializedName("profile_image") val profileImage: String?,
    @SerializedName("is_available") val isAvailable: Boolean?,
    @SerializedName("classes_taught") val classesTaught: List<String>?
)

data class TutorSearchResponse(
    val tutors: List<Tutor>,
    val total: Int
)

// ─── Booking ─────────────────────────────────────────────────────────────────

data class BookingRequest(
    @SerializedName("tutor_id") val tutorId: Int,
    val subject: String,
    @SerializedName("booking_type") val bookingType: String,
    @SerializedName("scheduled_at") val scheduledAt: String,
    val duration: Int = 60,
    val notes: String? = null
)

data class Booking(
    val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("tutor_id") val tutorId: Int,
    @SerializedName("student_name") val studentName: String?,
    @SerializedName("tutor_name") val tutorName: String?,
    val subject: String,
    @SerializedName("booking_type") val bookingType: String,
    val status: String,
    @SerializedName("scheduled_at") val scheduledAt: String,
    val duration: Int?,
    val notes: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class BookingStatusUpdate(
    val status: String
)

// ─── Availability ────────────────────────────────────────────────────────────

data class Availability(
    val id: Int?,
    @SerializedName("tutor_id") val tutorId: Int,
    @SerializedName("day_of_week") val dayOfWeek: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("is_active") val isActive: Boolean
)

data class AvailabilityRequest(
    @SerializedName("day_of_week") val dayOfWeek: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("is_active") val isActive: Boolean = true
)

// ─── Admin ───────────────────────────────────────────────────────────────────

data class AdminStats(
    @SerializedName("total_students") val totalStudents: Int,
    @SerializedName("total_tutors") val totalTutors: Int,
    @SerializedName("total_bookings") val totalBookings: Int,
    @SerializedName("pending_bookings") val pendingBookings: Int,
    @SerializedName("confirmed_bookings") val confirmedBookings: Int,
    @SerializedName("completed_bookings") val completedBookings: Int,
    @SerializedName("rejected_bookings") val rejectedBookings: Int
)

data class UserListItem(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("is_active") val isActive: Boolean? = true
)

// ─── Generic ─────────────────────────────────────────────────────────────────

data class MessageResponse(
    val message: String
)

data class ApiError(
    val detail: String
)
