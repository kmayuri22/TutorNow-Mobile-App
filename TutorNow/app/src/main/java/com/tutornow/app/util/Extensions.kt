package com.tutornow.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tutornow.app.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun ImageView.loadAvatar(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_profile)
        .error(R.drawable.ic_profile)
        .circleCrop()
        .into(this)
}

fun formatDate(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return "N/A"
    return try {
        val inputFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFmt = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = inputFmt.parse(isoDate)
        date?.let { outputFmt.format(it) } ?: isoDate
    } catch (e: Exception) {
        isoDate
    }
}

fun formatDateShort(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return "N/A"
    return try {
        val inputFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFmt = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        val date = inputFmt.parse(isoDate)
        date?.let { outputFmt.format(it) } ?: isoDate
    } catch (e: Exception) {
        isoDate
    }
}

fun getStatusColor(status: String): Int {
    return when (status.lowercase()) {
        "pending" -> android.R.color.holo_orange_dark
        "confirmed" -> android.R.color.holo_green_dark
        "rejected" -> android.R.color.holo_red_dark
        "completed" -> android.R.color.holo_blue_dark
        else -> android.R.color.darker_gray
    }
}
