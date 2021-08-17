package com.android.test_app_news.data.remote


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

data class PostResponse(
    val id: Int?,
    val title: String?,
    val body: String?,
    val userId: Int?
)

data class UserResponse(
    val id: Int?,
    val name: String?
)