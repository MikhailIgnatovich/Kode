package com.bulich.misha.kode.presentation

import android.app.Application
import android.content.Context
import com.bulich.misha.kode.presentation.di.AppComponent
import com.bulich.misha.kode.presentation.di.DaggerAppComponent

class MyApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is MyApp -> appComponent
        else -> applicationContext.appComponent
    }

