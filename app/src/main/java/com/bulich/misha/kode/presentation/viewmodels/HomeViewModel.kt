package com.bulich.misha.kode.presentation.viewmodels


import androidx.lifecycle.*
import com.bulich.misha.kode.domain.entity.UserEntity
import com.bulich.misha.kode.domain.useCase.GetInternetConnectionStatus
import com.bulich.misha.kode.domain.useCase.GetListUserEntityUseCase
import com.bulich.misha.kode.domain.util.Resource
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet

class HomeViewModel @Inject constructor(
    private val getListUserEntityUseCase: GetListUserEntityUseCase,
    private val getInternetConnectionStatus: GetInternetConnectionStatus
) : ViewModel() {

    private var _userList = MutableLiveData<List<UserEntity>>()
    val userList: LiveData<List<UserEntity>>
        get() = _userList

    private var _userFilterList = MutableLiveData<List<UserEntity>>()
    val userFilterList: LiveData<List<UserEntity>>
        get() = _userFilterList

    private var _checkListEmpty = MutableLiveData<Boolean>()
    val checkListEmpty: LiveData<Boolean>
        get() = _checkListEmpty

    private var _sortMode = MutableLiveData<Boolean>()
    val sortMode: LiveData<Boolean>
        get() = _sortMode

    private var _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean>
        get() = _loadingStatus

    private var _loadingError = MutableLiveData<String>()
    val loadingError: LiveData<String>
        get() = _loadingError

    private var _internetConnectionStatus = MutableLiveData<Boolean>()
    val internetConnectionStatus: LiveData<Boolean>
        get() = _internetConnectionStatus


    init {

        loadUserEntityList()

    }

    fun setSortMode(boolean: Boolean) {
        _sortMode.value = boolean
    }

    fun loadUserEntityList() {
        viewModelScope.launch {
            _internetConnectionStatus.value = getInternetConnectionStatus.connectionInternetStatus()

            if (getInternetConnectionStatus.connectionInternetStatus()){
                _loadingStatus.value = true
                when (val response = getListUserEntityUseCase.getListUserEntity()) {
                    is Resource.Success -> {
                        _userList.value = response.data!!
                        _loadingStatus.value = false
                    }
                    is Resource.Error -> {
                        _loadingStatus.value = false
                        _loadingError.value = response.message.toString()

                    }
                }
                _userFilterList.value = _userList.value
                _checkListEmpty.value = true
            }

        }
    }

    fun getFilterSearchList(name: String) {
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

            val filterList = HashSet<UserEntity>()
            filterList.addAll(firstNameFilterList!!)
            filterList.addAll(lastNameFilterList!!)
            filterList.addAll(userTagFilterList!!)

            _userFilterList.value = filterList.toList()
        }
    }

    fun getDepartmentFilterList(list: List<UserEntity>, position: Int?): List<UserEntity> {

        val departmentFilterList = when (position) {
            0 -> list
            1 -> list.filter { it.department == "Android" }
            2 -> list.filter { it.department == "iOS" }
            3 -> list.filter { it.department == "Дизайн" }
            4 -> list.filter { it.department == "Менеджмент" }
            5 -> list.filter { it.department == "QA" }
            6 -> list.filter { it.department == "Бэк-офис" }
            7 -> list.filter { it.department == "Frontend" }
            8 -> list.filter { it.department == "HR" }
            9 -> list.filter { it.department == "PR" }
            10 -> list.filter { it.department == "Backend" }
            11 -> list.filter { it.department == "Техподдержка" }
            12 -> list.filter { it.department == "Аналитика" }

            else -> emptyList<UserEntity>()
        }

        val departmentListSortBySortMode = when (true) {
            _sortMode.value -> sortListByBirthday(departmentFilterList)
            else -> sortListByAlphabet(departmentFilterList)
        }

        _checkListEmpty.value = departmentFilterList.isNotEmpty()
        return departmentListSortBySortMode
    }

    private fun sortListByBirthday(list: List<UserEntity>): List<UserEntity> {
        val currentDate = Calendar.getInstance()
        val currentDayOfYear = currentDate.get(Calendar.DAY_OF_YEAR)
        val thisYearList = mutableListOf<UserEntity>()
        val followingYearList = mutableListOf<UserEntity>()

        for (user in list) {
            user.sortMode = true
            if (user.birthday.dayOfYear >= currentDayOfYear) {
                thisYearList.add(user)
            } else {
                followingYearList.add(user)
            }
        }

        val thisYearListSort = thisYearList.sortedBy { it.birthday.dayOfYear }.toList()
        val followingYearListSort = followingYearList.sortedBy { it.birthday.dayOfYear }.toList()

        return thisYearListSort + followingYearListSort

    }

    private fun sortListByAlphabet(list: List<UserEntity>): List<UserEntity> {
        val listSort1 = mutableListOf<UserEntity>()
        if (list.isNotEmpty()) {
            for (user in list) {
                user.sortMode = false
                listSort1.add(user)
            }
        }
        return listSort1.toList().sortedBy { it.firstName }
    }
}