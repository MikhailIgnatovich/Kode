package com.bulich.misha.kode.presentation.di

import com.bulich.misha.kode.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}