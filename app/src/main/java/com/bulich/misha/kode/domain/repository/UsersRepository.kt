package com.bulich.misha.kode.domain.repository

import com.bulich.misha.kode.domain.entity.UserEntity

interface UsersRepository {

    suspend fun getListUserEntity(): List<UserEntity>
}