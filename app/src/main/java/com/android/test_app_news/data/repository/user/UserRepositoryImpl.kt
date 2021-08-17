package com.android.test_app_news.data.repository.user

import com.android.test_app_news.data.db.AppDatabase
import com.android.test_app_news.data.db.model.User
import com.android.test_app_news.data.remote.RemoteDataSource
import com.android.test_app_news.data.remote.toUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

class UserRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteDataSource: RemoteDataSource
) : UserRepository {

    override suspend fun loadUser(userId: Int) {
        val response = remoteDataSource.getUser(userId)
        database.userDao.saveUser(response.toUser())
    }

}