package com.bulich.misha.kode.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.useCase.GetListUserEntityUseCase
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class HomeViewModel(private val getListUserEntityUseCase: GetListUserEntityUseCase) : ViewModel() {

    private var _userList = MutableLiveData<List<UserEntity>>()
    val userList: LiveData<List<UserEntity>>
        get() = _userList

    private var _userFilterList = MutableLiveData<List<UserEntity>>()
    val userFilterList: LiveData<List<UserEntity>>
        get() = _userFilterList

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            _userList.value = getListUserEntityUseCase.getListUserEntity()
            _userFilterList.value = _userList.value
        }
    }

    fun filterNameList(name: String) {
        viewModelScope.launch {
            _userFilterList.value = _userList.value

            val firstNameFilterList = _userList.value?.filter { it.firstName.startsWith(name.lowercase()) }
            val lastNameFilterList = _userList.value?.filter { it.lastName.startsWith(name.lowercase()) }
            val userTagFilterList = when(true) {
                name.length in 0..2  -> {_userList.value?.filter { it.userTag.startsWith(name.lowercase()) }}
                else -> {_userList.value?.filter { it.userTag.startsWith(name.lowercase().subSequence(0, 2)) }}
            }

            val list = HashSet<UserEntity>()
            list.addAll(firstNameFilterList!!)
            list.addAll(lastNameFilterList!!)
            list.addAll(userTagFilterList!!)

            _userFilterList.value = list.toList()
        }
    }

}