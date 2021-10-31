package com.bulich.misha.kode.presentation.di

import com.bulich.misha.kode.presentation.MainActivity
import com.bulich.misha.kode.presentation.fragments.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
}