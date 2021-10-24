package com.hari.mycat.data.cat

import com.hari.mycat.data.cat.datasources.netwoks.CatApi
import com.hari.mycat.data.cat.models.mapper.CatMapper
import com.hari.mycat.data.cat.repository.CatRepositoryImpl
import com.hari.mycat.data.common.modules.NetworkModule
import com.hari.mycat.domain.cat.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
object CatModule {

    @Singleton
    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApi = retrofit.create(CatApi::class.java)

    @Singleton
    @Provides
    fun provideCatRepository(
        catApi: CatApi,
        catMapper: CatMapper
    ): CatRepository = CatRepositoryImpl(catApi, catMapper)
}