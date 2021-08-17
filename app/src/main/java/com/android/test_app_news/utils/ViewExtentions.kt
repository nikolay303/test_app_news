package com.android.test_app_news.utils

import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}