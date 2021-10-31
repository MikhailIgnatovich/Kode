package com.bulich.misha.kode.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulich.misha.kode.databinding.UserItemBinding
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bumptech.glide.Glide

class UserListAdapter : ListAdapter<UserEntity, UserListAdapter.UserListViewHolder>(UserItemDiffCallback()) {

    class UserListViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(layout, parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userItem = getItem(position)
        val binding = holder.binding
        binding.tvName.text = String.format(userItem.firstName + " " + userItem.lastName)
        binding.tvPosition.text = userItem.position
        binding.tvTag.text = userItem.userTag
        Glide.with(binding.ivPhoto.context)
            .load(userItem.avatarUrl)
            .circleCrop()
            .into(binding.ivPhoto)
    }
}