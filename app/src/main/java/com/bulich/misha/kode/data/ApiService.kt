package com.bulich.misha.kode.data

import com.bulich.misha.kode.data.models.Items
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, example=success"
    )
    @GET("users")
    suspend fun getUsersFromServer(): Response<Items>

    @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, dynamic=true"
    )
    @GET("users")
    suspend fun getTestUsersFromServer(): Response<Items>

    @Headers(
        "Content-Type: application/json",
        "Prefer: code=500, example=error-500"
    )
    @GET("users")
    suspend fun getErrorUsersFromServer(): Response<Items>
}