package com.android.test_app_news.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.test_app_news.data.db.model.User
import kotlinx.coroutines.flow.Flow


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

}