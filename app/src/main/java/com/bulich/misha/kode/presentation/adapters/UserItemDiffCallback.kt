package com.bulich.misha.kode.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bulich.misha.kode.domain.entity.UserEntity

class UserItemDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }
}