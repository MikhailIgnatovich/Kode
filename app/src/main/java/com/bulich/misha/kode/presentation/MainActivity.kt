package com.bulich.misha.kode.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bulich.misha.kode.R
import com.bulich.misha.kode.data.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
//    @Inject
//    lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        appComponent.inject(this)
//        val scope = CoroutineScope(Dispatchers.Main)
//        scope.launch {
//            val api = apiService.getUsersFromServer()
//            if (api.isSuccessful){
//                val response = api.body()
//                val user1 = response!!.items[0]
//                Log.d("USER1", "$user1")
//                for (user in response!!.items) {
////                    val used = User(id = user.id, avatarUrl = user.avatarUrl, firstName = user.firstName, lastName = user.lastName,
////                    userTag = user.userTag, department = user.department, position = user.position, birthday = user.birthday,
////                    phone = user.phone)
//                    Log.d("USER", "$user")
//                }
//            }
//        }
//

    }
}