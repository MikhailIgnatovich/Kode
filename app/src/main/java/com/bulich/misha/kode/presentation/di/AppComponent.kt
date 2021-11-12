package com.bulich.misha.kode.presentation.di

import android.app.Application
import com.bulich.misha.kode.presentation.MainActivity
import com.bulich.misha.kode.presentation.fragments.HomeFragment
import com.bulich.misha.kode.presentation.fragments.SortBottomSheetFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, AppModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: Application): Builder

        fun build(): AppComponent
    }

//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        fun context(application: Application):Builder
//
//        fun build(): AppComponent
//    }

}