package com.bulich.misha.kode.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.useCase.GetListUserEntityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val getListUserEntityUseCase: GetListUserEntityUseCase) : ViewModel() {

    private var _userList = MutableLiveData<List<UserEntity>>()
    val userList: LiveData<List<UserEntity>>
        get() = _userList

    init {
        getUserList()
    }

    fun getUserList() {
        viewModelScope.launch {
            _userList.value = getListUserEntityUseCase.getListUserEntity()
        }
    }

}