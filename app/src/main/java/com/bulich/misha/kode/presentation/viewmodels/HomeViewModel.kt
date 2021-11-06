package com.bulich.misha.kode.presentation.viewmodels

import androidx.lifecycle.*
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.useCase.GetListUserEntityUseCase
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.text.FieldPosition

class HomeViewModel(private val getListUserEntityUseCase: GetListUserEntityUseCase) : ViewModel() {

    private var _userList = MutableLiveData<List<UserEntity>>()
    val userList: LiveData<List<UserEntity>>
        get() = _userList

    private var _userFilterList = MutableLiveData<List<UserEntity>>()
    val userFilterList: LiveData<List<UserEntity>>
        get() = _userFilterList

    private var _checkList = MutableLiveData<Boolean>()
    val checkList: LiveData<Boolean>
        get() = _checkList


    init {
        getUserList()


    }

     fun getUserList() {
        viewModelScope.launch {
            _userList.value = getListUserEntityUseCase.getListUserEntity()
            _userFilterList.value = _userList.value
            _checkList.value = true
        }
    }

    fun filterNameList(name: String) {
        viewModelScope.launch {

            _userFilterList.value = _userList.value

            val firstNameFilterList =
                _userList.value?.filter { it.firstName.startsWith(name.lowercase()) }

            val lastNameFilterList =
                _userList.value?.filter { it.lastName.startsWith(name.lowercase()) }

            val userTagFilterList = when (true) {
                name.length in 0..2 -> {
                    _userList.value?.filter { it.userTag.startsWith(name.lowercase()) }
                }
                else -> {
                    _userFilterList.value?.filter {
                        it.userTag.startsWith(
                            name.lowercase().subSequence(0, 2)
                        )
                    }
                }
            }

            val list = HashSet<UserEntity>()
            list.addAll(firstNameFilterList!!)
            list.addAll(lastNameFilterList!!)
            list.addAll(userTagFilterList!!)

            _userFilterList.value = list.toList()
        }
    }

    fun departmentFilter(list: List<UserEntity>, position: Int?): List<UserEntity> {

        val list1 = when (position) {
            0 ->  list

            1 -> list.filter { it.department == "design" }

            2 ->  list.filter { it.department == "analytics" }

            3 ->  list.filter { it.department == "management" }

            4 ->  list.filter { it.department == "ios" }

            else -> emptyList<UserEntity>()
        }
        val list2 = list1.sortedBy { it.firstName }
        _checkList.value = list1.isNotEmpty()
        return list2
    }
}