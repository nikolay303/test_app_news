package com.android.test_app_news.di

import com.android.test_app_news.data.repository.post.*
import com.android.test_app_news.data.repository.user.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindPostRepository(repository: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
