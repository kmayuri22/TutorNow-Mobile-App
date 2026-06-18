package com.tutornow.app.data.network

import com.tutornow.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ─── Auth ────────────────────────────────────────────────────────────────

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/auth/profile")
    suspend fun getProfile(): Response<UserProfile>

    @PUT("api/auth/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UserProfile>

    // ─── Tutors ──────────────────────────────────────────────────────────────

    @GET("api/tutors")
    suspend fun searchTutors(
        @Query("subject") subject: String? = null,
        @Query("location") location: String? = null,
        @Query("min_rate") minRate: Double? = null,
        @Query("max_rate") maxRate: Double? = null,
        @Query("min_experience") minExperience: Int? = null,
        @Query("min_rating") minRating: Double? = null,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<List<Tutor>>

    @GET("api/tutors/{tutor_id}")
    suspend fun getTutorDetail(@Path("tutor_id") tutorId: Int): Response<Tutor>

    @GET("api/tutors/{tutor_id}/availability")
    suspend fun getTutorAvailability(@Path("tutor_id") tutorId: Int): Response<List<Availability>>

    // ─── Bookings ────────────────────────────────────────────────────────────

    @POST("api/bookings")
    suspend fun createBooking(@Body request: BookingRequest): Response<Booking>

    @GET("api/bookings")
    suspend fun getMyBookings(): Response<List<Booking>>

    @GET("api/bookings/{booking_id}")
    suspend fun getBookingDetail(@Path("booking_id") bookingId: Int): Response<Booking>

    @PUT("api/bookings/{booking_id}/status")
    suspend fun updateBookingStatus(
        @Path("booking_id") bookingId: Int,
        @Body request: BookingStatusUpdate
    ): Response<Booking>

    @GET("api/bookings/tutor/pending")
    suspend fun getTutorPendingBookings(): Response<List<Booking>>

    @GET("api/bookings/tutor/upcoming")
    suspend fun getTutorUpcomingBookings(): Response<List<Booking>>

    // ─── Availability ────────────────────────────────────────────────────────

    @POST("api/availability")
    suspend fun addAvailability(@Body request: AvailabilityRequest): Response<Availability>

    @DELETE("api/availability/{slot_id}")
    suspend fun deleteAvailability(@Path("slot_id") slotId: Int): Response<MessageResponse>

    // ─── Admin ───────────────────────────────────────────────────────────────

    @GET("api/admin/stats")
    suspend fun getAdminStats(): Response<AdminStats>

    @GET("api/admin/students")
    suspend fun getAllStudents(): Response<List<UserListItem>>

    @GET("api/admin/tutors")
    suspend fun getAllTutors(): Response<List<UserListItem>>

    @GET("api/admin/bookings")
    suspend fun getAllBookings(): Response<List<Booking>>

    @DELETE("api/admin/users/{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: Int): Response<MessageResponse>
}
