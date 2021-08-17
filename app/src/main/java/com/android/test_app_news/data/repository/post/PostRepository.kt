package com.android.test_app_news.data.repository.post

import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.data.db.model.PostWithUser
import kotlinx.coroutines.flow.Flow


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

interface PostRepository {

    suspend fun loadPosts()

    fun getPosts(): Flow<List<Post>>

    suspend fun loadPost(postId: Int): Post

    fun getPostWithUser(postId: Int): Flow<PostWithUser>
}