package com.android.test_app_news.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

abstract class BaseActivity<T : ViewBinding>(@LayoutRes layoutResId: Int) : AppCompatActivity(layoutResId) {

    protected abstract val binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
