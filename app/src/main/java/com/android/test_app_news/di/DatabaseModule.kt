package com.android.test_app_news.di

import android.content.Context
import androidx.room.Room
import com.android.test_app_news.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    private const val DATABASE_NAME = "app_news.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}
