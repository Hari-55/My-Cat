package com.hari.mycat.utils

interface EntityMapper<Entity, DataModel> {
    fun mapToEntity(domainModel: DataModel): Entity
}