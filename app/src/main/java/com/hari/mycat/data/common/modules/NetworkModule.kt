package com.hari.mycat.data.common.modules

import android.content.Context
import com.hari.mycat.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            baseUrl(BuildConfig.BASE_URL_CAT)
        }.build()

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient = OkHttpClient().newBuilder().apply {
        connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        cache(cache)
        addInterceptor(headerInterceptor)
    }.build()

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }
}