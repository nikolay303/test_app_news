package com.android.test_app_news.ui.post_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.test_app_news.R
import com.android.test_app_news.data.db.model.PostWithUser
import com.android.test_app_news.data.db.model.avatarUrl
import com.android.test_app_news.databinding.FragmentPostDetailsBinding
import com.android.test_app_news.ui.base.BaseFragment
import com.android.test_app_news.utils.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@AndroidEntryPoint
class PostDetailsFragment :
    BaseFragment<FragmentPostDetailsBinding>(R.layout.fragment_post_details) {
    companion object {
        const val ARG_POST_ID = "post_id"
    }

    override val binding: FragmentPostDetailsBinding by viewBinding()

    private val viewModel by viewModels<PostDetailsViewModel> {
        val postId = arguments?.getInt(ARG_POST_ID, -1) ?: -1
        PostDetailsViewModel.provideFactory(viewModelFactory, postId)
    }

    @Inject
    lateinit var viewModelFactory: PostDetailsViewModel.AssistedFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { onStateChanged(it) }
        }
        lifecycleScope.launch {
            viewModel.postWithUser
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { onPostWithUserChanged(it) }
        }
    }

    private fun onStateChanged(state: PostDetailsViewModel.State) {
        binding.progressBar.isVisible = state is PostDetailsViewModel.State.Loading
        if (state is PostDetailsViewModel.State.Error) {
            showError(state.error,
                onRetryClick = { viewModel.retryLoadData() },
                onCancelClick = { viewModel.cachedState() }
            )
        }
    }

    private fun onPostWithUserChanged(data: PostWithUser) {
        with(binding) {
            ivUserAvatar.load(data.user?.avatarUrl.orEmpty())
            tvUserName.text = data.user?.name.orEmpty()
            tvPostTitle.text = data.post.title
            tvPostDescription.text = data.post.body
        }
    }
}