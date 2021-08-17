package com.android.test_app_news.data.remote

import javax.inject.Inject


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

class RemoteDataSource @Inject constructor(
    private val remoteApi: RemoteApi
) : BaseRemoteDataSource() {

    /**
     * Post
     */
    suspend fun getPosts(): List<PostResponse> {
        return remoteCall(remoteApi.getPosts())
    }

    suspend fun getPostDetails(postId: Int): PostResponse {
        return remoteCall(remoteApi.getPostDetails(postId))
    }

    /**
     * User
     */
    suspend fun getUser(userId: Int): UserResponse {
        return remoteCall(remoteApi.getUser(userId))
    }
}