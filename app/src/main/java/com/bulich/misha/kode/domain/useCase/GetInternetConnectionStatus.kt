package com.bulich.misha.kode.domain.useCase

import com.bulich.misha.kode.domain.repository.UsersRepository
import javax.inject.Inject

class GetInternetConnectionStatus @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun connectionInternetStatus(): Boolean {
        return usersRepository.connectionInternetStatus()
    }
}