package com.bulich.misha.kode.domain.useCase

import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.repository.UsersRepository

class GetListUserEntityUseCase(private val usersRepository: UsersRepository) {

    suspend fun getListUserEntity(): List<UserEntity> {
        return usersRepository.getListUserEntity()
    }
}