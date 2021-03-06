package com.bulich.misha.kode.data.mappers

import android.app.Application
import com.bulich.misha.kode.data.models.User
import com.bulich.misha.kode.domain.entity.UserEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UserMapper @Inject constructor(private val application: Application) {

    private fun userToUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            avatarUrl = user.avatarUrl,
            firstName = user.firstName.lowercase(),
            lastName = user.lastName.lowercase(),
            userTag = user.userTag.lowercase(),
            department = user.department,
            position = user.position,
            birthday = setupFormatDate(user.birthday),
            phone = user.phone,
            sortMode = false
        )
    }

    fun userListToUserEntityList(userList: List<User>): List<UserEntity> {
        val list = mutableListOf<UserEntity>()
        for (user in userList) {
            list.add(userToUserEntity(user))
        }
        return list.toList()
    }

    private fun setupFormatDate(string: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(string, formatter)
    }

}