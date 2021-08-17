package com.android.test_app_news.data.db

import androidx.room.*
import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.data.db.model.PostWithUser
import kotlinx.coroutines.flow.Flow


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePost(post: Post)

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<Post>>

    @Transaction
    @Query("SELECT * FROM posts WHERE id=:postId")
    fun getPostWithUserById(postId: Int): Flow<PostWithUser>
}