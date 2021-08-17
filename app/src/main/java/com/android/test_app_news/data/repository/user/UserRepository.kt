package com.android.test_app_news.data.repository.user


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

interface UserRepository {

    suspend fun loadUser(userId: Int)
}