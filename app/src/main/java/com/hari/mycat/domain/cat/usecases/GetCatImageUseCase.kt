package com.hari.mycat.domain.cat.usecases

import com.hari.mycat.domain.cat.CatRepository
import com.hari.mycat.domain.cat.entities.CatEntity
import com.hari.mycat.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatImageUseCase @Inject constructor(private val catRepository: CatRepository) {
    operator fun invoke(): Flow<Result<CatEntity>> = catRepository.getImageCat()
}