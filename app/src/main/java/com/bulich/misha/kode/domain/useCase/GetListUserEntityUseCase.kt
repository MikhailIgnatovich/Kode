package com.bulich.misha.kode.domain.useCase

import com.bulich.misha.kode.data.models.Items
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.repository.UsersRepository
import com.bulich.misha.kode.domain.util.Resource
import javax.inject.Inject

class GetListUserEntityUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun getListUserEntity(): Resource<List<UserEntity>> {
        return usersRepository.getListUserEntity()
    }
}