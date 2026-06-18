package com.tutornow.app.ui.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tutornow.app.data.model.Tutor
import com.tutornow.app.databinding.ItemTutorCardBinding

class TutorListAdapter(
    private val onClick: (Tutor) -> Unit
) : ListAdapter<Tutor, TutorListAdapter.TutorViewHolder>(TutorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val binding = ItemTutorCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TutorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TutorViewHolder(
        private val binding: ItemTutorCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tutor: Tutor) {
            binding.tvName.text = tutor.name
            binding.tvSubjects.text = tutor.subjects?.joinToString(", ") ?: "General"
            binding.tvRate.text = "₹${tutor.hourlyRate?.toInt() ?: 0}/hr"
            binding.tvRating.text = "⭐ ${String.format("%.1f", tutor.rating ?: 0.0)}"
            binding.tvExperience.text = "${tutor.experience ?: 0} yrs exp"
            binding.tvLocation.text = tutor.location ?: "Online"

            val initials = tutor.name.split(" ")
                .take(2)
                .map { it.firstOrNull()?.uppercaseChar() ?: ' ' }
                .joinToString("")
            binding.tvInitials.text = initials

            binding.root.setOnClickListener { onClick(tutor) }
        }
    }

    class TutorDiffCallback : DiffUtil.ItemCallback<Tutor>() {
        override fun areItemsTheSame(oldItem: Tutor, newItem: Tutor) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Tutor, newItem: Tutor) = oldItem == newItem
    }
}
