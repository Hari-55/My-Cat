package com.hari.mycat.data.cat.models.mapper

import com.hari.mycat.data.cat.models.CatModel
import com.hari.mycat.domain.cat.entities.CatEntity
import com.hari.mycat.utils.EntityMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatMapper @Inject constructor() : EntityMapper<CatEntity, CatModel> {

    override fun mapToEntity(domainModel: CatModel) = CatEntity(
        imageCat = domainModel.link
    )
}