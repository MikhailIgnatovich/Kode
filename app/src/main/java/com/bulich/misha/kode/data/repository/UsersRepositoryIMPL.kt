package com.bulich.misha.kode.data.repository

import com.bulich.misha.kode.data.ApiService
import com.bulich.misha.kode.data.mappers.UserMapper
import com.bulich.misha.kode.data.models.Items
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.repository.UsersRepository
import com.bulich.misha.kode.domain.util.Resource

import javax.inject.Inject
import kotlin.Exception

class UsersRepositoryIMPL @Inject constructor(private val apiService: ApiService) :
    UsersRepository {
    @Inject
    lateinit var userMapper: UserMapper
    override suspend fun getListUserEntity(): Resource<List<UserEntity>> {
        return try {


            val response = apiService.getUsersFromServer()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                val listUserEntity = userMapper.userListToUserEntityList(result.items)
                Resource.Success(listUserEntity)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}