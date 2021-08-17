package com.android.test_app_news.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
) {
    constructor() : this(-1, "", "", -1)
}