package com.android.test_app_news.ui.feed

import androidx.lifecycle.*
import com.android.test_app_news.data.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    val posts = postRepository.getPosts()

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        loadFeed()
    }

    private suspend fun loadData() {
        runCatching { postRepository.loadPosts() }
            .onSuccess { _state.emit(State.Loaded) }
            .onFailure { _state.emit(State.Error(it.localizedMessage.orEmpty())) }
    }

    fun refreshFeed() {
        viewModelScope.launch {
            loadData()
        }
    }

    fun loadFeed() {
        viewModelScope.launch {
            _state.emit(State.Loading)
            loadData()
        }
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