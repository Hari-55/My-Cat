package com.hari.mycat.data.cat.datasources.netwoks

import com.hari.mycat.data.cat.models.CatModel
import retrofit2.Response
import retrofit2.http.GET

interface CatApi {
    @GET("img/cat")
    suspend fun getImageCat(): Response<CatModel>
}