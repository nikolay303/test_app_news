package com.android.test_app_news.data.remote

import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.data.db.model.User


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

fun PostResponse.toPost(): Post {
    return Post(
        id ?: -1,
        title.orEmpty(),
        body.orEmpty(),
        userId ?: -1
    )
}

fun UserResponse.toUser(): User {
    return User(
        id ?: -1,
        name.orEmpty()
    )
}