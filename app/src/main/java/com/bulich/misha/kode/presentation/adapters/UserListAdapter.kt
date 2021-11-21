package com.bulich.misha.kode.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulich.misha.kode.R
import com.bulich.misha.kode.databinding.UserItemBinding
import com.bulich.misha.kode.databinding.UserItemSortDateBinding
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bumptech.glide.Glide
import java.lang.RuntimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UserListAdapter :
    ListAdapter<UserEntity, UserListAdapter.UserListViewHolder>(UserItemDiffCallback()) {

    var onUserEntityClickListener: ((UserEntity) -> Unit)? = null

    class UserListViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layout = when (viewType) {
            ALPHABET_SORT_VIEW_TYPE -> R.layout.user_item
            BIRTHDAY_SORT_VIEW_TYPE -> R.layout.user_item_sort_date
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onUserEntityClickListener?.invoke(userItem)
        }
        when (binding) {
            is UserItemBinding -> {
                with(binding) {
                    tvName.text = String.format(userItem.firstName + " " + userItem.lastName)
                    tvName.text =
                        String.format(userItem.firstName.replaceFirstChar { it.uppercase() }
                                + " " + userItem.lastName.replaceFirstChar { it.uppercase() })
                    tvPosition.text = userItem.position
                    tvTag.text = userItem.userTag
                    Glide.with(ivPhoto.context)
                        .load(userItem.avatarUrl)
                        .placeholder(R.drawable.rectangle)
                        .circleCrop()
                        .into(ivPhoto)
                }

            }

            is UserItemSortDateBinding -> {
                with(binding) {
                    tvName.text = String.format(userItem.firstName + " " + userItem.lastName)
                    tvName.text =
                        String.format(userItem.firstName.replaceFirstChar { it.uppercase() }
                                + " " + userItem.lastName.replaceFirstChar { it.uppercase() })
                    tvPosition.text = userItem.position
                    tvTag.text = userItem.userTag
                    tvDateBirthday.text = parseDateBirthday(userItem.birthday)
                    Glide.with(ivPhoto.context)
                        .load(userItem.avatarUrl)
                        .placeholder(R.drawable.rectangle)
                        .circleCrop()
                        .into(ivPhoto)
                }

            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.sortMode) {
            BIRTHDAY_SORT_VIEW_TYPE
        } else {
            ALPHABET_SORT_VIEW_TYPE
        }
    }

    private fun parseDateBirthday(date: LocalDate): String {

        return DateTimeFormatter.ofPattern("d MMM", Locale("ru"))
            .format(date).dropLast(1)
    }

    companion object {
        private const val ALPHABET_SORT_VIEW_TYPE = 100
        private const val BIRTHDAY_SORT_VIEW_TYPE = 101
    }
}