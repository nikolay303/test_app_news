package com.android.test_app_news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.test_app_news.data.db.model.*


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

private const val DATABASE_VERSION = 1

@Database(
    entities = [Post::class, User::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val postDao: PostDao

    abstract val userDao: UserDao
}
