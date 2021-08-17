package com.android.test_app_news.data.remote

import retrofit2.Response


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

open class BaseRemoteDataSource {

    protected fun <ResponseType> remoteCall(response: Response<ResponseType>): ResponseType {
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalArgumentException("Empty response body")
        } else {
            throw IllegalStateException(response.errorBody()?.string())
        }
    }
}