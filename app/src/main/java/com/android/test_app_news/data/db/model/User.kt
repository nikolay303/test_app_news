package com.android.test_app_news.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.test_app_news.BuildConfig


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    val name: String
) {
    constructor() : this(-1, "")
}

val User.avatarUrl: String
    get() = BuildConfig.USER_PHOTO_BASE_URL + "?sig=$id"
