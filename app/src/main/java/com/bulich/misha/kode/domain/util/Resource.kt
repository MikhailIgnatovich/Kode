package com.bulich.misha.kode.domain.util

sealed class Resource<T>(val data: T?, val message: String?, val loadingStatus: Boolean?){

    class Success<T>(data: T) : Resource<T>(data, null, null)
    class Error<T>(message: String) : Resource<T>(null, message, null)

}
