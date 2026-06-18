package com.tutornow.app.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tutornow.app.data.model.UserListItem
import com.tutornow.app.databinding.ItemUserCardBinding

class UserListAdapter(
    private val onClick: (UserListItem) -> Unit
) : ListAdapter<UserListItem, UserListAdapter.UserViewHolder>(UserDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(getItem(position))

    inner class UserViewHolder(private val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserListItem) {
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email
            binding.tvRole.text = user.role
            val initials = user.name.split(" ").take(2).map { it.first().uppercaseChar() }.joinToString("")
            binding.tvInitials.text = initials
            binding.root.setOnClickListener { onClick(user) }
        }
    }

    class UserDiff : DiffUtil.ItemCallback<UserListItem>() {
        override fun areItemsTheSame(old: UserListItem, new: UserListItem) = old.id == new.id
        override fun areContentsTheSame(old: UserListItem, new: UserListItem) = old == new
    }
}
