package com.hari.mycat.data.cat.models

import com.google.gson.annotations.SerializedName

data class CatModel(
    @field:SerializedName("link")
    val link: String? = null
)