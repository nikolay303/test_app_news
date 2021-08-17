package com.android.test_app_news.data.repository.post

import com.android.test_app_news.data.db.AppDatabase
import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.data.db.model.PostWithUser
import com.android.test_app_news.data.remote.RemoteDataSource
import com.android.test_app_news.data.remote.toPost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

class PostRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteDataSource: RemoteDataSource
) : PostRepository {

    override suspend fun loadPosts() {
        remoteDataSource.getPosts()
            .map { it.toPost() }
            .also { database.postDao.savePosts(it) }
    }

    override fun getPosts(): Flow<List<Post>> {
        return database.postDao.getPosts()
    }

    override suspend fun loadPost(postId: Int): Post {
        val response = remoteDataSource.getPostDetails(postId)
        val post = response.toPost()
        database.postDao.savePost(post)
        return post
    }

    override fun getPostWithUser(postId: Int): Flow<PostWithUser> {
        return database.postDao.getPostWithUserById(postId)
    }
}