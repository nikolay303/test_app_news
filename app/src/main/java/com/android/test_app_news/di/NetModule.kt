package com.android.test_app_news.di

import android.content.Context
import com.android.test_app_news.BuildConfig
import com.android.test_app_news.data.remote.RemoteApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@InstallIn(SingletonComponent::class)
@Module
object NetModule {

    private const val OK_HTTP_CACHE_SIZE = 10L * 1024 * 1024 // 10 MB
    private const val OK_HTTP_READ_TIMEOUT = 60L * 1000
    private const val OK_HTTP_WRITE_TIMEOUT = 60L * 1000

    @Provides
    @Singleton
    fun provideRemoteApi(retrofit: Retrofit): RemoteApi =
        retrofit.create(RemoteApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpClientBuilder
            .cache(Cache(context.cacheDir, OK_HTTP_CACHE_SIZE))
            .readTimeout(OK_HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(OK_HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .create()
}
