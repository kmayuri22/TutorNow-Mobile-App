package com.tutornow.app.ui.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tutornow.app.R
import com.tutornow.app.data.model.Booking
import com.tutornow.app.databinding.ItemBookingCardBinding
import com.tutornow.app.util.formatDateShort

class BookingAdapter(
    private val onAction: ((Booking, String) -> Unit)? = null
) : ListAdapter<Booking, BookingAdapter.BookingViewHolder>(BookingDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookingViewHolder(private val binding: ItemBookingCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(booking: Booking) {
            binding.tvSubject.text = booking.subject
            binding.tvTutorName.text = booking.tutorName ?: booking.studentName ?: "User #${booking.tutorId}"
            binding.tvDate.text = formatDateShort(booking.scheduledAt)
            binding.tvType.text = if (booking.bookingType == "online") "🖥 Online" else "📍 In-Person"

            val statusColor = when (booking.status.lowercase()) {
                "pending" -> R.color.status_pending
                "confirmed" -> R.color.status_confirmed
                "rejected" -> R.color.status_rejected
                "completed" -> R.color.status_completed
                else -> R.color.text_secondary
            }
            binding.tvStatus.text = booking.status.uppercase()
            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, statusColor))

            if (onAction != null && booking.status.lowercase() == "pending") {
                binding.btnAccept.visibility = android.view.View.VISIBLE
                binding.btnReject.visibility = android.view.View.VISIBLE
                binding.btnAccept.setOnClickListener { onAction.invoke(booking, "Confirmed") }
                binding.btnReject.setOnClickListener { onAction.invoke(booking, "Rejected") }
            } else {
                binding.btnAccept.visibility = android.view.View.GONE
                binding.btnReject.visibility = android.view.View.GONE
            }
        }
    }

    class BookingDiff : DiffUtil.ItemCallback<Booking>() {
        override fun areItemsTheSame(old: Booking, new: Booking) = old.id == new.id
        override fun areContentsTheSame(old: Booking, new: Booking) = old == new
    }
}
