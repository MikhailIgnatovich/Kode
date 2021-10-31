package com.bulich.misha.kode.data.mappers

import com.bulich.misha.kode.data.models.User
import com.bulich.misha.kode.domain.entity.UserEntity
import javax.inject.Inject

class UserMapper @Inject constructor() {

   private fun userToUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            avatarUrl = user.avatarUrl,
            firstName = user.firstName,
            lastName = user.lastName,
            userTag = user.userTag,
            department = user.department,
            position = user.position,
            birthday = user.birthday,
            phone = user.phone
        )
    }

    fun userListToUserEntityList(userList: List<User>): List<UserEntity> {
        val list = mutableListOf<UserEntity>()
        for (user in userList){
            list.add(userToUserEntity(user))
        }
        return list.toList()
    }
}