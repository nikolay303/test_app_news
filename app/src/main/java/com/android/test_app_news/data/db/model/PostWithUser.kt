package com.android.test_app_news.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

data class PostWithUser(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User?
)