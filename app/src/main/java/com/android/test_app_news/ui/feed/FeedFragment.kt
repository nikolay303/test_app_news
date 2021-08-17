package com.android.test_app_news.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.test_app_news.R
import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.databinding.FragmentFeedBinding
import com.android.test_app_news.ui.base.BaseFragment
import com.android.test_app_news.ui.post_details.PostDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    override val binding: FragmentFeedBinding by viewBinding()

    private val viewModel by viewModels<FeedViewModel>()
    private val adapter by lazy { PostsAdapter(onPostClick = this::onPostClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { onStateChanged(it) }
        }
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refreshFeed() }
        setupFeed()
    }

    private fun setupFeed() {
        binding.rvFeed.adapter = adapter
        binding.rvFeed.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rvFeed.layoutManager as LinearLayoutManager).orientation
            )
        )
        lifecycleScope.launch {
            viewModel.posts
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collectLatest { adapter.submitList(it) }
        }
    }

    private fun onStateChanged(state: FeedViewModel.State) {
        binding.progressBar.isVisible = state is FeedViewModel.State.Loading
        if (state is FeedViewModel.State.Error) {
            binding.swipeRefreshLayout.hideProgress()
            showError(state.error,
                onRetryClick = { viewModel.loadFeed() },
                onCancelClick = { viewModel.cachedState() }
            )
        }
        if (state is FeedViewModel.State.Loaded) {
            binding.swipeRefreshLayout.hideProgress()
        }
    }

    private fun onPostClick(post: Post) {
        findNavController().navigate(
            R.id.action_feedFragment_to_postDetailsFragment,
            bundleOf(PostDetailsFragment.ARG_POST_ID to post.id)
        )
    }

    private fun SwipeRefreshLayout.hideProgress() {
        isRefreshing = false
    }
}