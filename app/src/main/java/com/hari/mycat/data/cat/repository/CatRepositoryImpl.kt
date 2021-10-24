package com.hari.mycat.data.cat.repository

import com.hari.mycat.data.cat.datasources.netwoks.CatApi
import com.hari.mycat.data.cat.models.mapper.CatMapper
import com.hari.mycat.domain.cat.CatRepository
import com.hari.mycat.domain.cat.entities.CatEntity
import com.hari.mycat.utils.Result
import com.hari.mycat.utils.errorCodeNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApi: CatApi,
    private val catMapper: CatMapper
) : CatRepository {
    override fun getImageCat(): Flow<Result<CatEntity>> = flow {
        val response = catApi.getImageCat()
        if (response.isSuccessful) {
            emit(Result.Data(catMapper.mapToEntity(response.body()!!)))
        }
        if (response.code() == 400) Result.Error(response.code().errorCodeNetwork(response.errorBody()?.string()))
        else Result.Error(response.code().errorCodeNetwork(""))
    }.flowOn(Dispatchers.IO)
}