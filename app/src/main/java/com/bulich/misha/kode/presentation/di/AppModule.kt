package com.bulich.misha.kode.presentation.di

import com.bulich.misha.kode.data.repository.UsersRepositoryIMPL
import com.bulich.misha.kode.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideUsersRepositoryIMPLtoUsersRepository(usersRepositoryIMPL: UsersRepositoryIMPL) : UsersRepository {
        return usersRepositoryIMPL
    }
}