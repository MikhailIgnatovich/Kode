package com.bulich.misha.kode.presentation.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bulich.misha.kode.domain.useCase.GetListUserEntityUseCase
import com.bulich.misha.kode.presentation.viewmodels.HomeViewModel
import java.lang.RuntimeException
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val getListUserEntityUseCase: GetListUserEntityUseCase) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getListUserEntityUseCase) as T
        }
        throw RuntimeException("Not unknown $modelClass")
    }
}