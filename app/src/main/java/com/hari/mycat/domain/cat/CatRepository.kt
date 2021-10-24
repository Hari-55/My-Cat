package com.hari.mycat.domain.cat

import com.hari.mycat.domain.cat.entities.CatEntity
import com.hari.mycat.utils.Result
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getImageCat(): Flow<Result<CatEntity>>
}