package com.bulich.misha.kode.data.models

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("items")
    val items: List<User>
)
