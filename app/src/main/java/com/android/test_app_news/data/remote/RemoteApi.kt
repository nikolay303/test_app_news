package com.android.test_app_news.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

interface RemoteApi {

    /**
     * Post
     */
    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET("posts/{post_id}")
    suspend fun getPostDetails(@Path("post_id") postId: Int): Response<PostResponse>

    /**
     * User
     */
    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: Int): Response<UserResponse>
}