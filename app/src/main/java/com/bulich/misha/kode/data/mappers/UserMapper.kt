package com.bulich.misha.kode.data.mappers

import com.bulich.misha.kode.data.models.User
import com.bulich.misha.kode.domain.entity.UserEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

class UserMapper @Inject constructor() {

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
        for (user in userList){
            list.add(userToUserEntity(user))
        }
        return list.toList()
    }

    private fun setupFormatDate(string: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(string, formatter)
//        val date1 = date.format()
//        return date
//        val date = formatter.parse(string)
//        val date1 = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru")).format(date)


    }
//    val firstDate = "08/08/2019"
//    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//    val date = formatter.parse(firstDate)
//    val desiredFormat = DateTimeFormatter.ofPattern("dd, MMM yyyy").format(date)
}