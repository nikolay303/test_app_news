package com.android.test_app_news.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.test_app_news.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

abstract class BaseFragment<T : ViewBinding>(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected abstract val binding: T

    protected fun showError(error: String, onRetryClick: () -> Unit, onCancelClick: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.label_error)
            .setMessage(error)
            .setPositiveButton(R.string.btn_retry) { _, _ -> onRetryClick() }
            .setNegativeButton(R.string.btn_cancel) { _, _ -> onCancelClick() }
            .setCancelable(false)
            .show()
    }
}
