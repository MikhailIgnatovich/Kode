package com.bulich.misha.kode.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulich.misha.kode.databinding.UserItemBinding
import com.bulich.misha.kode.domain.entity.UserEntity

class UserListAdapter : ListAdapter<UserEntity, UserListAdapter.UserListViewHolder>(UserItemDiffCallback()) {

    class UserListViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}