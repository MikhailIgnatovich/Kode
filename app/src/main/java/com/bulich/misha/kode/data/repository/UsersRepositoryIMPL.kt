package com.bulich.misha.kode.data.repository

import com.bulich.misha.kode.data.ApiService
import com.bulich.misha.kode.data.mappers.UserMapper
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryIMPL @Inject constructor(private val apiService: ApiService): UsersRepository {
    @Inject
    lateinit var userMapper: UserMapper
    override suspend fun getListUserEntity(): List<UserEntity> {
        val response = apiService.getUsersFromServer()
        var userEntityList = emptyList<UserEntity>()
        if (response.isSuccessful){
            val body = response.body()!!
            val list = body.items
            userEntityList = userMapper.userListToUserEntityList(list)
        }
        return userEntityList
    }
}