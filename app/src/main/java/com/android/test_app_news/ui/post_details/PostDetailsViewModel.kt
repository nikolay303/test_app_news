package com.android.test_app_news.ui.post_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.test_app_news.data.repository.post.PostRepository
import com.android.test_app_news.data.repository.user.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.Cache


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

class PostDetailsViewModel @AssistedInject constructor(
    @Assisted private val postId: Int,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(postId: Int): PostDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            postId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(postId) as T
            }
        }
    }

    val postWithUser = postRepository.getPostWithUser(postId)

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.emit(State.Loading)
            runCatching {
                val post = postRepository.loadPost(postId)
                userRepository.loadUser(post.userId)
            }
                .onSuccess { _state.emit(State.Loaded) }
                .onFailure { _state.emit(State.Error(it.localizedMessage.orEmpty())) }
        }
    }

    fun retryLoadData() {
        loadData()
    }

    fun cachedState() {
        _state.value = State.Cached
    }

    sealed class State {
        object Loading : State()
        object Loaded : State()
        data class Error(val error: String) : State()
        object Cached : State()
    }
}
