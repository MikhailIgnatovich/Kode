package com.bulich.misha.kode.domain.repository

import androidx.lifecycle.LiveData
import com.bulich.misha.kode.data.models.Items
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.util.Resource

interface UsersRepository {

    suspend fun getListUserEntity(): Resource<List<UserEntity>>
    suspend fun connectionInternetStatus(): Boolean
}